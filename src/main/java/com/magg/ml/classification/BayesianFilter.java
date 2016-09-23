package com.magg.ml.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.magg.ml.classification.feature.AmountFeature;
import com.magg.ml.classification.feature.DateFeature;
import com.magg.ml.classification.feature.Feature;
import com.magg.ml.classification.feature.KeywordFeature;
import com.magg.ml.classification.feature.TickerFeature;

@Component
public class BayesianFilter {

	private int goodClassificationCount = 0;
	private int badClassificationCount = 0;
	private int count = 0;
	
	private Map<Feature, Integer> featureGoodCount = new HashMap<>();
	private Map<Feature, Integer> featureBadCount = new HashMap<>();
	
	private List<Feature> features = new ArrayList<>();
	
	public BayesianFilter() {
		features.add(new KeywordFeature("dividend"));
		features.add(new KeywordFeature("distribution"));
		features.add(new KeywordFeature("result"));
		features.add(new KeywordFeature("payable"));
		features.add(new KeywordFeature("paid"));
		features.add(new KeywordFeature("record"));
		features.add(new KeywordFeature("announce"));
		features.add(new KeywordFeature("declare"));
		features.add(new KeywordFeature("raise"));
		features.add(new TickerFeature());
		features.add(new AmountFeature());
		features.add(new DateFeature());
	}
	
	public void train(Collection<String> words, boolean good){
		for (Feature feature : features) {
			boolean exist = feature.exists(words);
			if (exist){
				incrementFeatureCount(feature, good);
			}
		}
		if (good){
			goodClassificationCount++;
		} else {
			badClassificationCount++;
		}
		count++;
	}
	
	public double evaluate(Collection<String> words, boolean good){
		double p = 1.0;
		for (Feature feature : features) {
			boolean exist = feature.exists(words);
			if (exist){
				p = p* featureProbability(feature, good);
			}
		}
		double classificationProbability = goodClassificationCount * 1.0 / count;
		return p * classificationProbability;
	}
	
	private double featureProbability(Feature f, boolean good){
		double baseProbability = 0.5;
		int weight = 1;
		
		Map<Feature, Integer> map = good ? featureGoodCount : featureBadCount;
		int fCount = 0;
		if (map.containsKey(f)){
			fCount = map.get(f);
		}
		int cCount = good ? goodClassificationCount : badClassificationCount;
		
		double p = (weight * baseProbability + fCount) / (weight + cCount);
		return p;
	}
	
	private void incrementFeatureCount(Feature f, boolean good){
		Map<Feature, Integer> map = good ? featureGoodCount : featureBadCount;
		if (!map.containsKey(f)){
			map.put(f, 0);
		}
		int value = map.get(f);
		value++;
		map.put(f, value);
	}
	
	@Override
	public String toString() {
		return featureGoodCount + "," + featureBadCount;
	}
}
