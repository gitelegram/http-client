package com.gitzhu.common.http.json;

import com.gitzhu.common.http.SimpleClient;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;

/**
 * Created by qiuxu.zhu on 15-12-20.
 */
public class SimpleJsonClient extends SimpleClient {

    private static final ObjectMapper mapper = new ObjectMapper();

    static{
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);

        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        mapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, false);

        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

    }

    public <T> T get(String url, Class<T> clazz) throws IOException {
        CloseableHttpResponse response = get(url);
        StatusLine status = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (status != null && status.getStatusCode() == 200){
            String content = EntityUtils.toString(entity, "UTF-8");
            return mapper.readValue(content, clazz);
        }
        return null;
    }

    public <T> T post(String url, Class<T> clazz) throws IOException {
        CloseableHttpResponse response = post(url);
        StatusLine status = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        if(status != null && status.getStatusCode() != 200){
            String content = EntityUtils.toString(entity, "UTF-8");
            return mapper.readValue(content, clazz);
        }
        return null;
    }
}
