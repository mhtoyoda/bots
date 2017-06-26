package com.fiveware;


import com.fiveware.extractPDF.Page;
import com.fiveware.extractPDF.PageIterator;
import com.fiveware.extractPDF.RectangularTextContainer;
import com.fiveware.extractPDF.Table;
import com.fiveware.extractPDF.extractors.BasicExtractionAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by valdisnei on 23/06/17.
 */
public class Extract {
    static Logger logger= LoggerFactory.getLogger(Extract.class);

    private final static Extract builderPDF = new Extract();

    private PageIterator pageIterator;
    private Page page;
    private Pattern compile;
    private String search;

    private StringBuilder builder = new StringBuilder();

    public Extract() {
    }

    public Extract(String pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }


    public Extract(String pathFIle, int numberPage) {
        try {
            main(pathFIle,Integer.valueOf(numberPage));
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private void main(String pathFile) throws IOException {
        pageIterator = UtilsPages.forEach(pathFile);
    }

    private void main(Object ... pathFile) throws IOException {
        page = UtilsPages.getPage(pathFile[0].toString(),
                Integer.valueOf((Integer) pathFile[1]).intValue());
    }

    public Extract search(String search, TypeSearch typeSearch){
        this.compile = Pattern.compile(typeSearch.getRegex());
        this.search=search;
        return this;
    }

    public Extract search(String search, Pattern compile){
        this.search=search;
        this.compile=compile;
        return this;
    }
    public Extract search(String search, String regex){
        this.search=search;
        this.compile=Pattern.compile(regex);
        return this;
    }

    public Search search(Map search, Class className){
        return new Search(search,className,this);
    }

    public String build(){
        return Objects.isNull(page)?searchField(pageIterator, this.search, compile):
                                        searchField(page, this.search, compile);
    }


    private String searchField(PageIterator pages, String search, Pattern pattern) {
        builder(pages);
        String searchRegex = search+pattern.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(builder.toString());
        if (matcher.find())
            return matcher.group().replace(search, "");
        else
            return getResult(pages,search,compile);

    }


    private String searchField(Page page, String search, Pattern pattern) {
        builder(page);

        String searchRegex = search+pattern.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(builder.toString());
        if (matcher.find()) {
            return matcher.group().replace(search, "").trim();
        }else{
            return getResult(page,search,compile);
        }
    }

    private void builder(PageIterator pages) {
        while (pages.hasNext()){
            Page page =pages.next();
            builder(page);
        }
    }


    private void builder(Page page) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
        bea.extract(page).forEach((table)->{
            table.getRows().forEach((row)->{
                row.forEach((col)->builder.append(col.getText().toUpperCase()));
            });
        });
    }

    private String getResult(Page page, String search, Pattern pattern) {
        return processPage(search, pattern, page);

    }

    private String getResult(PageIterator pages, String search, Pattern pattern) {
        while (pages.hasNext()) {
            Page page = pages.next();
            return processPage(search, pattern, page);
        }
        return null;
    }

    private String processPage(String search, Pattern pattern, Page page) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
        List<Table> extract = bea.extract(page);
        for (int i = 0; i < extract.size(); i++) {
            Table table = extract.get(i);
            List<List<RectangularTextContainer>> rows = table.getRows();
            for (int j = 0; j < rows.size(); j++) {

                List<RectangularTextContainer> rectangularTextContainers = rows.get(j);
                for (int k = 0; k < rectangularTextContainers.size(); k++) {

                    String text = table.getCell(j, k).getText();

                    boolean contain = text.toUpperCase().contains(search.toUpperCase());
                    if (contain)
                        return builder(rows, table, pattern, j);
                }
            }
        }

        return null;
    }

    private String builder(List<List<RectangularTextContainer>> rows,Table table, Pattern pattern, int j) {

        String compile = pattern.pattern().replace(search, "");
        for (; j < rows.size(); j++) {
            List<RectangularTextContainer> cols = rows.get(j);

            for (int k = 0; k < cols.size(); k++) {
                String text = table.getCell(j, k).getText();
                Matcher matcher = Pattern.compile(compile).matcher(text);
                if (matcher.find())
                    return matcher.group();

            }
        }

        return null;
    }

    public static FromTo FromTo(String key, Object obj){
        return new FromTo(key,obj);
    }

    public static class FromTo {
        String key;
        Object value;

        public FromTo(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }


}
