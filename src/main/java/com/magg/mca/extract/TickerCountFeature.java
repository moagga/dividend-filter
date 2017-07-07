package com.magg.mca.extract;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.magg.mca.Article;

public class TickerCountFeature {

	private Pattern pattern;
	
	public TickerCountFeature() {
		pattern = Pattern.compile("\\([A-Z]*:?[A-Z]*\\)");
	}
	
	public void extract(Article article){
		int count = 0;
		count += count(article.getHeadings());
		count += count(article.getParagraphs());
	}
	
	private int count(List<String> lines){
		int count = 0;
		for(String text : lines){
			Matcher m = pattern.matcher(text);
			while (m.find()){
				count++;
			}
		}
		return count;
	}
	
}
