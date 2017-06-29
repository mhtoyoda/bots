package com.fiveware.dsl;

import com.fiveware.UtilsPages;
import com.fiveware.core.Page;
import com.fiveware.core.PageIterator;
import com.fiveware.core.RectangularTextContainer;
import com.fiveware.core.Table;
import com.fiveware.core.extractors.BasicExtractionAlgorithm;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by valdisnei on 28/06/17.
 */
class Search {

    private final String search;
    private final Pattern compile;
    private final BuilderPDF builderPDF;


    Search(String search, Pattern compile, BuilderPDF builderPDF) {
        this.search = search;
        this.compile = compile;
        this.builderPDF = builderPDF;
    }


    public String build() {

            Page page = builderPDF.getPdf().getPage();
            PageIterator pageIterator = builderPDF.getPdf().getPageIterator();
            return Objects.isNull(page)?searchField(pageIterator, this.search, compile):
                    searchField(page, this.search, compile);



    }


    private String searchField(PageIterator pages, String search, Pattern pattern) {
        if(Strings.isNullOrEmpty(this.builderPDF.getPdf().getBuilder().toString()))
            builder(pages);
        String searchRegex = search+pattern.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(this.builderPDF.getPdf().getBuilder().toString());
        if (matcher.find())
            return matcher.group().replace(search, "");
        else
            return getResult(pages,search,compile);

    }


    private String searchField(Page page, String search, Pattern pattern) {
        if(Strings.isNullOrEmpty(this.builderPDF.getPdf().getBuilder().toString()))
            builder(page);

        String searchRegex = search+pattern.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(this.builderPDF.getPdf().getBuilder().toString());
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
                row.forEach((col)->this.builderPDF.getPdf().getBuilder().append(col.getText().toUpperCase()));
            });
        });
    }

    private String getResult(Page page, String search, Pattern pattern) {
        return processPage(search, pattern, page);

    }

    private String getResult(PageIterator pages, String search, Pattern pattern) {
        if(!pages.hasNext())
            try {
                pages = UtilsPages.forEach(builderPDF.getPdf().getPathFile());
                while (pages.hasNext()) {
                    Page page = pages.next();
                    return processPage(search, pattern, page);
                }
            } catch (IOException e) {
                e.printStackTrace();
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

}
