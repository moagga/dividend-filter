package com.magg.mca;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private final String url;
    private String heading;
    private final List<String> paragraphs;

    public Article(String url) {
        this.url = url;
        paragraphs = new ArrayList<>();
    }

    public String getHeading() {
        return heading;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public Article addHeadings(String heading) {
        this.heading = heading;
        return this;
    }

    public Article addParagraphs(List<String> paragraphs) {
        this.paragraphs.addAll(paragraphs);
        return this;
    }

    public String getUrl() {
        return url;
    }
}
