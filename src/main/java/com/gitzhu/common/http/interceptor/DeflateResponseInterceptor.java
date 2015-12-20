package com.gitzhu.common.http.interceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by qiuxu.zhu on 15-12-20.
 */
public class DeflateResponseInterceptor implements HttpResponseInterceptor {

    public static final DeflateRequestInterceptor instance = new DeflateRequestInterceptor();

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {

    }
}
