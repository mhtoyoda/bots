package com.fiveware.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.file.RecordLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by valdisnei on 25/05/17.
 */

@Component
public class ConverterRecordLine {

    @Autowired
    private ObjectMapper oMapper;

    public RecordLine converter(Object object){
        Map<String, String> map = oMapper.convertValue(object, Map.class);
        return new RecordLine(map);
    }
}
