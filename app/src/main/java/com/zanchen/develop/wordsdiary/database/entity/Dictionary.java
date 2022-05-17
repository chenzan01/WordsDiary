package com.zanchen.develop.wordsdiary.database.entity;

public class Dictionary {
    private int wid;
    private String wordname;
    private String wordpronounce_UK;
    private String wordpronounce_US;
    private String wordmean_CN;

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWordname() {
        return wordname;
    }

    public void setWordname(String wordname) {
        this.wordname = wordname;
    }

    public String getWordpronounce_UK() {
        return wordpronounce_UK;
    }

    public void setWordpronounce_UK(String wordpronounce_UK) {
        this.wordpronounce_UK = wordpronounce_UK;
    }

    public String getWordpronounce_US() {
        return wordpronounce_US;
    }

    public void setWordpronounce_US(String wordpronounce_US) {
        this.wordpronounce_US = wordpronounce_US;
    }

    public String getWordmean_CN() {
        return wordmean_CN;
    }

    public void setWordmean_CN(String wordmean_CN) {
        this.wordmean_CN = wordmean_CN;
    }
}
