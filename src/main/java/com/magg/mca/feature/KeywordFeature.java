package com.magg.mca.feature;

import java.util.Set;

import com.magg.mca.Article;
import com.magg.mca.SampleBuilder;

public class KeywordFeature extends Feature {

    private Set<String> keywords;

    public KeywordFeature() {
    }

    @Override
    public void extract(Article article, SampleBuilder sampleBuilder) {
        keywords.stream().forEach((keyword) -> {

        });
    }

}
