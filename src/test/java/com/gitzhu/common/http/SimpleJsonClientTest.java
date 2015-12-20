package com.gitzhu.common.http;

import com.gitzhu.common.http.json.SimpleJsonClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by qiuxu.zhu on 15-12-20.
 */
public class SimpleJsonClientTest {

    private SimpleJsonClient client;

    @Before
    public void init() {
        client = new SimpleJsonClient();
    }

    @Test
    //TODO
    public void testGet() throws IOException {
        client.get("http://www.baidu.com", Class.class);
    }
}
