package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Pdf {

    Pdf open(String pathFIle);

    Pdf open(String pathFIle, int numberPage);

    Write writeObject();

    Search search(String search, TypeSearch typeSearch);

    Search search(TypeSearch typeSearch);

    Search search(String search, String regex);

    Search search(String regex);

    Search getSearch();

    String getText();

    void append(String text);
}
