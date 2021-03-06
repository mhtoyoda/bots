package com.fiveware.dsl.html;

import java.io.IOException;

/**
 * Created by valdisnei on 23/06/17.
 */
public class BuilderExtractHtml {

    private ExtractTextFromHtml extractTextFromHtml;

    public BuilderExtractHtml() {
        this.extractTextFromHtml = new ExtractTextFromHtml();
    }

    public BuilderExtractHtml file(String fileHtml) throws IOException {
        extractTextFromHtml.file(fileHtml);
        return this;
    }

    public BuilderExtractHtml selectElement(String element){
        this.extractTextFromHtml.selectElement(element);
        return this;
    }

    public String text(int numberElement){
        return this.extractTextFromHtml.text(numberElement);
    }

    public String build(){
        return extractTextFromHtml.build();
    }


}
