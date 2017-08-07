package com.fiveware.model;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * Created by valdisnei on 14/06/17.
 */
@AutoProperty
public class MessageHeader implements Serializable{
    private final String pathFile;
    private final Integer totalLines;
    private final Long timeStamp;

    private MessageHeader(String pathFile, Integer totalLines, Long timeStamp) {
        this.pathFile = pathFile;
        this.totalLines = totalLines;
        this.timeStamp = timeStamp;
    }

    public Integer getTotalLines() {
        return totalLines;
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
        private final Integer totalLines;
        private  Long timeStamp;

        public MessageHeaderBuilder(String pathFile, Integer totalLines) {
            this.pathFile = pathFile;
            this.totalLines = totalLines;
        }

        public MessageHeaderBuilder timeStamp(Long timeStamp){
            this.timeStamp=timeStamp;
            return this;
        }

        public MessageHeader build(){
            return new MessageHeader(pathFile, totalLines, timeStamp);
        }

    }

}
