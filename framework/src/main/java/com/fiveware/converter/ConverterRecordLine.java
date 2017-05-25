package com.fiveware.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.model.OutTextRecord;

/**
 * Created by valdisnei on 25/05/17.
 */

@Component
public class ConverterRecordLine {

    @Autowired
    private ObjectMapper oMapper;

    @SuppressWarnings("unchecked")
	public OutTextRecord converter(Object object){
        Map<String, String> map = oMapper.convertValue(object, Map.class);
        return new OutTextRecord(map);
    }
}
