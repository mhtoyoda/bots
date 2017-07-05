package com.fiveware.dsl.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Convert {

    byte[] requestHtmlToPdf(URL url) throws InvalidParameterException, MalformedURLException, IOException;

    static Convert convert(String fileHtml) {
        return new HtmlToPDFImpl(fileHtml);
    }

    static Convert convert(URL url) {
        return new HtmlToPDFImpl(url);
    }


    Convert toPdf();

    byte[] buildToBytes();

    File buildToFile(String outFile);
}
