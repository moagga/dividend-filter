package com.magg.ml.classification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
//		filter.train(Arrays.asList(new String[]{"dividend", "BMI", "yield"}), true);
//		filter.train(Arrays.asList(new String[]{"dividend", "BMI"}), true);
//		filter.train(Arrays.asList(new String[]{"distribution", "MCI"}), true);
//		filter.train(Arrays.asList(new String[]{"dividend", "stock"}), false);
//		filter.train(Arrays.asList(new String[]{"dividend", "date"}), false);
//		filter.train(Arrays.asList(new String[]{"dividend", "result", "merger", "BSAC"}), false);

		trainFilter(true);
		trainFilter(false);
		
		System.out.println(filter);
		
		List<String> words = new ArrayList<>();
		words.add("dividend");
		words.add("BLL");
		words.add("$2");
		words.add("announce");
		words.add("record");
		words.add("June");
		double good = filter.evaluate(words, true);
		double bad = filter.evaluate(words, false);
		System.out.println("Good:" + good);
		System.out.println("Bad:" + bad);
		System.out.println(good/bad);
	}
	
	private void trainFilter(boolean good){
		String name = (good ? "good" : "bad") + ".samples.txt";
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		try {
			while ((line = br.readLine()) != null){
				line = line.trim();
				filter.train(Arrays.asList(line.split("\\s")), good);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				in.close();
			} catch (IOException e) {
			}
		}
	}
}
