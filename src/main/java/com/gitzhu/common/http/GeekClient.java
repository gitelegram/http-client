package com.gitzhu.common.http;


import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author michael
 * qiuxu.zhu@gmail.com
 */
public class GeekClient {

    private final HttpClientBuilder builder = HttpClientBuilder.create();

    private final Supplier<CloseableHttpClient> lazyClient = Suppliers.memoize(new Supplier<CloseableHttpClient>() {
        @Override
        public CloseableHttpClient get() {
            return builder.build();
        }
    });

    public GeekClient(){
        setConnectionPool();
    }

    public void setConnectionPool(){
        builder.setMaxConnTotal(200);
        builder.setMaxConnPerRoute(20);
    }

    public CloseableHttpClient getClient(){
        return lazyClient.get();
    }

    public CloseableHttpResponse get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return getClient().execute(httpGet);
    }

    public <T> T get(String url, final ResponseHandler<T> handler) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return getClient().execute(httpGet, handler);
    }

    public CloseableHttpResponse post(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        return getClient().execute(httpPost);
    }

    public <T> T post(String url, ResponseHandler<T> handler) throws IOException{
        HttpPost httpPost = new HttpPost(url);
        return getClient().execute(httpPost, handler);
    }

    public void close() throws IOException {
        getClient().close();
    }
}
