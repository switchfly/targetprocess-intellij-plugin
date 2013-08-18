package com.switchfly.targetprocess.plugin;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class TargetProcessMethodBuilder {

    private final StringBuilder _uri;
    private final Map<String, String> _parameters;

    public TargetProcessMethodBuilder(String url) {
        _uri = new StringBuilder(url);
        _uri.append("/api/v1/");
        _parameters = new HashMap<String, String>();
    }

    public TargetProcessMethodBuilder append(int path) {
        return append(Integer.toString(path));
    }

    public TargetProcessMethodBuilder append(String path) {
        if (StringUtils.isNotBlank(path)) {
            _uri.append(path);
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
            _parameters.put(name, value);
        }
        return this;
    }

    public HttpMethod build() {
        GetMethod method = new GetMethod(_uri.toString());
        method.addRequestHeader("Content-Type", "application/json");
        NameValuePair[] params = new NameValuePair[_parameters.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : _parameters.entrySet()) {
            params[index++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        method.setQueryString(params);
        return method;
    }
}
