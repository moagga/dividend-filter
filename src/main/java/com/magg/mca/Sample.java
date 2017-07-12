package com.magg.mca;

import java.util.Map;

public class Sample {

	private Article article;
	private Map<String, Object> features;
	
	public Sample(Article article, Map<String, Object> features) {
		this.article = article;
		this.features = features;
	}
	
	public Article getArticle() {
		return article;
	}
	
}
