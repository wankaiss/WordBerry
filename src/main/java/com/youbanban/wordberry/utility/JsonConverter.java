package com.youbanban.wordberry.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private static final ObjectMapper mapper = new ObjectMapper();
   
    public static <T> T readValue(String str, Class<T> clazz) throws Exception {
        return mapper.readValue(str, clazz);
    }

    public static <T> T readValue(String str, TypeReference<T> t) throws Exception {
        return mapper.readValue(str, t);
    }

    public static String toJson(Object o) throws Exception {
        return mapper.writeValueAsString(o);
    }

    public static String toPrettyJson(Object o) throws Exception {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }
}
