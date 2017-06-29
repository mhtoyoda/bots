package com.fiveware.dsl;

/**
 * Created by valdisnei on 23/06/17.
 */
public class Extract {

    public static Extract extract(){
        return new Extract();
    }

    public BuilderHtml html(){
        BuilderHtml builderHtml = new BuilderHtml();
        return builderHtml;
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
