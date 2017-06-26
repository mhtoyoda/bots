package com.fiveware;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by valdisnei on 26/06/17.
 */
public class PdfImpl implements Pdf{

    private Extract extract;


    public PdfImpl(String pathFIle){
        this.extract = new Extract(pathFIle);
    }

    public PdfImpl(String pathFIle, int numberPage) {
        this.extract = new Extract(pathFIle,numberPage);
    }

    @Override
    public Extract search(String search, TypeSearch typeSearch) {
        return extract.search(search,typeSearch);
    }

    @Override
    public Extract search(String search, String typeSearch) {
        return extract.search(search,typeSearch);
    }

    @Override
    public Extract search(String search, Pattern pattern) {
        return extract.search(search,pattern);
    }

    @Override
    public Search search(Map map, Class myClass) {
        return extract.search(map,myClass);
    }
}
