package com.switchfly.targetprocess.plugin;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.intellij.tasks.LocalTask;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskRepositoryType;
import com.intellij.tasks.impl.BaseRepository;
import com.intellij.tasks.impl.BaseRepositoryImpl;
import com.intellij.util.NullableFunction;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.Tag;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.GenericListResponse;
import com.switchfly.targetprocess.model.User;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

@Tag("TargetProcess")
public class TargetProcessRepository extends BaseRepositoryImpl {

    // private static final Logger log = Logger.getInstance(TargetProcessRepository.class);
    private static final Gson gson = getGson();
    //private static final String WHERE_TOKEN = "where:";
    private boolean _useNTLM;
    private String _host;
    private String _domain;
    private int _userId = 0;

    @SuppressWarnings({"UnusedDeclaration"}) //for serialization
    public TargetProcessRepository() {
        super();
    }

    public TargetProcessRepository(TaskRepositoryType type) {
        super(type);
    }

    public TargetProcessRepository(BaseRepository other) {
        super(other);
        if (other instanceof TargetProcessRepository) {
            TargetProcessRepository o = (TargetProcessRepository) other;
            _useNTLM = o.isUseNTLM();
            _host = o.getHost();
            _domain = o.getDomain();
        }
    }

    private static Gson getGson() {//TODO clean
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Date.class, new JsonDeserializer() {
            //private final DateFormat df = new SimpleDateFormat("yyMMddHHmmssZ");

            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String stringDate = json.getAsJsonPrimitive().getAsString();
                stringDate = stringDate.replace("/Date(", "");//TODO replace this \\/Date\((-?\d+)\)\\/
                stringDate =
                    stringDate.substring(0, stringDate.indexOf("-")); // use a dateparse function: http://md5.tpondemand.com/api/v1/UserStories/meta
                return new Date(Long.valueOf(stringDate));
            }
        });
        return gb.create();
    }

    @Nullable
    @Override
    public CancellableConnection createCancellableConnection() {
        HttpMethod method = getUserMethod();
        return new HttpTestConnection<HttpMethod>(method) {
            @Override
            protected void doTest(HttpMethod method) throws Exception {
                execute(myMethod);
            }
        };
    }

    private HttpMethod getUserMethod() {
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(getUrl()).append("Users");
        builder.setWhere("Login eq '" + getUsername() + "'").setOrderByDesc("CreateDate").setTake(1);
        return builder.build();
    }

    private void execute(HttpMethod method) throws Exception {
        HttpClient client = getHttpClient();
        int status = client.executeMethod(method);
        if (status != 200) {
            throw new Exception(status + " Invalid!");
        }
    }

    @Override
    protected void configureHttpClient(HttpClient client) {
        super.configureHttpClient(client);
        if (isUseNTLM()) {
            client.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY, Arrays.asList(AuthPolicy.NTLM));
            AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT);
            Credentials credentials = new NTCredentials(getUsername(), getPassword(), getHost(), getDomain());
            client.getState().setCredentials(authScope, credentials);
        }
    }

    @Override
    public Task[] getIssues(@Nullable String query, int max, long since) throws Exception {
        List<Assignable> assignables = getUserAssignable(query, max);
        if (assignables == null || assignables.isEmpty()) {
            return Task.EMPTY_ARRAY;
        }

        final String url = getUrl();

        List<Task> taskList = ContainerUtil.mapNotNull(assignables, new NullableFunction<Assignable, Task>() {
            public Task fun(Assignable o) {
                return new TargetProcessTask(o, url, TargetProcessRepository.this);
            }
        });
        return taskList.toArray(new Task[taskList.size()]);
    }

    private List<Assignable> getUserAssignable(String query, int max) throws Exception {
        String where = null;
        if (StringUtils.isNotBlank(query)) {
            if (StringUtils.isNumeric(query)) {
                where = "(Id eq " + query + ")";
            } else {
                where = "(Name contains '" + query + "')";
            }
        }

        HttpMethod method = getAssignablesMethod(where, max);

        execute(method);

        String bodyAsString = method.getResponseBodyAsString();
        Type type = new TypeToken<GenericListResponse<Assignable>>() {
        }.getType();
        GenericListResponse<Assignable> userStoriesResponse = gson.fromJson(bodyAsString, type);

        final List<Assignable> assignables = userStoriesResponse.getItems();
        getCommentsForAssignables(assignables);
        return assignables;
    }

    private void getCommentsForAssignables(List<Assignable> assignables) throws Exception {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < assignables.size(); ++i) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(assignables.get(i).getId());
        }
        final HttpMethod commentsMethod = getCommentsMethod(sb.toString());
        execute(commentsMethod);
        final String bodyAsString = commentsMethod.getResponseBodyAsString();
        Type type = new TypeToken<GenericListResponse<com.switchfly.targetprocess.model.Comment>>() {
        }.getType();
        final GenericListResponse<com.switchfly.targetprocess.model.Comment> commentsResponse = gson.fromJson(bodyAsString, type);

        Map<Integer, Assignable> generalIdToAssignableMapping = new HashMap<Integer, Assignable>();

        for (Assignable assignable : assignables) {
            generalIdToAssignableMapping.put(assignable.getId(), assignable);
        }

        for (com.switchfly.targetprocess.model.Comment comment : commentsResponse.getItems()) {
            final int assignableId = comment.getGeneral().getId();
            generalIdToAssignableMapping.get(assignableId).getComments().add(comment);
        }
    }

    private HttpMethod getAssignablesMethod(String where, int take) throws Exception {
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(getUrl());
        builder.append("Users/").append(getUserId()).append("/Assignables");
        builder.setInclude("Name", "Description", "CreateDate", "ModifyDate", "EntityType", "Project", "EntityState");
        builder.setWhere(where).setTake(take).setOrderByDesc("CreateDate");
        return builder.build();
    }

    private HttpMethod getCommentsMethod(String ids) throws Exception {
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(getUrl()).append("/Comments");
        builder.setWhere("General.Id in (" + ids + ")");
        return builder.build();
    }

    @Override
    public Task findTask(String id) throws Exception {
        HttpMethod method = getAssignablesMethod("Id eq " + id, 1);

        execute(method);

        String bodyAsString = method.getResponseBodyAsString();
        Type type = new TypeToken<GenericListResponse<Assignable>>() {
        }.getType();
        GenericListResponse<Assignable> assignableResponse = gson.fromJson(bodyAsString, type);

        List<Assignable> assignable = assignableResponse.getItems();
        return assignable.isEmpty() ? null : new TargetProcessTask(assignable.get(0), getUrl(), this);
    }

    @Override
    public TargetProcessRepository clone() {
        return new TargetProcessRepository(this);
    }

    @Override
    public void updateTimeSpent(LocalTask task, String timeSpent, String comment) throws Exception {
        super.updateTimeSpent(task, timeSpent, comment); //TODO implement
    }

    private int getUserId() throws Exception {
        if (_userId == 0) {
            HttpMethod method = getUserMethod();
            execute(method);
            Type type = new TypeToken<GenericListResponse<User>>() {
            }.getType();
            GenericListResponse<User> userResponse = gson.fromJson(method.getResponseBodyAsString(), type);
            _userId = userResponse.getItems().iterator().next().getId();
        }
        return _userId;
    }

    @Override
    protected int getFeatures() {
        return BASIC_HTTP_AUTHORIZATION | TIME_MANAGEMENT;
    }

    public boolean isUseNTLM() {
        return _useNTLM;
    }

    public void setUseNTLM(boolean useNTLM) {
        _useNTLM = useNTLM;
    }

    public String getHost() {
        return _host;
    }

    public void setHost(String host) {
        _host = host;
    }

    public String getDomain() {
        return _domain;
    }

    public void setDomain(String domain) {
        _domain = domain;
    }
}
