package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;

import java.util.regex.Pattern;

/**
 * Created by valdisnei on 05/07/17.
 */
public interface Search {

    void next(boolean isNext);

    Search seek(String search, TypeSearch typeSearch);

    Search seek(String search, String typeSearch);

    Search seek(String search, Pattern pattern);

    String build();

}
