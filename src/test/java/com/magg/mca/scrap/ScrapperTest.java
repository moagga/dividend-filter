package com.magg.mca.scrap;

import org.junit.Test;

import com.magg.mca.Article;

public class ScrapperTest {

	
	@Test
	public void testInit(){
		Scrapper sc = new Scrapper();
		Article article = sc.scrap("https://seekingalpha.com/article/4085656-daimler-best-dividend-return-play-global-automobile-sector");
		System.out.println(article.getHeadings().size());
		System.out.println(article.getParagraphs().size());
	}
	
}
