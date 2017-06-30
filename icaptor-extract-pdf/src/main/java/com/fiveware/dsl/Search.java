package com.fiveware.dsl;

import com.fiveware.UtilsPages;
import com.fiveware.core.Page;
import com.fiveware.core.PageIterator;
import com.fiveware.core.RectangularTextContainer;
import com.fiveware.core.Table;
import com.fiveware.core.extractors.BasicExtractionAlgorithm;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by valdisnei on 28/06/17.
 */
class Search {

    static Logger logger = LoggerFactory.getLogger(Search.class);
    private final String search;
    private final Pattern compile;
    private final BuilderPDF builderPDF;
    private boolean isNext;


    Search(String search, Pattern compile, BuilderPDF builderPDF) {
        this.search = search;
        this.compile = compile;
        this.builderPDF = builderPDF;
    }

    protected void next(boolean isNext) {
        this.isNext = isNext;
    }

    public String build() {
        Page page = builderPDF.getPdf().getPage();
        PageIterator pageIterator = builderPDF.getPdf().getPageIterator();
        Object somePageObject = MoreObjects.firstNonNull(page, pageIterator);

        return searchField(somePageObject, this.search, compile);
    }

    private String searchField(Object somePageObject, String search, Pattern pattern) {
        if (this.builderPDF.isEmptyText())
            builder(somePageObject);

        String searchRegex = search + pattern.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(this.builderPDF.getText());
        if (matcher.find()) {
            return matcher.group().replace(search, "").trim();
        } else {
            if (somePageObject instanceof Page)
                return getResult((Page) somePageObject, search, compile);
            else
                return getResult((PageIterator) somePageObject, search, compile);
        }
    }

    private void builder(Object somePageObject) {
        if (somePageObject instanceof PageIterator) {
            while (((PageIterator) somePageObject).hasNext()) {
                Page page = ((PageIterator) somePageObject).next();
                addPage(page);
            }
        } else {
            addPage((Page) somePageObject);
        }
    }

    private void addPage(Page page) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
        bea.extract(page).forEach((table) -> {
            table.getRows().forEach((row) -> {
                row.forEach((col) -> this.builderPDF.append(col.getText().toUpperCase()));
            });
        });
    }

    private String getResult(Page page, String search, Pattern pattern) {
        return processPage(search, pattern, page);

    }

    private String getResult(PageIterator pages, String search, Pattern pattern) {
        if (!pages.hasNext())
            try {
                pages = UtilsPages.pages(builderPDF.getPdf().getPathFile());
                while (pages.hasNext()) {
                    Page page = pages.next();
                    String s = processPage(search, pattern, page);
                    if (!Strings.isNullOrEmpty(s))
                        return s;
                }
            } catch (IOException e) {
                logger.error("{}", e);
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

    private String builder(List<List<RectangularTextContainer>> rows, Table table, Pattern pattern, int j) {
        boolean controleNext = false;
        String compile = pattern.pattern().replace(search, "");

        int k1 = 0;

        for (; j < rows.size(); j++) {
            List<RectangularTextContainer> cols = rows.get(j);

            next:
            for (int k = k1; k < cols.size(); k++) {
                String text = table.getCell(j, k).getText();
                Matcher matcher = Pattern.compile(compile).matcher(text);
                if (matcher.find()) {
                    if (isNext && !controleNext) {
                        if (matcher.find())
                            return matcher.group().trim();
                    }
                    return matcher.group().trim();
                }

            }
        }
        return null;
    }

}
