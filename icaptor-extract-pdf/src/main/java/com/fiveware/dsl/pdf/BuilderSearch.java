package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;

import java.util.regex.Pattern;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderSearch {

    private Search search;
    private final BuilderPDF builderPDF;

    public BuilderSearch(BuilderPDF builderPDF) {
        this.builderPDF = builderPDF;
    }


    public BuilderSearch search(String search,TypeSearch typeSearch){
        this.search=new Search(search, Pattern.compile(typeSearch.getRegex()),this.builderPDF);
        return this;
    }

    public BuilderSearch search(String search,String typeSearch){
        this.search=new Search(search, Pattern.compile(typeSearch),this.builderPDF);
        return this;
    }

    public BuilderSearch search(String search,Pattern pattern){
        this.search=new Search(search, pattern,this.builderPDF);
        return this;
    }

    public BuilderSearch next(){
        this.search.next(true);
        return this;
    }

    public String build(){
        return this.search.build();
    }

}
