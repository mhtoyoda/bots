package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;
import com.fiveware.dsl.pdf.core.Page;
import com.fiveware.dsl.pdf.core.PageIterator;

import java.util.regex.Pattern;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Pdf {

    Pdf open(String pathFIle);

    Pdf open(String pathFIle, int numberPage);

    Write writeObject();

    Search search(String search, TypeSearch typeSearch);

    Search search(String search, String regex);

    Search search(String search, Pattern pattern);

    Search getSearch();

    Page getPage();

    PageIterator getPageIterator();

    String getPathFile();

    String getText();

    void append(String text);

    boolean isEmptyText();
}
