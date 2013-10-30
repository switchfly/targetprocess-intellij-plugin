package com.switchfly.targetprocess.plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskRepositoryType;
import com.intellij.tasks.impl.BaseRepository;
import com.intellij.tasks.impl.BaseRepositoryImpl;
import com.intellij.util.NullableFunction;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.Tag;
import com.switchfly.targetprocess.TargetProcessMethodFactory;
import com.switchfly.targetprocess.TargetProcessParser;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.User;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.jetbrains.annotations.Nullable;

@Tag("TargetProcess")
public class TargetProcessRepository extends BaseRepositoryImpl {

    private static final TargetProcessParser PARSER = new TargetProcessParser();
    private static final TargetProcessMethodFactory FACTORY = new TargetProcessMethodFactory();

    private boolean useNTLM;
    private String host;
    private String domain;
    private int userId = 0;

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
            useNTLM = o.isUseNTLM();
            host = o.getHost();
            domain = o.getDomain();
        }
    }

    @Nullable
    @Override
    public CancellableConnection createCancellableConnection() {
        HttpMethod method = FACTORY.getUserMethod(getUrl(), getUsername());
        return new HttpTestConnection<HttpMethod>(method) {
            @Override
            protected void doTest(HttpMethod method) throws Exception {
                execute(myMethod);
            }
        };
    }

    void execute(HttpMethod method) throws Exception {
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

        List<Task> taskList = ContainerUtil.mapNotNull(assignables, new NullableFunction<Assignable, Task>() {
            public Task fun(Assignable o) {
                return new TargetProcessTask(o, TargetProcessRepository.this);
            }
        });
        return taskList.toArray(new Task[taskList.size()]);
    }

    List<Assignable> getUserAssignable(String query, int max) throws Exception {
        HttpMethod method = FACTORY.getAssignablesMethod(getUrl(), getUserId(), query, max);

        execute(method);

        List<Assignable> assignables = PARSER.parseAssignables(method.getResponseBodyAsStream());

        //TODO attach comments

        return assignables;
    }

    void getCommentsForAssignables(List<Assignable> assignables) throws Exception {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < assignables.size(); ++i) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(assignables.get(i).getId());
        }
        final HttpMethod commentsMethod = FACTORY.getCommentsMethod(getUrl(), sb.toString()); // TODO
        execute(commentsMethod);
        final String bodyAsString = commentsMethod.getResponseBodyAsString();

        Map<Integer, Assignable> generalIdToAssignableMapping = new HashMap<Integer, Assignable>();

        for (Assignable assignable : assignables) {
            generalIdToAssignableMapping.put(assignable.getId(), assignable);
        }

        /*for (com.switchfly.targetprocess.model.Comment comment : commentsResponse.getItems()) {
            final int assignableId = comment.getGeneral().getId();
            generalIdToAssignableMapping.get(assignableId).getComments().add(comment);
        }*/
    }

    @Nullable
    @Override
    public Task findTask(String id) throws Exception {
        HttpMethod method = FACTORY.getAssignableMethod(getUrl(), id);
        execute(method);
        Assignable assignable = PARSER.parseAssignable(method.getResponseBodyAsStream());
        return assignable == null ? null : new TargetProcessTask(assignable, this);
    }

    @Override
    public TargetProcessRepository clone() {
        return new TargetProcessRepository(this);
    }

    private int getUserId() throws Exception {
        if (userId == 0) {
            HttpMethod method = FACTORY.getUserMethod(getUrl(), getUsername());
            execute(method);
            User user = PARSER.parseUser(method.getResponseBodyAsStream());
            userId = user.getId();
        }
        return userId;
    }

    @Override
    protected int getFeatures() {
        return BASIC_HTTP_AUTHORIZATION; //TODO | TIME_MANAGEMENT;
    }

    public boolean isUseNTLM() {
        return useNTLM;
    }

    public void setUseNTLM(boolean useNTLM) {
        this.useNTLM = useNTLM;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
