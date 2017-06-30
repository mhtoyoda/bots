package com.fiveware.dsl.excel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by valdisnei on 30/06/17.
 */
public class BuilderExcel {

    private ToHtml toHtml;

    public BuilderExcel() {
    }

    public BuilderExcel convert(String fileXls, String fileHtml) throws IOException {
        this.toHtml = ToHtml.create(fileXls, new PrintWriter(new FileWriter(fileHtml)));
        toHtml.setCompleteHTML(true);
        return this;
    }

    public void buildToFile() throws IOException {
        this.toHtml.printPage();
    }
}
