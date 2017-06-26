package com.fiveware;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by valdisnei on 26/06/17.
 */
public class Search {

    static Logger logger = LoggerFactory.getLogger(Search.class);

    final Map searchmap;
    final Class className;
    final Extract extractPDF;

    Search(Map searchmap, Class className,Extract extractPDF) {
        this.searchmap = searchmap;
        this.className = className;
        this.extractPDF=extractPDF;
    }


    public Object build(){
        try {
            Object myObject = this.className.newInstance();
            Class<?> aClass = myObject.getClass();

            BiConsumer biConsumer = getBiConsumer(myObject, aClass);

            this.searchmap.forEach((k,v)->biConsumer.accept(k,v));

            return myObject;


        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("{}",e);

        }
        return null;
    }

    private BiConsumer getBiConsumer(Object myObject, Class<?> aClass) {
        return (k, v)->{
                    try {
                        String key;
                        Object fieldPDF;
                        if (k instanceof Extract.FromTo){
                            key=((Extract.FromTo) k).getValue().toString();
                            fieldPDF = k;
                        }else{
                            key=k.toString();
                            fieldPDF = key.toString();
                        }

                        Field field= myObject.getClass().getDeclaredField(key);
                        Method setter=aClass.getMethod("set"+ WordUtils.capitalizeFully(key),field.getType());


                        Extract search = null;
                        if (this.searchmap.get(fieldPDF) instanceof TypeSearch) {
                            Object typeSearch =  (TypeSearch) this.searchmap.get(fieldPDF);
                            search = this.extractPDF.search(fieldPDF.toString().toUpperCase(), (TypeSearch) typeSearch);

                        }else if(this.searchmap.get(fieldPDF) instanceof String){
                            String typeSearch = (String) this.searchmap.get(fieldPDF);
                            search = this.extractPDF.search(fieldPDF.toString().toUpperCase(), typeSearch);
                        }

                        setter.invoke(myObject, MoreObjects.firstNonNull(search.build(),"").trim());


                    } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException |
                            InvocationTargetException e) {
                        logger.error("{}",e);
                    }
                };
    }

}
