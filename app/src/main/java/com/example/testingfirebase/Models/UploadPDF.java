package com.example.testingfirebase.Models;

public class UploadPDF {

    private String name;
    private String url;

    public UploadPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public UploadPDF() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
