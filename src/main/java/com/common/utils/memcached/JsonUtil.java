package com.common.utils.memcached;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Google Json工具类.
 *
 *
 */
public final class JsonUtil {

    private JsonUtil() {

    }

    private static class SingletonHolder {
        private static final ObjectMapper INSTANCE = new ObjectMapper();

    }

    /**
     * 获取Gson.
     *
     * @return
     */
    public static ObjectMapper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 对象转json.
     *
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static String toJson(Object o) throws JsonProcessingException {
        return getInstance().writeValueAsString(o);
    }


    /**
     * 转换成json bytes[].
     *
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static byte[] toJsonbytes(Object o) throws JsonProcessingException {
        return getInstance().writeValueAsBytes(o);
    }


    /**
     * Json转对象.
     *
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public static <T> T fromJsonbytes(byte[] jsonbytes, Class<T> clazz) throws
        JsonParseException, JsonMappingException, IOException {
        if (jsonbytes == null || jsonbytes.length == 0) {
            return  null;
        }
        return getInstance().readValue(jsonbytes, clazz);
    }

    /**
     * Json转对象.
     *
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws
        JsonParseException, JsonMappingException, IOException {
        if (StringUtils.isBlank(json)) {
            return  null;
        }
        return getInstance().readValue(json, clazz);
    }

}
