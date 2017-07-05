package com.fiveware.dsl.excel;

import com.fiveware.dsl.excel.IExcel.Formula;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by valdisnei on 03/07/17.
 */
class ExcelConvert implements IExcelConvert {

    static Logger logger = LoggerFactory.getLogger(ExcelConvert.class);

    private Excel excel;

    private  Object obj;

    public ExcelConvert(Excel excel) {
        this.excel =excel;
    }


    @Override
    public IExcelConvert convert(String reference, String attributeClass, Class _aClass) {
        try {
            this.excel.cell(reference);

            if (obj==null) obj = _aClass.newInstance();

            Class<?> aClass = obj.getClass();

            Field field= obj.getClass().getDeclaredField(attributeClass);
            Method setter=aClass.getMethod("set"+ WordUtils.capitalizeFully(attributeClass),field.getType());

            setter.invoke(obj, this.excel.getMapCell().get(reference));

        } catch (InstantiationException | IllegalAccessException |NoSuchMethodException
                | InvocationTargetException |NoSuchFieldException e) {
            logger.error("{}",e);
        }
        return this;
    }

    @Override
    public IExcelConvert convert(Formula formula, String attributeClass, Class _aClass) {
        return convert(formula.getReference(),attributeClass,_aClass);
    }


    @Override
    public Object toObject() {
        return obj;
    }
}
