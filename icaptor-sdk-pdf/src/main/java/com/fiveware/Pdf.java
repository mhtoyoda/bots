package com.fiveware;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by valdisnei on 26/06/17.
 */
public interface Pdf {
    Extract search(String search, TypeSearch typeSearch);
    Extract search(String search, String typeSearch);
    Extract search(String search, Pattern pattern);
    Search search(Map map, Class pattern);
}
