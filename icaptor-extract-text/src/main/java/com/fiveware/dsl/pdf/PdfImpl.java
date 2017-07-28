package com.fiveware.dsl.pdf;


import com.fiveware.dsl.TypeSearch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


/**
 * Created by valdisnei on 23/06/17.
 */
class PdfImpl implements Pdf{
    static Logger logger= LoggerFactory.getLogger(PdfImpl.class);

    private StringBuilder builder = new StringBuilder();
    private String pathFile;

    private PDDocument document;
    private PDFTextStripper pdfStripper;

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
        document = PDDocument.load(new File(this.pathFile));
        pdfStripper = new PDFTextStripper();

    }

    private void main(Object ... pathFile) throws IOException {
        this.pathFile=pathFile[0].toString();
        document = PDDocument.load(new File(this.pathFile));
        pdfStripper = new PDFTextStripper();
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
    public Search search(TypeSearch typeSearch) {
        Search builderSearch = new SearchImpl(this);
        return builderSearch.seek("",typeSearch);
    }

    @Override
    public Search search(String search, String regex) {
        Search builderSearch = new SearchImpl(this);
        return builderSearch.seek(search.toUpperCase(),regex);
    }

    @Override
    public Search search(String regex) {
        Search builderSearch = new SearchImpl(this);
        return builderSearch.seek("",regex);
    }

    @Override
    public Search getSearch(){
        return new SearchImpl(this);
    }

    private StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getText() {
        StringBuilder builder = getBuilder();

        if (builder.length()==0){
            try {
                builder.append(pdfStripper.getText(document));
                return builder.toString();
            } catch (IOException e) {
                logger.error("{}",e);
            }
        }
        return  builder.toString();
    }

    @Override
    public void append(String text){
        getBuilder().append(text);
    }



}
