package com.fiveware;


import com.fiveware.tabula.Page;
import com.fiveware.tabula.RectangularTextContainer;
import com.fiveware.tabula.Table;
import com.fiveware.tabula.extractors.BasicExtractionAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by valdisnei on 23/06/17.
 */
public class ExtractPDF {

    static Logger logger= LoggerFactory.getLogger(ExtractPDF.class);

    private final static ExtractPDF builderPDF = new ExtractPDF();

    private Page page;

    private Pattern compile;

    private String search;

    public ExtractPDF() {
    }

    public ExtractPDF(String pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public ExtractPDF(File pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public ExtractPDF(Path pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public ExtractPDF(Path pathFIle,int numberPage) {
        try {
            main(pathFIle,Integer.valueOf(numberPage));
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private void main(Path pathFIle) throws IOException {
        main(pathFIle.toAbsolutePath().toFile());
    }

    public ExtractPDF(InputStream pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private void main(InputStream pathFIle) throws IOException {
        File file = new File(String.valueOf(pathFIle));
        main(file);
    }

    private void main(File pathFile) throws IOException {
            main(pathFile.getAbsoluteFile().toString());
    }

    private void main(String pathFile) throws IOException {
        page = UtilsPages.getPage(pathFile, 1);
    }

    private void main(Object ... pathFile) throws IOException {
        page = UtilsPages.getPage(pathFile[0].toString(), Integer.valueOf((Integer) pathFile[1]).intValue());
    }

    public ExtractPDF search(String search, TypeSearch typeSearch){
        this.compile = Pattern.compile(typeSearch.getRegex());
        this.search=search;
        return this;
    }

    public ExtractPDF search(String search, Pattern compile){
        this.search=search;
        this.compile=compile;
        return this;
    }
    public ExtractPDF search(String search, String compile){
        this.search=search;
        this.compile=Pattern.compile(compile);
        return this;
    }


    public String build(){
        return search(page, this.search,compile);
    }

    private String search(Page page,String search,Pattern pattern) {
        return  searchField(page, search,pattern);
    }


    private String searchField(Page page, String search, Pattern compile) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();

        List<Table> extract = bea.extract(page);

        for (int iTable = 0; iTable < extract.size(); iTable++) {
            Table table = extract.get(iTable);

            List<List<RectangularTextContainer>> rows = table.getRows();

            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j < rows.get(i).size(); j++) {
                    String text = table.getCell(i, j).getText();
                    boolean contains = text.contains(search);
                    Predicate<String> predicate = getStringPredicate();
                    if (contains)
                        return searchPattern(table,text,compile,i);
                }

            }
        }
        return null;
    }

    private Predicate<String> getStringPredicate() {
        return new Predicate<String>() {
                            @Override
                            public boolean test(String s) {
                                return s.contains(search);
                            }
                        };
    }

    private String searchPattern(Table table,String texto,Pattern pattern, int i) {
        List<List<RectangularTextContainer>> rows = table.getRows();

        for (; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                String text = table.getCell(i, j).getText();
                Matcher matcher = pattern.matcher(text);
                if (matcher.find() && getStringPredicate().test(texto))
                    return matcher.group();
            }
        }
        return null;
    }


    private String search2(Page page,String search) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();

        for (Table table : bea.extract(page)) {
            List<List<RectangularTextContainer>> rows = table.getRows();

            for (List<RectangularTextContainer> row:rows) {
                Optional<RectangularTextContainer> any = row.stream()
                        .filter((text) -> text.getText().contains(search)).findAny();

                if (any.isPresent() && !any.get().isEmpty())
                    return any.get().getText();
            }
        }
        return null;
    }

}
