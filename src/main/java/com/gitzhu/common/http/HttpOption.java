package com.gitzhu.common.http;

import com.google.common.collect.ImmutableMap;
import com.ning.http.client.ProxyServer;
import org.apache.commons.collections.MapUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author michael
 * qiuxu.zhu@gmail.com
 */
public class HttpOption {

    private Map<String, String> headers;
    private ProxyServer proxyServer;
    private Map<String, String> postFormData;
    private String postBodyData;

    public Map<String, String> getHeaders(){
        if (headers == null) {
            headers = Collections.emptyMap();
        }
        return ImmutableMap.copyOf(headers);
    }

    public synchronized HttpOption addHeaders(String key, String value){
        if (headers == null){
            headers = Collections.emptyMap();
        }
        headers.put(key, value);
        return this;
    }

    public synchronized HttpOption addMultiHeaders(Map<String, String> newHeaders) {
        if (headers == null){
            headers = Collections.emptyMap();
        }
        headers.putAll(newHeaders);
        return this;
    }

    public HttpOption setProxy(String host, int port){
        proxyServer = new ProxyServer(host, port);
        return this;
    }

    public ProxyServer getProxy(){
        return proxyServer;
    }

    public synchronized HttpOption addPostFormData(String key, String value){
        if (postFormData == null){
            postFormData = new HashMap<String, String>();
        }
        postFormData.put(key, value);
        return this;
    }

    public Map<String, String> getPostFormData(){
        if(postFormData == null){
            postFormData = Collections.emptyMap();
        }
        return ImmutableMap.copyOf(postFormData);
    }

    public String getPostBodyData(){
        return postBodyData;
    }

    public HttpOption setPostBodyData(String postBodyData){
        this.postBodyData = postBodyData;
        return this;
    }

}

