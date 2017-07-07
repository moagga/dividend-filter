package com.magg.mca.utils;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NYSETickersExtractor {

	private static final String URL = "https://en.wikipedia.org/wiki/Companies_listed_on_the_New_York_Stock_Exchange_({index})";
	
	public static void main(String[] args) throws Exception {
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVQXYZ";
		char[] chars = alphabets.toCharArray();
		List<String> tickers = new ArrayList<>();
		for(char c : chars){
			String url = URL.replace("{index}", Character.toString(c));
			Document doc = Jsoup.connect(url).get();
			Elements els = doc.select("table a.external");
			tickers.addAll(els.eachText());
		}
		Collections.sort(tickers);
		Path path = FileSystems.getDefault().getPath("", "src", "main", "resources", "nyse.txt");
		System.out.println(path);
		Path nyse = Files.createFile(path);
		System.out.println(nyse);
		Files.write(nyse, tickers);
	}

}
