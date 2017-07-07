package com.magg.mca.provider;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SeekingAlpha {

	public static void main(String[] args) throws Exception {
		
		/*
		 * Negative examples URLs
		 */
		List<String> negativeUrls = new ArrayList<String>();
		negativeUrls.addAll(crawl("https://seekingalpha.com/dividends/dividend-ideas"));
		negativeUrls.addAll(crawl("https://seekingalpha.com/dividends/dividend-strategy"));
		
		Path path = FileSystems.getDefault().getPath("", "src", "main", "resources", "negative-urls.txt");
		Path nyse = Files.createFile(path);
		Files.write(nyse, negativeUrls);

	}
	
	private static List<String> crawl(String baseUrl){
		List<String> urls = new ArrayList<String>();
		Document doc;
		try {
			int index = 2;
			String url = baseUrl;
			do {
				doc = Jsoup.connect(url).get();
				Elements els = doc.select("li.article a.a-title");
				urls.addAll(els.eachAttr("href"));
				url = baseUrl + "?page=" + index;
				index++;
			} while (index < 11);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urls;
	}

	private static List<String> generatePositiveUrls(String baseUrl){
		List<String> urls = new ArrayList<String>();
		Document doc;
		try {
			int index = 2;
			String url = baseUrl;
			do {
				doc = Jsoup.connect(url).get();
				Elements els = doc.select("div.title a");
				urls.addAll(els.eachAttr("href"));
				url = baseUrl + "?page=" + index;
				index++;
			} while (index < 11);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return urls;
		
//		String url = "https://seekingalpha.com/market-news/ajax_get_market_currents?current_tag=dividends&until=1499101620";
	}

}
