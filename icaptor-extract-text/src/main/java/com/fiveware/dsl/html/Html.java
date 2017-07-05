package com.fiveware.dsl.html;

import com.fiveware.dsl.TypeSearch;
import com.fiveware.dsl.pdf.Convert;
import com.fiveware.dsl.pdf.Search;

import java.io.IOException;
import java.net.URL;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Html {
    static Html getInstance() {
        return new ConvertHtmlToPdf();
    }

    Html open(String url);

    Html outPutFile(String file);

    Convert fromFile(String fileHtml);

    Convert fromUrl(URL url);

    Search search(String textSearch, TypeSearch typeSearch);

    ExtractHtml file(String fileHtml) throws IOException;

    void buildToFile();
}
