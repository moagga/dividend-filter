package com.magg.ml.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.magg.ml.classification.feature.Feature;
import com.magg.ml.classification.feature.KeywordFeature;
import com.magg.ml.classification.feature.TickerFeature;

public class BayesianFilter {

	private int goodClassificationCount = 0;
	private int badClassificationCount = 0;
	private int count = 0;
	
	private Map<Feature, Integer> featureGoodCount = new HashMap<>();
	private Map<Feature, Integer> featureBadCount = new HashMap<>();
	
	private List<Feature> features = new ArrayList<>();
	
	public BayesianFilter() {
		features.add(new KeywordFeature(new String[]{"dividend", "distribution", "result"}));
		features.add(new TickerFeature());
	}
	
	public void train(Collection<String> words, boolean good){
		for (Feature feature : features) {
			incrementFeatureCount(feature, good);
		}
		if (good){
			goodClassificationCount++;
		} else {
			badClassificationCount++;
		}
		count++;
	}
	
	public double evaluate(Collection<String> words){
		double p = 0.0;
		for (Feature feature : features) {
			boolean exist = feature.exists(words);
			if (exist){
				p = p* featureProbability(feature, exist);
			}
			incrementFeatureCount(feature, exist);
		}
		double classificationProbability = goodClassificationCount * 1.0 / count;
		
		count++;
		return p * classificationProbability;
	}
	
	private double featureProbability(Feature f, boolean good){
		//TODO Add weight
		Map<Feature, Integer> map = good ? featureGoodCount : featureBadCount;
		int fCount = map.get(f);
		int cCount = good ? goodClassificationCount : badClassificationCount;
		
		return fCount * 1.0 / cCount;
	}
	
	private void incrementFeatureCount(Feature f, boolean good){
		Map<Feature, Integer> map = good ? featureGoodCount : featureBadCount;
		if (!map.containsKey(f)){
			map.put(f, 0);
		}
		int value = map.get(f);
		map.put(f, value++);
	}
}
