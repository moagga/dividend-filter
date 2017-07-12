package com.magg.mca.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class Features {

	private static final List<Feature> features = new ArrayList<>();
	
	static {
		
	}
	
	public static Stream<Feature> asStream(){
		return features.stream();
	}
	
}
