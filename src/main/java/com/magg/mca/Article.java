package com.magg.mca;

import java.util.ArrayList;
import java.util.List;

public class Article {

	private final String url;
	private List<String> headings;
	private List<String> paragraphs;
	
	public Article(String url) {
		this.url = url;
		headings = new ArrayList<>();
		paragraphs = new ArrayList<>();
	}
	
	public List<String> getHeadings() {
		return headings;
	}
	
	public List<String> getParagraphs() {
		return paragraphs;
	}
	
	public Article addHeadings(List<String> headings){
		this.headings.addAll(headings);
		return this;
	}
	
	public Article addParagraphs(List<String> paragraphs){
		this.paragraphs.addAll(paragraphs);
		return this;
	}

	public String getUrl() {
		return url;
	}
}
