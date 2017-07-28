package com.fiveware.dsl.pdf;

import com.fiveware.dsl.TypeSearch;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by valdisnei on 28/06/17.
 */
class SearchImpl implements Search {

    static Logger logger = LoggerFactory.getLogger(SearchImpl.class);
    private String search;
    private Pattern compile;
    private Pdf pdf;
    private boolean isNext;
    private boolean noSpace;

    public SearchImpl(Pdf pdf) {
        this.pdf = pdf;
    }

    SearchImpl(String search, Pattern compile, Pdf pdf) {
        this.search = search;
        this.compile = compile;
        this.pdf = pdf;
    }

    @Override
    public Search next() {
        this.isNext = true;
        return this;
    }

    @Override
    public Search noSpace() {
        this.noSpace = true;
        return this;
    }

    @Override
    public Search seek(String search, TypeSearch typeSearch){
        return new SearchImpl(search, Pattern.compile(typeSearch.getRegex()),this.pdf);
    }

    @Override
    public Search seek(String search, String typeSearch){
        return new SearchImpl(search, Pattern.compile(typeSearch),this.pdf);
    }

    @Override
    public Search seek(String search, Pattern pattern){
        return new SearchImpl(search, pattern,this.pdf);
    }


    @Override
    public String build() {
        String searchRegex = search + compile.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(this.pdf.getText());

        if (matcher.find())
            return matcher.group().replace(search, "").trim();
        else
            return null;
    }

    @Override
    public String next(int nNext) {
        String searchRegex = search + compile.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(this.pdf.getText());

        String result=null;

        for (int i = 0; i < nNext; i++) {
            if (matcher.find())
                result = matcher.group().replace(search, "").trim();
        }

        return result;
    }

    @Override
    public List<String> list() {
        String searchRegex = search + compile.pattern();
        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(this.pdf.getText());


        List<String> list = Lists.newArrayList();

        while (matcher.find())
                list.add(matcher.group().replace(search, "").trim());

        return list;
    }


}
