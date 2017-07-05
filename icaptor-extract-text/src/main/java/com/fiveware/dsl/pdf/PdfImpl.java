package com.fiveware.dsl.pdf;


import com.fiveware.UtilsPages;
import com.fiveware.dsl.TypeSearch;
import com.fiveware.dsl.pdf.core.Page;
import com.fiveware.dsl.pdf.core.PageIterator;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;


/**
 * Created by valdisnei on 23/06/17.
 */
class PdfImpl implements Pdf{
    static Logger logger= LoggerFactory.getLogger(PdfImpl.class);

    private PageIterator pageIterator;
    private Page page;
    private StringBuilder builder = new StringBuilder();
    private String pathFile;

    private PdfImpl() {
    }

    public static Pdf getPdf() {
        return new PdfImpl();
    }


    @Override
    public Pdf open(String pathFIle) {
        return new PdfImpl(pathFIle);
    }
    @Override
    public Pdf open(String pathFIle, int numberPage) {
        return new PdfImpl(pathFIle,numberPage);
    }

    protected PdfImpl(String pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    protected PdfImpl(String pathFIle, int numberPage) {
        try {
            main(pathFIle,Integer.valueOf(numberPage));
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private void main(String pathFile) throws IOException {
        this.pathFile=pathFile;
        pageIterator = UtilsPages.pages(pathFile);
    }

    private void main(Object ... pathFile) throws IOException {
        this.pathFile=pathFile[0].toString();
        page = UtilsPages.getPage(pathFile[0].toString(),
                Integer.valueOf((Integer) pathFile[1]).intValue());
    }


    @Override
    public Write writeObject() {
        Write writeToPojo = new WriteToPojo(new SearchImpl(this));
        return writeToPojo;
    }


    @Override
    public Search search(String search, TypeSearch typeSearch) {
        Search builderSearch = new SearchImpl(this);
        return builderSearch.seek(search.toUpperCase(),typeSearch);
    }

    @Override
    public Search search(String search, String regex) {
        Search builderSearch = new SearchImpl(this);
        return builderSearch.seek(search.toUpperCase(),regex);
    }

    @Override
    public Search search(String search, Pattern pattern) {
        Search builderSearch = new SearchImpl(this);
        return builderSearch.seek(search.toUpperCase(),pattern);
    }


    @Override
    public Search getSearch(){
        return new SearchImpl(this);
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public PageIterator getPageIterator() {
        return pageIterator;
    }


    private StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getPathFile() {
        return pathFile;
    }

    @Override
    public String getText(){
        return  getBuilder().toString();
    }

    @Override
    public void append(String text){
        getBuilder().append(text);
    }


    @Override
    public boolean isEmptyText(){
        return  Strings.isNullOrEmpty(getBuilder().toString());
    }



}
