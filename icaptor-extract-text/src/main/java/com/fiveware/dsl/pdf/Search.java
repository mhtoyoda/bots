package com.fiveware.dsl.pdf;

import java.util.regex.Pattern;

import com.fiveware.dsl.TypeSearch;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Search {

    Search next();

    Search noSpace();

    Search seek(String search, TypeSearch typeSearch);

    Search seek(String search, String typeSearch);

    Search seek(String search, Pattern pattern);

    String build();

}
