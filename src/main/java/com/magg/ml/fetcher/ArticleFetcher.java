package com.magg.ml.fetcher;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ArticleFetcher {

	public static void main(String[] args) {
		ArticleFetcher af = new ArticleFetcher();
		af.fetch("http://www.dividend.com/news/2016/09/09/china-mobile-increases-dividend-by-24/");
	}
	
	public void fetch(String url){
		try {
			Document doc = Jsoup.connect(url).get();
			String text = doc.body().text();
			System.out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
