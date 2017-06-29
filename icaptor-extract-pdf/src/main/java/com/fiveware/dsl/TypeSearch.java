package com.fiveware.dsl;

/**
 * Created by valdisnei on 23/06/17.
 */
public enum TypeSearch {
    DATE("[0-9]{2}/[0-9]{2}/[0-9]{4}"),
    MONEY("\\s?(R\\$ ?\\d{1,3}(\\.\\d{3})*,\\d{2})"),
    NEGATIVE_MONEY("(-R\\$ ?\\d{1,3}(\\.\\d{3})*,\\d{2})"),
    PERCENT("\\s?(\\d{1,3}(\\.\\d{3})*,\\d{2}%)"),
    CNPJ("\\s?([0-9]{2}.[0-9]{3}.[0-9]{3}\\/[0-9]{4}-[0-9]{2})"),
    INSCR("[0-9]{3}.[0-9]{3}.[0-9]{7}-[0-9]"),
    CFOP("(\\d\\.\\d{3})"),
    NUMBER("([0-9]{12})"),
    CEP("[0-9]{5}\\-?[0-9]{3}");

    private String regex;


    TypeSearch(String s) {
        this.regex=s;
    }

    public String getRegex() {
        return regex;
    }
}
