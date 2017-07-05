package com.magg.mca.scrap;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.yaml.snakeyaml.Yaml;

import com.magg.mca.Article;

public class Scrapper {

	private Map config;
	
	public Scrapper() {
		Yaml yaml = new Yaml();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("scrap-rules.yaml");
		config = (Map) yaml.load(in);
		System.out.println(config);
	}
	
	public Article scrap(String url) {
		Article article = null;
		Pattern pattern = Pattern.compile("https?://([\\w\\.]*)/");
		Matcher matcher = pattern.matcher(url);
		String domain = null;
		if (matcher.find()){
			domain = matcher.group(1);
			System.out.println(domain);
		}
		try {
			Document doc = Jsoup.connect(url).get();
			article = new Article(url);
			Map rules = (Map) config.get(domain);
			List<String> headingRules = (List<String>) rules.get("headings");
			for (String rule : headingRules) {
				Elements els = doc.select(rule);
				List<String> data = els.eachText();
				article.addHeadings(data);
			}
			List<String> paraRules = (List<String>) rules.get("paragraphs");
			for (String rule : paraRules) {
				Elements els = doc.select(rule);
				List<String> data = els.eachText();
				article.addParagraphs(data);
				for(String para : article.getParagraphs()){
					System.out.println(para);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return article;
	}
	
}
