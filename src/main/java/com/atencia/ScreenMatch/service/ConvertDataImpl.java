package com.atencia.ScreenMatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertDataImpl implements ConvertData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> tClass) throws JsonProcessingException {
        return objectMapper.readValue(json, tClass);
    }

}
