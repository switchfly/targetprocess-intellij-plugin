package com.switchfly.targetprocess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.intellij.util.NotNullFunction;
import com.intellij.util.containers.ContainerUtil;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.Comment;
import com.switchfly.targetprocess.model.User;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class TargetProcessMethodFactory {

    public HttpMethod getAssignablesMethod(String url, int userId, String query, int take) throws Exception { //TODO improve
        String where = null;
        if (StringUtils.isNotBlank(query)) {
            if (StringUtils.isNumeric(query)) {
                where = "(Id eq " + query + ')';
            } else {
                where = "(Name contains '" + query + "')";
            }
        }
        MethodBuilder builder = new MethodBuilder(url).append("Users/").append(userId).append("/Assignables");
        builder.setInclude(Assignable.INCLUDE).setTake(take).setOrderByDesc("CreateDate");
        builder.setWhere(where);

        return builder.build();
    }

    public HttpMethod getAssignableMethod(String url, String id) throws Exception {
        MethodBuilder builder = new MethodBuilder(url).append("Assignables");
        builder.setInclude(Assignable.INCLUDE);
        builder.setWhere("(Id eq " + id + ')');
        return builder.build();
    }

    public HttpMethod getCommentsMethod(String url, int... assignableIds) throws Exception {
        MethodBuilder builder = new MethodBuilder(url).append("Comments");
        StringBuilder where = new StringBuilder("(General.Id in (");
        for (int i = 0; i < assignableIds.length; i++) {
            if (i != 0) {
                where.append(',');
            }
            where.append(assignableIds[i]);
        }
        where.append("))");
        builder.setWhere(where.toString()).setInclude(Comment.INCLUDE); //TODO order?
        return builder.build();
    }

    public HttpMethod getUserMethod(String url, String username) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("orderByDesc", "CreateDate");
        parameters.put("take", "1");
        parameters.put("where", "(Login eq '" + username + "')");

        return createMethod(url, "Users", parameters, User.INCLUDE);
    }

    private HttpMethod createMethod(@NotNull String url, @NotNull String path, @NotNull Map<String, String> parameters, @NotNull String... include) {
        GetMethod method = new GetMethod(url);
        method.setPath("/api/v1/" + path);

        method.addRequestHeader("Accept", "application/json");

        parameters.put("include", Arrays.toString(include));
        NameValuePair[] params =
            ContainerUtil.map2Array(parameters.entrySet(), NameValuePair.class, new NotNullFunction<Map.Entry<String, String>, NameValuePair>() {
                @NotNull
                @Override
                public NameValuePair fun(Map.Entry<String, String> entry) {
                    return new NameValuePair(entry.getKey(), entry.getValue());
                }
            });
        method.setQueryString(params);

        return method;
    }

    private class MethodBuilder {

        private final String url;
        private final StringBuilder path;
        private final Map<String, String> parameters;

        private MethodBuilder(String url) {
            this.url = url;
            path = new StringBuilder("/api/v1/");
            parameters = new HashMap<String, String>();
        }

        private MethodBuilder append(int path) {
            return append(Integer.toString(path));
        }

        private MethodBuilder append(String path) {
            if (StringUtils.isNotBlank(path)) {
                this.path.append(path);
            }
            return this;
        }

        private MethodBuilder setWhere(String where) {
            if (StringUtils.isNotBlank(where)) { //TODO
                parameters.put("where", where);
            }
            return this;
        }

        private MethodBuilder setInclude(String... fields) {
            parameters.put("include", Arrays.toString(fields));
            return this;
        }

        private MethodBuilder setTake(int take) {
            parameters.put("take", Integer.toString(take));
            return this;
        }

        private MethodBuilder setTake(String take) {
            parameters.put("take", take);
            return this;
        }

        private MethodBuilder setOrderByDesc(String orderByDesc) {
            parameters.put("orderByDesc", orderByDesc);
            return this;
        }

        private HttpMethod build() {
            GetMethod method = new GetMethod(url);
            method.setPath(path.toString());

            method.addRequestHeader("Accept", "application/json");

            NameValuePair[] params =
                ContainerUtil.map2Array(parameters.entrySet(), NameValuePair.class, new NotNullFunction<Map.Entry<String, String>, NameValuePair>() {
                    @NotNull
                    @Override
                    public NameValuePair fun(Map.Entry<String, String> entry) {
                        return new NameValuePair(entry.getKey(), entry.getValue());
                    }
                });
            method.setQueryString(params);

            return method;
        }
    }
}
