package com.fiveware.dsl.html;

import com.fiveware.dsl.pdf.core.PageIterator;
import com.fiveware.dsl.pdf.BuilderPDF;
import com.fiveware.dsl.pdf.BuilderSearch;
import com.fiveware.dsl.TypeSearch;

import java.io.IOException;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderHtml {

    private BuilderExtractHtml builderExtractHtml;

    private ConvertHtmlToPdf convertHtmlToPdf;
    private final BuilderPDF builderPDF;

    public BuilderHtml(BuilderPDF builderPDF) {
        this.builderPDF = builderPDF;
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

    public BuilderSearch search(String search, TypeSearch typeSearch) {
        convertHtmlToPdf.buildToFile();
        builderPDF.open(convertHtmlToPdf.getOutPut());
        BuilderSearch builderSearch = new BuilderSearch(builderPDF);
        return builderSearch.search(search,typeSearch);
    }

    public void buildToFile() {
        convertHtmlToPdf.buildToFile();
    }

    public PageIterator buildToPages() {
        return convertHtmlToPdf.buildToPages();
    }
}
