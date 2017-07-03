package com.fiveware.dsl;

import com.fiveware.dsl.excel.BuilderExcel;
import com.fiveware.dsl.excel.IExcel;
import com.fiveware.dsl.html.BuilderHtml;
import com.fiveware.dsl.pdf.BuilderPDF;

import java.io.IOException;

import static org.apache.commons.csv.CSVFormat.Predefined.Excel;

/**
 * Created by valdisnei on 23/06/17.
 */
public class Helpers {

    public static Helpers helpers(){
        return new Helpers();
    }

    public BuilderHtml html(){
        BuilderHtml builderHtml = new BuilderHtml(this.pdf());
        return builderHtml;
    }

    public IExcel excel() throws IOException {
        BuilderExcel builderExcel = new BuilderExcel();
        return builderExcel.getExcel();
    }

    public BuilderPDF pdf(){
        BuilderPDF builderPDF = new BuilderPDF(this);
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
            return key.toString();
        }
    }
}
