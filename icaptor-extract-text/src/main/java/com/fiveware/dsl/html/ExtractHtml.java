package com.fiveware.dsl.html;

import java.io.IOException;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface ExtractHtml {


    static ExtractHtml getInstance(){
        return new ExtractTextFromHtml();
    }

    ExtractHtml file(String fileInput) throws IOException;

    ExtractHtml selectElement(String element);

    String text(int numberElement);
}
