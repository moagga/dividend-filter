package com.magg.ml.classification;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BayesianFilterTest {

	private BayesianFilter filter;
	
	@Before
	public void setup(){
		filter = new BayesianFilter();
	}
	
	@Test
	public void test(){
		filter.train(Arrays.asList(new String[]{"dividend", "BMI", "yield"}), true);
		filter.train(Arrays.asList(new String[]{"distribution", "MCI"}), true);
		filter.train(Arrays.asList(new String[]{"dividend", "stock"}), false);
		filter.train(Arrays.asList(new String[]{"dividend", "date"}), false);
		filter.train(Arrays.asList(new String[]{"dividend", "result", "merger", "BSAC"}), false);

		System.out.println(filter);
		List<String> words = Arrays.asList(new String[]{"dividend"});
		double good = filter.evaluate(words, true);
		double bad = filter.evaluate(words, false);
		System.out.println("Good:" + good);
		System.out.println("Bad:" + bad);
	}
}
