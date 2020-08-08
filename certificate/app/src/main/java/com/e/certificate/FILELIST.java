package com.e.certificate;

public class FILELIST {
    String name,audiourl;
    public FILELIST(){

    }
    public FILELIST(String name, String audiourl) {
        this.name = name;
        this.audiourl = audiourl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudiourl() {
        return audiourl;
    }

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl;
    }
}
