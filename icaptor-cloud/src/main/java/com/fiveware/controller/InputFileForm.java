package com.fiveware.controller;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

/**
 * Created by valdisnei on 6/16/17.
 */
@AutoProperty
public class InputFileForm implements Serializable{

    private String nameBot;
    private String pathFile;


    public InputFileForm() {
    }

    public String getNameBot() {
        return nameBot;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setNameBot(String nameBot) {
        this.nameBot = nameBot;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
