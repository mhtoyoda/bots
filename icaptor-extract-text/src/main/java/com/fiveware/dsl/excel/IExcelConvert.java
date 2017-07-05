package com.fiveware.dsl.excel;

/**
 * Created by valdisnei on 03/07/17.
 */
public interface IExcelConvert {

    IExcelConvert convert(String reference, String attributeClass, Class _aClass);

    IExcelConvert convert(IExcel.Formula reference, String attributeClass, Class _aClass);

    Object toObject();
}
