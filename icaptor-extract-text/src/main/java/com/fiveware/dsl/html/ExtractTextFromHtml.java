package com.fiveware.dsl.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by valdisnei on 27/06/17.
 */
class ExtractTextFromHtml implements ExtractHtml{

    static Logger logger = LoggerFactory.getLogger(ExtractTextFromHtml.class);
    private String fileInput;
    private Document document;
    private Elements elements;
    private StringBuilder htmlString;
    private boolean controlFirst;

    protected ExtractTextFromHtml() {
    }

    @Override
    public ExtractHtml file(String fileInput) throws IOException {
        this.fileInput= fileInput;
        StringBuilder br = getBufferedReader(fileInput);
        document = Jsoup.parse(br.toString(), "UTF-8");
        return this;
    }


    @Override
    public ExtractHtml selectElement(String element){
        elements = document.select(element); // a with href
        return this;
    }

    @Override
    public String text(int numberElement){
        Element element = elements.get(numberElement);
        return element.text();
    }

    public String build() {
        return elements.html();
    }

    private StringBuilder getBufferedReader(String fileInput) throws IOException {

        StringBuilder list = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileInput), Charset.forName("ISO8859_1"))) {

            br.lines().collect(Collectors.toList())
                    .forEach((s)->list.append(s));

        } catch (IOException e) {
            logger.error("{}",e);
        }

        return list;
    }



}
