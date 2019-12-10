package com.example.goalwise.api;

public class APIUrls {
    private static APIUrls instance;
    private String apiPrefix;

    public APIUrls(String prefix) {
        this.apiPrefix = prefix;
    }

    public static APIUrls init(String prefix) {
        instance = new APIUrls(prefix);
        return instance;
    }

    public static APIUrls get() {
        return instance;
    }

    public String getSip() {
        return  apiPrefix + "search";
    }
}
