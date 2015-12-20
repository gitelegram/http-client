package com.gitzhu.common.http.interceptor;

import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author michael
 * qiuxu.zhu@gmail.com
 */
public class Interceptors {

    private Interceptors(){}

    public static void gzip(HttpClientBuilder builder){
        builder.addInterceptorFirst(DeflateRequestInterceptor.instance);
        builder.addInterceptorFirst(DeflateResponseInterceptor.instance);
    }
}
