package com.magg.mca.feature;

import java.util.stream.Stream;

import com.magg.mca.Article;
import com.magg.mca.SampleBuilder;

public abstract class Feature {

	public abstract void extract(Article article, SampleBuilder sampleBuilder);
	
	
}
