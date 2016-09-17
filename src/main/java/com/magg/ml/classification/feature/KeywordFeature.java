package com.magg.ml.classification.feature;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class KeywordFeature implements Feature{

	private String keyword;
	
	public KeywordFeature(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public boolean exists(Collection<String> words) {
		for (String word : words) {
			if (keyword.equals(word.toLowerCase().trim())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return keyword;
	}

}
