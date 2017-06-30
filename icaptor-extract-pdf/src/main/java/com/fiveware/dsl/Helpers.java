package com.fiveware.dsl;

import com.fiveware.dsl.excel.BuilderExcel;

import java.io.IOException;

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

    public BuilderExcel excel() throws IOException {
        BuilderExcel builderExcel = new BuilderExcel();
        return builderExcel;
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
