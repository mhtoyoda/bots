package com.fiveware.dsl;

import java.util.Map;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderConverter {

    private ConvertToPojo convertToPojo;


    protected BuilderConverter(ConvertToPojo convertToPojo) {
        this.convertToPojo = convertToPojo;
    }

    public BuilderConverter map(Map map, Class className) {
        convertToPojo.converter(map,className);
        return this;
    }


    public Object build() {
        return convertToPojo.build();
    }
}
