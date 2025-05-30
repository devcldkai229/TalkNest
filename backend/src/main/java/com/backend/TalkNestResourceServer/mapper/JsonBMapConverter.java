package com.backend.TalkNestResourceServer.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = false)
public class JsonBMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return objectMapper.writeValueAsString(stringObjectMap);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not convert map to JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, HashMap.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not convert JSON to map", e);
        }
    }
}
