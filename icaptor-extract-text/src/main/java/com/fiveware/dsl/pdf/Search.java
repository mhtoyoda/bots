package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;

import java.util.List;
import java.util.regex.Pattern;

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

    String next(int nNext);

    List<String> list();
}
