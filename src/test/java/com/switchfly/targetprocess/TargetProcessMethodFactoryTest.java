package com.switchfly.targetprocess;

import java.util.Arrays;
import java.util.List;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.Comment;
import com.switchfly.targetprocess.model.User;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TargetProcessMethodFactoryTest {

    TargetProcessMethodFactory factory = new TargetProcessMethodFactory();

    @Test
    public void testGetAssignablesMethodString() throws Exception {
        HttpMethod method = factory.getAssignablesMethod("http://some.test", 5, "Some Magic Query", 6);

        URI uri = method.getURI();
        assertEquals("some.test", uri.getHost());
        assertEquals("/api/v1/Users/5/Assignables", uri.getPath());

        assertTrue(Arrays.toString(method.getRequestHeaders()).contains("Accept: application/json"));

        List<String> params = Arrays.asList(StringUtils.split(uri.getQuery(), '&'));
        assertEquals(4, params.size());
        assertTrue(params.contains("take=6"));
        assertTrue(params.contains("orderByDesc=CreateDate"));
        assertTrue(params.contains("where=(Name contains 'Some Magic Query')"));
        assertTrue(params.contains("include=" + Arrays.toString(Assignable.INCLUDE)));
    }

    @Test
    public void testGetAssignablesMethodNumber() throws Exception {
        HttpMethod method = factory.getAssignablesMethod("http://some.test", 5, "2695", 6);

        URI uri = method.getURI();
        assertEquals("some.test", uri.getHost());
        assertEquals("/api/v1/Users/5/Assignables", uri.getPath());

        assertTrue(Arrays.toString(method.getRequestHeaders()).contains("Accept: application/json"));

        List<String> params = Arrays.asList(StringUtils.split(uri.getQuery(), '&'));
        assertEquals(4, params.size());
        assertTrue(params.contains("take=6"));
        assertTrue(params.contains("orderByDesc=CreateDate"));
        assertTrue(params.contains("where=(Id eq 2695)"));
        assertTrue(params.contains("include=" + Arrays.toString(Assignable.INCLUDE)));
    }

    @Test
    public void testGetAssignablesMethod() throws Exception {
        HttpMethod method = factory.getAssignablesMethod("http://some.test", 5, null, 6);

        URI uri = method.getURI();
        assertEquals("some.test", uri.getHost());
        assertEquals("/api/v1/Users/5/Assignables", uri.getPath());

        assertTrue(Arrays.toString(method.getRequestHeaders()).contains("Accept: application/json"));

        List<String> params = Arrays.asList(StringUtils.split(uri.getQuery(), '&'));
        assertEquals(3, params.size());
        assertTrue(params.contains("take=6"));
        assertTrue(params.contains("orderByDesc=CreateDate"));
        assertTrue(params.contains("include=" + Arrays.toString(Assignable.INCLUDE)));
    }

    @Test
    public void testGetAssignableMethod() throws Exception {
        HttpMethod method = factory.getAssignableMethod("http://some.test", "2695");

        URI uri = method.getURI();
        assertEquals("some.test", uri.getHost());
        assertEquals("/api/v1/Assignables", uri.getPath());

        assertTrue(Arrays.toString(method.getRequestHeaders()).contains("Accept: application/json"));

        List<String> params = Arrays.asList(StringUtils.split(uri.getQuery(), '&'));
        assertEquals(2, params.size());
        assertTrue(params.contains("where=(Id eq 2695)"));
        assertTrue(params.contains("include=" + Arrays.toString(Assignable.INCLUDE)));
    }

    @Test
    public void testGetCommentsMethod() throws Exception {
        HttpMethod method = factory.getCommentsMethod("http://some.test", 5, 6, 7, 2);

        URI uri = method.getURI();
        assertEquals("some.test", uri.getHost());
        assertEquals("/api/v1/Comments", uri.getPath());

        assertTrue(Arrays.toString(method.getRequestHeaders()).contains("Accept: application/json"));

        List<String> params = Arrays.asList(StringUtils.split(uri.getQuery(), '&'));
        assertEquals(2, params.size());
        assertTrue(params.contains("where=(General.Id in (5,6,7,2))"));
        assertTrue(params.contains("include=" + Arrays.toString(Comment.INCLUDE)));
    }

    @Test
    public void testGetUserMethod() throws Exception {
        HttpMethod method = factory.getUserMethod("http://some.test", "guti.dev");

        URI uri = method.getURI();
        assertEquals("some.test", uri.getHost());
        assertEquals("/api/v1/Users", uri.getPath());

        assertTrue(Arrays.toString(method.getRequestHeaders()).contains("Accept: application/json"));

        List<String> params = Arrays.asList(StringUtils.split(uri.getQuery(), '&'));
        assertEquals(4, params.size());
        assertTrue(params.contains("take=1"));
        assertTrue(params.contains("orderByDesc=CreateDate"));
        assertTrue(params.contains("where=(Login eq 'guti.dev')"));
        assertTrue(params.contains("include=" + Arrays.toString(User.INCLUDE)));
    }
}
