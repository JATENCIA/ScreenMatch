package com.atencia.ScreenMatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConvertData {

    public <T> T getData(String json, Class<T> tClass) throws JsonProcessingException;

}
