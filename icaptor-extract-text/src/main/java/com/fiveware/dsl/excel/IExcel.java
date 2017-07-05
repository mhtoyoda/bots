package com.fiveware.dsl.excel;


import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by valdisnei on 7/1/17.
 */
public interface IExcel {

    IExcel open(String fileExcel) throws IOException;

    IExcel createSheet(String sheet);

    IExcel deleteSheet();

    IExcel sheet(int sheet);

    IExcel cell(Excel.Formula referenceCell);

    String text();

    Integer totalSheets();

    String getFormula();

    IExcel formula(String referenceCell, String formula);

    IExcel cell(String referenceCell, String formula);

    IExcel cell(String referenceCell, Double value);

    IExcel cell(String referenceCell, LocalDate value);

    IExcel sheet(String sheet);

    IExcel cell(String referenceCell);

    IExcelConvert convert(String referenceCell, String attribute, Class aClass);

    Object build() throws IOException;

    IExcelCreaterList readObject(String referenceCell, Object aClass) throws InstantiationException, IllegalAccessException;

    Object getValue(Cell cell);


    static Formula Formula(String reference, Object formula){
        return new Formula(reference,formula);
    }

    static class Formula {
        String reference;
        Object formula;

        public Formula(String key, Object value) {
            this.reference = key;
            this.formula = value;
        }

        public String getReference() {
            return reference;
        }

        public Object getFormula() {
            return formula;
        }
    }
}
