package com.fiveware.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

/**
 * Created by valdisnei on 14/06/17.
 */
@AutoProperty
public class MessageHeader implements Serializable{
    private final String pathFile;
    private final Long timeStamp;

    private MessageHeader(String pathFile, Long timeStamp) {
        this.pathFile = pathFile;
        this.timeStamp = timeStamp;
    }

    public String getPathFile() {
        return pathFile;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    public static class MessageHeaderBuilder implements Serializable{
        private final String pathFile;
        private  Long timeStamp;

        public MessageHeaderBuilder(String pathFile) {
            this.pathFile = pathFile;
        }

        public MessageHeaderBuilder timeStamp(Long timeStamp){
            this.timeStamp=timeStamp;
            return this;
        }

        public MessageHeader build(){
            return new MessageHeader(pathFile, timeStamp);
        }

    }

}
