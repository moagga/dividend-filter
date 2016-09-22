package com.magg.ml.classification.feature;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class DateFeature implements Feature{

	private static final Set<String> months = new HashSet<>();
	
	static {
		months.addAll(Arrays.asList(new String[]{"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "sept", "oct", "nov", "dec"}));
		months.addAll(Arrays.asList(new String[]{"january", "february", "march", "april", "may", "june", 
				"july", "august", "september", "october", "november", "december"}));
	}
	
	@Override
	public boolean exists(Collection<String> words) {
		Stream<String> s = words.stream();
		return s.anyMatch(t -> months.contains(t.toLowerCase().trim().replaceAll("\\.", "")));
	}

	@Override
	public String toString() {
		return "date-feature";
	}
}
