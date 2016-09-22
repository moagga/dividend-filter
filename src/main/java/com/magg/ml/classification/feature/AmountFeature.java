package com.magg.ml.classification.feature;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class AmountFeature implements Feature{

	private Pattern dollarPattern;
	private Pattern centPattern;

	public AmountFeature() {
		dollarPattern = Pattern.compile("\\$\\d+\\.?\\d*");
		centPattern = Pattern.compile("\\d+\\.?\\d*cent(s?)");
	}
	
	@Override
	public boolean exists(Collection<String> words) {
		Stream<String> s = words.stream();
		return s.anyMatch(t -> dollarPattern.matcher(t).matches() 
			|| centPattern.matcher(t).matches());
	}

	@Override
	public String toString() {
		return "amount-present";
	}
}
