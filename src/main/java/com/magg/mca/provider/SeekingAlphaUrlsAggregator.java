package com.magg.mca.provider;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SeekingAlphaUrlsAggregator {

	public static void main(String[] args) throws Exception {

		SeekingAlphaUrlsAggregator alpha = new SeekingAlphaUrlsAggregator();
		List<String> negativeUrls = alpha.crawl();

		Path path = FileSystems.getDefault().getPath("", "src", "main", "resources", "negative-urls.txt");
		Path nyse = Files.createFile(path);
		Files.write(nyse, negativeUrls);

	}

	private int index = 1;
	
	private List<String> crawl() {
		List<String> negativeUrls = new ArrayList<>();
		
		while (index < 21){
			try {
				String url = createUrl();
				System.out.println("Fetching..." + url);
				Document doc = Jsoup.connect(url).get();
				Elements els = doc.select("li.article a.a-title");
				System.out.println(els.size() + " elements found");
				negativeUrls.addAll(els.eachAttr("abs:href"));
				Thread.sleep(5000);
			} catch (HttpStatusException e){
				e.printStackTrace();
				System.out.println("HTTP exception. Delaying");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			index++;
		}
		return negativeUrls;

	}

	private String createUrl(){
		String url = index <= 10 ? "https://seekingalpha.com/dividends/dividend-ideas" : "https://seekingalpha.com/dividends/dividend-strategy";
		url = url + "?page=" + index;
		return url;
	}
}
