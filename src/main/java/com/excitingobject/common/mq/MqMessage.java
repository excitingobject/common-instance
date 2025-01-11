package com.excitingobject.common.mq;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class MqMessage<T> {

    private T data;
    private static final String ENCODING_UTF8 = "UTF-8";

    public MqMessage() {
        super();
    }

    public MqMessage(T data) {
        this.data = data;
    }

    public MqMessage(String message, Class<T> typeClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = new String(Base64.getDecoder().decode(message.getBytes()), ENCODING_UTF8);
            this.data = mapper.readValue(json, typeClass);
        } catch (Exception e) {
            this.data = null;
        }
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(data);
            return new String(Base64.getEncoder().encode(message.getBytes()), ENCODING_UTF8);
        } catch (Exception e) {
            return null;
        }
    }
}