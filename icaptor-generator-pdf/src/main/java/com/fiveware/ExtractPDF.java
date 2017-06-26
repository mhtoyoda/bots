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
    private StringBuilder builder = new StringBuilder();

    public ExtractPDF() {
    }

    public static void main(String[] args) {
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
        page = UtilsPages.getPage(pathFile[0].toString(),
                Integer.valueOf((Integer) pathFile[1]).intValue());
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
        return  searchField(page, this.search,compile);
    }


    private String searchField(Page page, String search, Pattern pattern) {
        builder(page,search);

        String searchRegex = search+pattern.pattern();

        Pattern compile = Pattern.compile(searchRegex);

        Matcher matcher = compile.matcher(builder.toString());
        if (matcher.find()) {
            return matcher.group().replace(search, "");
        }else{
            return builder(page,search,compile);
        }
    }

    private void builder(Page page,String searc) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
        bea.extract(page).forEach((table)->{
            table.getRows().forEach((row)->{
                row.forEach((col)->builder.append(col.getText()));
            });
        });
    }

    private String builder(Page page,String search,Pattern pattern) {
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
        List<Table> extract = bea.extract(page);
        for (int i = 0; i <extract.size(); i++) {
            Table table = extract.get(i);
            List<List<RectangularTextContainer>> rows = table.getRows();
            for (int j = 0; j < rows.size(); j++) {

                List<RectangularTextContainer> rectangularTextContainers = rows.get(j);
                for (int k = 0; k < rectangularTextContainers.size(); k++) {

                    String text = table.getCell(j, k).getText();

                    boolean contain=text.contains(search);
                    if(contain)
                        return builder(rows,table,pattern,j);
                }
            }
        }

        return null;
    }

    private String builder(List<List<RectangularTextContainer>> rows,Table table, Pattern pattern, int j) {

        String compile = pattern.pattern().replace(search, "");


//        Table table = extract.get(i);
//        List<List<RectangularTextContainer>> rows = table.getRows();
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
