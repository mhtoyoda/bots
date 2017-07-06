package com.fiveware.dsl.excel;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by valdisnei on 03/07/17.
 */
class ExcelCreaterList implements IExcelCreaterList{


    private Class aClass;
    private Field[] fields;
    private Object object;

    private final Excel excel;

    public ExcelCreaterList(Excel excel) {
        this.excel = excel;
    }


    @Override
    public IExcelCreaterList readObject(String reference, Object object) throws IllegalAccessException, InstantiationException {
        this.object =object;
        if (this.object instanceof List)
            return generateListToSheet(reference);

        fields = this.object.getClass().getDeclaredFields();
        createHeader(reference, fields);
        createList(reference, fields);

        return this;
    }

    private IExcelCreaterList generateListToSheet(String reference) {
        List object = (List) this.object;
        Object o = object.get(0);
        fields = o.getClass().getDeclaredFields();
        createHeader(reference, fields);

        object.forEach((o1)->{
            this.object=o1;
            createList(reference,fields);
        });

        return this;
    }

    private void createList(String reference, Field[] fields) {
        CellReference cellReference = new CellReference(reference);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(cellReference.getRow(),cellReference.getCol(),
                cellReference.getRow(),cellReference.getCol()+fields.length);
        Row row = this.excel.getWorksheet().createRow(this.excel.getWorksheet().getLastRowNum()+1);
        AtomicInteger column= new AtomicInteger(-1);
        Arrays.stream(fields).forEach((field)->{

            Cell cell = row.createCell(cellRangeAddress.getFirstColumn()+column.incrementAndGet());
            try {


                Object value = getValue(field);
                if (value instanceof Double)
                    cell.setCellValue((Double) value);

                if (value instanceof String){
                    if (value.toString().startsWith("=")) {
                        value = ((String) value).substring(1, ((String) value).length());
                        cell.setCellFormula((String) value);
                    }else {
                        cell.setCellValue((String) value);
                    }
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }



    private Row createHeader(String reference, Field[] fields){
        CellReference cellReference = new CellReference(reference);

//        this.excel.buildCell(reference);
//
//        Cell cell1 = this.excel.getCell();


        CellRangeAddress cellRangeAddress = new CellRangeAddress(cellReference.getRow(),cellReference.getCol(),
                cellReference.getRow(),cellReference.getCol()+fields.length);
        Row row = this.excel.getWorksheet().createRow(0);

        AtomicInteger column= new AtomicInteger(-1);
        Arrays.stream(fields).forEach((field)->{

            Cell cell = row.createCell(cellRangeAddress.getFirstColumn()+column.incrementAndGet());
            cell.setCellValue(field.getName());
        });

        return row;
    }


    @Override
    public IExcel build() throws IOException {
        this.excel.build();
        return this.excel;
    }

    private Object getValue(Field field) throws IllegalAccessException, InstantiationException {
            try {
                Method setter= object.getClass().getMethod("get"+ WordUtils.capitalizeFully(field.getName()));
                System.out.println("methods = " + setter);

                return setter.invoke(object);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
    }

}
