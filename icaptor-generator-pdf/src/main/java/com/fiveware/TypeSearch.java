package com.fiveware;

/**
 * Created by valdisnei on 23/06/17.
 */
public enum TypeSearch {
    VENCIMENTO("[0-9]{2}/[0-9]{2}/[0-9]{4}"),
    MONETARY("(R\\$ ?\\d{1,3}(\\.\\d{3})*,\\d{2})"),
    MONETARY_WITHOUT_$("(\\d{1,3}(\\.\\d{3})*,\\d{2})"),
    MONETARY_WITHOUT_$_NEGATIVE("(\\-\\d{1,3}(\\.\\d{3})*,\\d{2})"),
    PERCENT("(\\d{1,3}(\\.\\d{3})*,\\d{2}%)"),
    CNPJ("([0-9]{2}.[0-9]{3}.[0-9]{3}/[0-9]{4}-[0-9]{2})"),
    CFOP("(\\d\\.\\d{3})");

    private String regex;


    TypeSearch(String s) {
        this.regex=s;
    }

    public String getRegex() {
        return regex;
    }
}
