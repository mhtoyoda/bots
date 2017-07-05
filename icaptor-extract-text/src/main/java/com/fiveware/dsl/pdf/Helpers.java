package com.fiveware.dsl.pdf;

import com.fiveware.dsl.excel.BuilderExcel;
import com.fiveware.dsl.excel.IExcel;
import com.fiveware.dsl.html.Html;

import java.io.IOException;


/**
 * Created by valdisnei on 23/06/17.
 */
public class Helpers {

    public static Helpers helpers(){
        return new Helpers();
    }

    public Html html(){
        Html builderHtml = Html.getInstance();
        return builderHtml;
    }

    public IExcel excel() throws IOException {
        BuilderExcel builderExcel = new BuilderExcel();
        return builderExcel.getExcel();
    }

    public Pdf pdf(){
        Pdf builderPDF = PdfImpl.getPdf();
        return builderPDF;
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
            return this.key;
        }
    }
}
