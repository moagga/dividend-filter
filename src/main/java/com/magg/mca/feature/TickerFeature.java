package com.magg.mca.feature;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.magg.mca.Article;
import com.magg.mca.SampleBuilder;

public class TickerFeature extends Feature {

    protected final Pattern pattern;

    public TickerFeature() {
        pattern = Pattern.compile("\\([A-Z]*:?[A-Z]*\\)");
    }

    @Override
    public void extract(Article article, SampleBuilder builder) {
        int count = 0;
        count += count(Arrays.asList(new String[] { article.getHeading() }));
        count += count(article.getParagraphs());

        builder.addFeatureValue("TICKERS_PRESENT", count == 0 ? "N" : "Y");
        builder.addFeatureValue("TICKERS_COUNT", count);
    }

    private int count(List<String> lines) {
        int count = 0;
        for (String text : lines) {
            Matcher m = pattern.matcher(text);
            while (m.find()) {
                count++;
            }
        }
        return count;
    }


}
