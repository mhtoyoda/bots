package com.fiveware.model;

import java.io.Serializable;

/**
 * Created by valdisnei on 14/06/17.
 */
public class MessageHeader implements Serializable{
    private final String pathFile;
    private final Integer totalLines;
    private final Integer chuncksInit;
    private final Integer chuncksEnd;
    private final Long timeStamp;

    public MessageHeader(String pathFile, Integer totalLines, Integer chuncksInit, Integer chuncksEnd, Long timeStamp) {
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
}
