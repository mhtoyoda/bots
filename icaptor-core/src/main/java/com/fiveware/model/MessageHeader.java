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
    private final Integer totalLines;
    private final Integer chuncksInit;
    private final Integer chuncksEnd;
    private final Long timeStamp;

    private MessageHeader(String pathFile, Integer totalLines, Integer chuncksInit, Integer chuncksEnd, Long timeStamp) {
        this.pathFile = pathFile;
        this.totalLines = totalLines;
        this.chuncksInit = chuncksInit;
        this.chuncksEnd = chuncksEnd;
        this.timeStamp = timeStamp;
    }

    public Integer getChuncksEnd() {
        return chuncksEnd;
    }

    public Integer getChuncksInit() {
        return chuncksInit;
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
        private Integer chuncksInit;
        private Integer chuncksEnd;
        private  Long timeStamp;

        public MessageHeaderBuilder(String pathFile, Integer totalLines) {
            this.pathFile = pathFile;
            this.totalLines = totalLines;
        }


        public MessageHeaderBuilder chuncksInitial(Integer chuncksInit){
           this.chuncksInit=chuncksInit;
           return this;
        }

        public MessageHeaderBuilder chuncksEnd(Integer chuncksEnd){
            this.chuncksEnd=chuncksEnd;
            return this;
        }

        public MessageHeaderBuilder timeStamp(Long timeStamp){
            this.timeStamp=timeStamp;
            return this;
        }


        public MessageHeader build(){
            return new MessageHeader(pathFile,totalLines,chuncksInit,chuncksEnd,timeStamp);
        }

    }

}
