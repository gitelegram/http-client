package com.gitzhu.common.http.strategy;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * Created by qiuxu.zhu on 15-12-20.
 */
public class SimpleKeepAliveStrategy extends DefaultConnectionKeepAliveStrategy{

    private final long time;

    public SimpleKeepAliveStrategy(long time){
        this.time = time;
    }

    @Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context){
        long time = super.getKeepAliveDuration(response, context);
        if (time == -1){
            time = this.time;
        }
        return time;
    }

}
