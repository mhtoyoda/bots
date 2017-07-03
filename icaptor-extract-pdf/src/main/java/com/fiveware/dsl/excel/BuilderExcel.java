package com.fiveware.dsl.excel;

import java.io.IOException;

/**
 * Created by valdisnei on 30/06/17.
 */
public class BuilderExcel {

    private IExcel excel;


    public BuilderExcel() {
        excel = new Excel();
    }

    public IExcel getExcel() {
        return excel;
    }
}
