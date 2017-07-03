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
        fields = this.object.getClass().getDeclaredFields();
        createHeader(reference, fields);
        createList(reference, fields);

        return this;
    }

    private void createList(String reference, Field[] fields) {
        CellReference cellReference = new CellReference(reference);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(cellReference.getRow(),cellReference.getCol(),
                cellReference.getRow(),cellReference.getCol()+fields.length);
        Row row = this.excel.getWorksheet().createRow(1);
        AtomicInteger column= new AtomicInteger(-1);
        Arrays.stream(fields).forEach((field)->{

            Cell cell = row.createCell(cellRangeAddress.getFirstColumn()+column.incrementAndGet());
            try {


                Object value = getValue(field);
                if (value instanceof Double)
                    cell.setCellValue((Double) value);

                if (value instanceof String){
                    cell.setCellValue((String) value);
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
    public void build() throws IOException {
        this.excel.build();
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
