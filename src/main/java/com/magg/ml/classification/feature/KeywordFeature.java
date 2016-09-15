package com.magg.ml.classification.feature;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class KeywordFeature implements Feature{

	private Set<String> keywords;
	
	public KeywordFeature(String[] keywords) {
		this.keywords = new HashSet<>();
		for(String word : keywords){
			this.keywords.add(word.toLowerCase().trim());
		}
	}

	@Override
	public boolean exists(Collection<String> words) {
		for (String word : words) {
			if (keywords.contains(word.toLowerCase().trim())){
				return true;
			}
		}
		return false;
	}

}
