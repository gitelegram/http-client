package com.gitzhu.common.http;

import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * @author michael
 * qiuxu.zhu@gmail.com
 */
public class GeekAsyncClientTest {

    private GeekAsyncClient geekAsyncClient;
    private String url;
    private HttpOption option;

    @Before
    public void init(){
        geekAsyncClient = new GeekAsyncClient();
        url = "http://www.10010.com";
        option = new HttpOption();
    }


    @Test
    public void testGet() throws ExecutionException, InterruptedException {
        ListenableFuture<String> future = geekAsyncClient.get(url, option, new AsyncHandler<String>() {
            private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            public void onThrowable(Throwable t) {

            }
            public STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                bytes.write(bodyPart.getBodyPartBytes());
                System.out.println("body");
                return STATE.CONTINUE;
            }

            public STATE onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                System.out.println("status");
                return STATE.CONTINUE;
            }

            public STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
                System.out.println("header");
                return STATE.CONTINUE;
            }

            public String onCompleted() throws Exception {
                return bytes.toString();
            }
        });
        String body = future.get();
        System.out.println(body);
    }
}
