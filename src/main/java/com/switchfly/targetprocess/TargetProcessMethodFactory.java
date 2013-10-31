package com.switchfly.targetprocess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.Comment;
import com.switchfly.targetprocess.model.User;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

public class TargetProcessMethodFactory {

    public HttpMethod getAssignablesMethod(String url, int userId, String query, int take) throws Exception { //TODO improve
        String where = null;
        if (StringUtils.isNotBlank(query)) {
            if (StringUtils.isNumeric(query)) {
                where = "(Id eq " + query + ")";
            } else {
                where = "(Name contains '" + query + "')";
            }
        }
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(url);
        builder.append("Users/").append(userId).append("/Assignables");
        builder.setInclude(Assignable.INCLUDE);
        builder.setWhere(where).setTake(take).setOrderByDesc("CreateDate");
        return builder.build();
    }

    public HttpMethod getAssignableMethod(String url, String id) throws Exception {
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(url).append("Assignables");
        builder.setInclude(Assignable.INCLUDE);
        builder.setWhere("(Id eq " + id + ')');
        return builder.build();
    }

    public HttpMethod getCommentsMethod(String url, String ids) throws Exception { //TODO list?
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(url).append("/Comments");
        builder.setWhere("General.Id in (" + ids + ")").setInclude(Comment.INCLUDE);
        return builder.build();
    }

    public HttpMethod getUserMethod(String url, String username) {
        TargetProcessMethodBuilder builder = new TargetProcessMethodBuilder(url).append("Users").setInclude(User.INCLUDE);
        builder.setWhere("(Login eq '" + username + "')").setOrderByDesc("CreateDate").setTake(1);
        return builder.build();
    }

    private class TargetProcessMethodBuilder {

        private final StringBuilder uri;
        private final Map<String, String> parameters;

        private TargetProcessMethodBuilder(String url) {
            uri = new StringBuilder(url);
            uri.append("/api/v1/");
            parameters = new HashMap<String, String>();
        }

        private TargetProcessMethodBuilder append(int path) {
            return append(Integer.toString(path));
        }

        private TargetProcessMethodBuilder append(String path) {
            if (StringUtils.isNotBlank(path)) {
                uri.append(path);
            }
            return this;
        }

        private TargetProcessMethodBuilder setWhere(String where) {
            return setParameter("where", where);
        }

        private TargetProcessMethodBuilder setInclude(String... fields) {
            return setParameter("include", Arrays.toString(fields));
        }

        private TargetProcessMethodBuilder setTake(int take) {
            return setParameter("take", Integer.toString(take));
        }

        private TargetProcessMethodBuilder setOrderByDesc(String orderByDesc) {
            return setParameter("orderByDesc", orderByDesc);
        }

        private TargetProcessMethodBuilder setParameter(String name, String value) {
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                parameters.put(name, value);
            }
            return this;
        }

        private HttpMethod build() {
            GetMethod method = new GetMethod(uri.toString());
            method.addRequestHeader("Accept", "application/json");
            NameValuePair[] params = new NameValuePair[parameters.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                params[index++] = new NameValuePair(entry.getKey(), entry.getValue());
            }
            method.setQueryString(params); //TODO ??
            return method;
        }
    }
}
