package com.fiveware.dsl;

import com.fiveware.core.PageIterator;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderHtml {

    private ConvertHtmlToPdf convertHtmlToPdf;
    private final BuilderPDF builderPDF;

    public BuilderHtml(BuilderPDF builderPDF) {
        this.builderPDF = builderPDF;
    }

    public BuilderHtml open(String url) {
        convertHtmlToPdf = new ConvertHtmlToPdf(url);
        return this;
    }

    public BuilderHtml outPutFile(String file) {
        convertHtmlToPdf.setOutPut(file);
        return this;
    }

    public BuilderSearch search(String search,TypeSearch typeSearch) {
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
