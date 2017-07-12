package com.magg.mca;

import java.util.LinkedHashMap;
import java.util.Map;

import com.magg.mca.feature.Features;

public class SampleBuilder {

	private Article article;
	private Map<String, Object> featureValues;
	
	public SampleBuilder(Article article) {
		this.article = article;
		this.featureValues = new LinkedHashMap<>();
	}
	
	public void addFeatureValue(String featureCode, Object value){
		featureValues.put(featureCode, value);
	}
	
	public Sample build(){
		Features.asStream().forEach((feature) -> {
			feature.extract(article, this);
		});
		return null;
	}
}
