package com.fiveware.dsl.excel;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by valdisnei on 7/1/17.
 */
public interface IExcel {

    IExcel open(String fileExcel) throws IOException;

    IExcel sheet(int sheet);

    IExcel cell(String referenceCell);

    String text();

    Integer totalSheets();

    String getFormula();

    IExcel formula(String referenceCell, String formula);

    IExcel cell(String referenceCell, String formula);

    IExcel cell(String referenceCell, Double value);

    IExcel cell(String referenceCell, LocalDate value);

    Object build() throws IOException;
}
