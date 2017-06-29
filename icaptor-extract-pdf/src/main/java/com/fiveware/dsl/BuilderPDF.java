package com.fiveware.dsl;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderPDF {

    private Pdf pdf;
    private ConvertToPojo convertToPojo;

    private Extract extract;

    protected BuilderPDF(Extract extract) {
        this.extract =extract;
    }

    public BuilderPDF open(String pathFIle) {
        pdf = new Pdf(pathFIle);
        return this;
    }

    public BuilderPDF open(String pathFIle,int numberPage) {
        pdf = new Pdf(pathFIle,numberPage);
        return this;
    }

    public BuilderConverter converter() {
        BuilderSearch builderSearch = new BuilderSearch(this);
        convertToPojo = new ConvertToPojo(builderSearch);
        BuilderConverter builderConverter = new BuilderConverter(convertToPojo);
        return builderConverter;
    }

    public BuilderSearch search(String search,TypeSearch typeSearch) {
        BuilderSearch builderSearch = new BuilderSearch(this);
        return builderSearch.search(search.toUpperCase(),typeSearch);
    }

    public BuilderSearch search(String search,String regex) {
        BuilderSearch builderSearch = new BuilderSearch(this);
        return builderSearch.search(search.toUpperCase(),regex);
    }

    public BuilderSearch search(String search,Pattern pattern) {
        BuilderSearch builderSearch = new BuilderSearch(this);
        return builderSearch.search(search.toUpperCase(),pattern);
    }

    protected Pdf getPdf() {
        return pdf;
    }

    protected boolean isEmptyText(){
        return  Strings.isNullOrEmpty(getPdf().getBuilder().toString());
    }
    protected String getText(){
        return  getPdf().getBuilder().toString();
    }

    protected void append(String text){
        getPdf().getBuilder().append(text);
    }
}
