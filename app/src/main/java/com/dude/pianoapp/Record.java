package com.dude.pianoapp;

public class Record {
    private String fileName;
    private String path;
    public Record(String fn,String path){
        this.fileName = fn;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
