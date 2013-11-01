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
import org.jetbrains.annotations.Nullable;

public class TargetProcessMethodFactory {

    public HttpMethod getAssignablesMethod(String url, int userId, @Nullable String query, int take) {
        String where = null;
        if (StringUtils.isNotBlank(query)) {
            if (StringUtils.isNumeric(query)) {
                where = "Id eq " + query;
            } else {
                where = "Name contains '" + query + '\'';
            }
        }

        MethodBuilder builder = new MethodBuilder(url).append("Users/").append(userId).append("/Assignables") //
            .setWhere(where).setOrderByDesc("CreateDate").setTake(take).setInclude(Assignable.INCLUDE);
        return builder.build();
    }

    public HttpMethod getAssignableMethod(String url, String id) {
        MethodBuilder builder = new MethodBuilder(url).append("Assignables") //
            .setWhere("Id eq " + id).setInclude(Assignable.INCLUDE);
        return builder.build();
    }

    public HttpMethod getCommentsMethod(String url, int... assignableIds) {
        StringBuilder where = new StringBuilder("General.Id in (");
        for (int i = 0; i < assignableIds.length; i++) {
            if (i != 0) {
                where.append(',');
            }
            where.append(assignableIds[i]);
        }
        where.append(')');

        MethodBuilder builder = new MethodBuilder(url).append("Comments") //
            .setWhere(where.toString()).setInclude(Comment.INCLUDE);
        return builder.build();
    }

    public HttpMethod getUserMethod(String url, String username) {
        MethodBuilder builder = new MethodBuilder(url).append("Users") //
            .setWhere("Login eq '" + username + '\'') //
            .setOrderByDesc("CreateDate").setTake(1).setInclude(User.INCLUDE);
        return builder.build();
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

        private MethodBuilder append(@NotNull String path) {
            this.path.append(path);
            return this;
        }

        private MethodBuilder setWhere(String where) {
            return addParameter("where", where);
        }

        private MethodBuilder setInclude(String... fields) {
            return addParameter("include", Arrays.toString(fields));
        }

        private MethodBuilder setTake(int take) {
            return addParameter("take", Integer.toString(take));
        }

        private MethodBuilder setOrderByDesc(String orderByDesc) {
            return addParameter("orderByDesc", orderByDesc);
        }

        private MethodBuilder addParameter(@NotNull String key, String value) {
            if (StringUtils.isNotBlank(value)) {
                parameters.put(key, value);
            }
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
