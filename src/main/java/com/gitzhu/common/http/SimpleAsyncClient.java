package com.gitzhu.common.http;

import com.gitzhu.common.http.adapter.ListenableFutureAdapter;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.ning.http.client.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import org.apache.commons.collections.MapUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author michael
 * qiuxu.zhu@gmail.com
 */
public class SimpleAsyncClient {

    private final Builder builder = new Builder();

    private final Supplier<AsyncHttpClient> lazyClient = Suppliers.memoize(new Supplier<AsyncHttpClient>() {
        public AsyncHttpClient get() {
            builder.setExecutorService(Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "AsyncHttpClient");
                    thread.setDaemon(true);
                    return thread;
                }
            }
            ));
            return new AsyncHttpClient(builder.build());
        }
    });

    public SimpleAsyncClient(){

        setCompressionEnforced(true);

        //pooling conf
        setAllowPoolingConnection(true);
        setPooledConnectionIdleTimeout(60000);

        //connection conf
        setMaximunConnectionsTotal(100);
        setMaximumConnectionsPerHost(20);

        //request conf
        setConnectionTimeout(1000);
        setRequestTimeout(60000);

        //UA
        setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) Chrome/27.0.1453.94 Safari/537.36 qunarhc/8.0.1");
    }

    public void setCompressionEnforced(boolean compressionEnabled){
        builder.setCompressionEnforced(compressionEnabled);
    }

    public void setAllowPoolingConnection(boolean allowPoolingConnection){
        builder.setAllowPoolingConnections(allowPoolingConnection);
    }

    public void setPooledConnectionIdleTimeout(int defaultIdleConnectionInPoolTimeoutInMs){
        builder.setPooledConnectionIdleTimeout(defaultIdleConnectionInPoolTimeoutInMs);
    }

    public void setMaximunConnectionsTotal(int defaultMaxTotalConnections){
        builder.setMaxConnections(defaultMaxTotalConnections);
    }

    public void setMaximumConnectionsPerHost(int defaultMaxConnectionPerHost){
        builder.setMaxConnectionsPerHost(defaultMaxConnectionPerHost);
    }

    public void setConnectionTimeout(int defaultConnectionTimeOut){
        builder.setConnectTimeout(defaultConnectionTimeOut);
    }

    public void setRequestTimeout(int defaultRequestTimeout){
        builder.setRequestTimeout(defaultRequestTimeout);
    }

    public void setUserAgent(String userAgent){
        builder.setUserAgent(userAgent);
    }
    public AsyncHttpClient getClient(){
        return lazyClient.get();
    }

    private <T>ListenableFuture<T> privateGet(final String url, HttpOption option, AsyncHandler<T> handler){
        BoundRequestBuilder builder = getClient().prepareGet(url);

        if (Optional.of(option).isPresent()){
            Map<String, String> headers = option.getHeaders();

            if(MapUtils.isNotEmpty(headers)){
                for (Map.Entry<String, String> entry : option.getHeaders().entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            if (option.getProxy() != null){
                builder.setProxyServer(option.getProxy());
            }

        }

        Request request = builder.build();
        return new ListenableFutureAdapter<T>(getClient().executeRequest(request, handler));
    }

    private <T> ListenableFuture<T> privatePost(String url, HttpOption option, AsyncHandler<T> handler) {
        BoundRequestBuilder builder = getClient().preparePost(url);

        if (Optional.of(option).isPresent()){
            Map<String, String> headers = option.getHeaders();
            Map<String, String> postFormData = option.getPostFormData();
            String postBodyData = option.getPostBodyData();
            ProxyServer proxyServer = option.getProxy();

            if(MapUtils.isNotEmpty(headers)){
                for(Map.Entry<String, String> entry : option.getHeaders().entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            if (MapUtils.isNotEmpty(postFormData)){
                for (Map.Entry<String, String> entry : postFormData.entrySet()){
                    builder.addFormParam(entry.getKey(), entry.getValue());
                }
            }

            if (proxyServer != null){
                builder.setProxyServer(proxyServer);
            }

            if (!Strings.isNullOrEmpty(postBodyData)){
                builder.setBody(postBodyData);
            }

        }

        Request request = builder.build();
        return new ListenableFutureAdapter<T>(getClient().executeRequest(request, handler));

    }

    public <T>ListenableFuture<T> get(String url, HttpOption option, AsyncHandler<T> handler){
        return privateGet(url, option, handler);
    }

    public <T>ListenableFuture<T> get(String url, HttpOption option){
        return privateGet(url, option, null);
    }

    public <T>ListenableFuture<T> post(String url, HttpOption option, AsyncHandler<T> handler){
        return privatePost(url, option, handler);
    }

    public <T>ListenableFuture<T> post(String url, HttpOption option){
        return privatePost(url, option, null);
    }

    public void close(){
        getClient().close();
    }

    public void closeAsynchronously(){
        getClient().closeAsynchronously();
    }

}
