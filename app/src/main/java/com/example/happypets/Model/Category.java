package com.example.happypets.Model;

public class Category {
    private String title;
    private String pic_url;
    public Category(String title, String pic_url) {
        this.title = title;
        this.pic_url = pic_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
