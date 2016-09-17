package com.magg.ml.classification.feature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TickerFeature implements Feature{

	private Set<String> tickers;
	
	public TickerFeature() {
		tickers = new HashSet<>();
		init();
	}
	
	@Override
	public boolean exists(Collection<String> words) {
		for (String word : words) {
			if (tickers.contains(word)){
				return true;
			}
		}
		return false;
	}

	private void init(){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("nyse-tickers.txt");
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		try {
			while ((line = br.readLine()) != null){
				tickers.add(line.trim());
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

	@Override
	public String toString() {
		return "nyse-tickers.txt";
	}
}
