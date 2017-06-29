package com.fiveware.dsl;


import com.fiveware.UtilsPages;
import com.fiveware.core.Page;
import com.fiveware.core.PageIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by valdisnei on 23/06/17.
 */
public class Pdf {
    static Logger logger= LoggerFactory.getLogger(Pdf.class);

    private PageIterator pageIterator;
    private Page page;
    private StringBuilder builder = new StringBuilder();
    private String pathFile;


    protected Pdf(String pathFIle) {
        try {
            main(pathFIle);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    protected Pdf(String pathFIle, int numberPage) {
        try {
            main(pathFIle,Integer.valueOf(numberPage));
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private void main(String pathFile) throws IOException {
        this.pathFile=pathFile;
        pageIterator = UtilsPages.forEach(pathFile);
    }

    private void main(Object ... pathFile) throws IOException {
        this.pathFile=pathFile[0].toString();
        page = UtilsPages.getPage(pathFile[0].toString(),
                Integer.valueOf((Integer) pathFile[1]).intValue());
    }

    protected Page getPage() {
        return page;
    }

    protected PageIterator getPageIterator() {
        return pageIterator;
    }

    public static FromTo FromTo(String key, Object obj){
        return new FromTo(key,obj);
    }

    protected StringBuilder getBuilder() {
        return builder;
    }


    protected String getPathFile() {
        return pathFile;
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
