package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;
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
class WriteToPojo implements Write{

    static Logger logger = LoggerFactory.getLogger(WriteToPojo.class);


    private Map searchmap;
    private Class className;
    private Search builderSearch;

    protected WriteToPojo(Search pdf) {
        this.builderSearch =pdf;
    }

    @Override
    public Write map(Map map, Class className) {
        this.searchmap = map;
        this.className = className;
        return this;
    }

    @Override
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
                        if (k instanceof Helpers.FromTo){
                            key=((Helpers.FromTo) k).getValue().toString();
                            fieldPDF = k;
                        }else{
                            key=k.toString();
                            fieldPDF = key.toString();
                        }

                        Field field= myObject.getClass().getDeclaredField(key);
                        Method setter=aClass.getMethod("set"+ WordUtils.capitalizeFully(key),field.getType());


                        Search extract = null;
                        if (this.searchmap.get(fieldPDF) instanceof TypeSearch) {
                            Object typeSearch =  (TypeSearch) this.searchmap.get(fieldPDF);
                            extract = this.builderSearch.seek(fieldPDF.toString().toUpperCase(), (TypeSearch) typeSearch);

                        }else if(this.searchmap.get(fieldPDF) instanceof String){
                            String typeSearch = (String) this.searchmap.get(fieldPDF);
                            extract = this.builderSearch.seek(fieldPDF.toString().toUpperCase(), typeSearch);
                        }

                        setter.invoke(myObject, MoreObjects.firstNonNull(extract.build(),"").trim());


                    } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException |
                            InvocationTargetException e) {
                        logger.error("{}",e);
                    }
                };
    }

}
