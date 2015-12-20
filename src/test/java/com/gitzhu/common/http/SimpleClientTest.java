package com.gitzhu.common.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by qiuxu.zhu on 15-12-20.
 */
public class SimpleClientTest {

    private SimpleClient client;

    @Before
    public void init(){
        client = new SimpleClient();
    }

    @Test
    public void testGet() throws IOException {
        CloseableHttpResponse response = client.get("http://www.ning.com");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Assert.assertNotNull(response.getEntity().toString());
        Assert.assertNotSame(response.getEntity().toString(), "");
    }

    @Test
    public void testGet2() throws IOException {
        String content = client.get("http://www.ning.com", new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                return response.getEntity().toString();
            }
        });
        System.out.println(content);
    }
}
