package com.gitzhu.common.http.strategy;

import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author michael
 * qiuxu.zhu@gmail.com
 */
public class Strategies {

    private Strategies(){}

    public static void keepAlive(HttpClientBuilder builder, long time){
        builder.setKeepAliveStrategy(new SimpleKeepAliveStrategy(time));
    }
}
