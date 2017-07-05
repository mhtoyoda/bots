package com.fiveware.dsl.html;

import com.fiveware.dsl.TypeSearch;
import com.fiveware.dsl.pdf.Pdf;
import com.fiveware.dsl.pdf.Search;
import com.fiveware.dsl.pdf.core.PageIterator;

import java.io.IOException;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderHtml {

    private BuilderExtractHtml builderExtractHtml;

    private ConvertHtmlToPdf convertHtmlToPdf;
    private final Pdf pdf;

    public BuilderHtml(Pdf builderPDF) {
        this.pdf = builderPDF;
    }

    public BuilderHtml open(String url) {
        convertHtmlToPdf = new ConvertHtmlToPdf(url);
        return this;
    }

    public BuilderExtractHtml file(String fileHtml) throws IOException {
        builderExtractHtml = new BuilderExtractHtml();
        return builderExtractHtml.file(fileHtml);
    }

    public BuilderHtml outPutFile(String file) {
        convertHtmlToPdf.setOutPut(file);
        return this;
    }





    public Search search(String textSearch, TypeSearch typeSearch) {
        convertHtmlToPdf.buildToFile();
        pdf.open(convertHtmlToPdf.getOutPut());
        Search search = pdf.getSearch();
        return search.seek(textSearch,typeSearch);
    }

    public void buildToFile() {
        convertHtmlToPdf.buildToFile();
    }

    public PageIterator buildToPages() {
        return convertHtmlToPdf.buildToPages();
    }
}
