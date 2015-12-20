package com.gitzhu.common.http.interceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by qiuxu.zhu on 15-12-20.
 */
public class DeflateRequestInterceptor implements HttpRequestInterceptor {

    public static final DeflateRequestInterceptor instance = new DeflateRequestInterceptor();

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {

    }
}
