package com.switchfly.targetprocess.plugin;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class TargetProcessMethodBuilder {

    private final StringBuilder uri;
    private final Map<String, String> parameters;

    public TargetProcessMethodBuilder(String url) {
        uri = new StringBuilder(url);
        uri.append("/api/v1/");
        parameters = new HashMap<String, String>();
    }

    public TargetProcessMethodBuilder append(int path) {
        return append(Integer.toString(path));
    }

    public TargetProcessMethodBuilder append(String path) {
        if (StringUtils.isNotBlank(path)) {
            uri.append(path);
        }
        return this;
    }

    public TargetProcessMethodBuilder setWhere(String where) {
        return setParameter("where", where);
    }

    public TargetProcessMethodBuilder setInclude(String... fields) {
        return setParameter("include", ArrayUtils.toString(fields).replace('{', '[').replace('}', ']'));
    }

    public TargetProcessMethodBuilder setTake(int take) {
        return setParameter("take", Integer.toString(take));
    }

    public TargetProcessMethodBuilder setOrderByDesc(String orderByDesc) {
        return setParameter("orderByDesc", orderByDesc);
    }

    public TargetProcessMethodBuilder setParameter(String name, String value) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
            parameters.put(name, value);
        }
        return this;
    }

    public HttpMethod build() {
        GetMethod method = new GetMethod(uri.toString());
        method.addRequestHeader("Content-Type", "application/json");
        NameValuePair[] params = new NameValuePair[parameters.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            params[index++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        method.setQueryString(params); //TODO ??
        return method;
    }
}
