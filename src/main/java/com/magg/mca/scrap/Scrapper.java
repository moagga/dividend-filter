package com.magg.mca.scrap;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
		}
		try {
			Document doc = Jsoup.parse(exec(url));
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
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return article;
	}
	
    private String exec(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        get.addHeader("Accept-Language", "en-US,en;q=0.5");
        get.addHeader("Referer", "https://seekingalpha.com/dividends/dividends");
        get.addHeader(
                "Cookie",
                "machine_cookie=2311960113220; __utma=150447540.1155203377.1499231751.1499660054.1499832670.7; __utmz=150447540.1499231751.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __gads=ID=63b57b81ac3afb02:T=1499231753:S=ALNI_Mbl2cJCKp0gmNr99PgexwZYbpjdYQ; _pxvid=0799fbf0-6141-11e7-9ea5-594ea5655e32; _sp_id=b59f65ee-da2d-49d5-ab17-f7427c7bce7e; _igur=https://seekingalpha.com/dividends/dividend-ideas; bknx_fa=1499832669450; bknx_ss=1499832669450; __utmb=150447540.13.8.1499832745988; __utmc=150447540; __utmt=1; _px=IYwpaRTRpSvcchhGbj86+OlZih9TAnFoG9eSCZ3KlVywFcA++80ucDEIo0O1ae1SItdrDpinIAIGfM3nd3aeMA==:1000:/++k1PT4gZbwBIsZvTAy3EK3KbgkKc/elCJrUJFLgEjhrRnMtrhnbBzZGNMZ62wJEcIL8EOmv4WKGfGZ4iiEbIDHansztj5qIYcYvWBasuW2Y6LRsDCR5gzDQEhznelY5hUKcDy46NVfNZ8N+wHF+/UDvioSD09tCHEW4QZX/oxLwM1YGc8SY0B9XKAFIy8+OOUhey2gKVWsG5FIUo0ePb2SNUviE8w3yVyT0JhBPO3C8KMGqn3uzx45Xq95JEHkvl50sJ3ZecrA7b81Ga9dDA==; _px2=eyJ1IjoiNTA0NjljYjAtNjZiOC0xMWU3LWFmOGQtNDNmMmZkNGYxYzllIiwidiI6IjA3OTlmYmYwLTYxNDEtMTFlNy05ZWE1LTU5NGVhNTY1NWUzMiIsInQiOjE0OTk4MzMyNTAwNzksImgiOiJhNmVkODQ0YzgxODZmYzBmNTBiY2U2YThkNzM0NGJjMzFlM2Y1MGQ5MjFlM2I0OTMwYTE3YWMxYjlmYTA3YTBiIn0=; _sp_ses=49972b39-41a7-4467-a49b-1ce763d8df58; _igt=02bebf25-7c84-4e1b-b8bf-43aa03d52dbb; _igsr=https://seekingalpha.com/dividends; _igh=https%3A%2F%2Fseekingalpha.com%2Farticle%2F4085656-daimler-best-dividend-return-play-global-automobile-sector|https%3A%2F%2Fseekingalpha.com%2Farticle%2F4085662-duke-energy-buy-dip|https%3A%2F%2Fseekingalpha.com%2Farticle%2F4085953-build-safer-dividend-strategy|https%3A%2F%2Fseekingalpha.com%2Farticle%2F4086062-intel-garden-portfolio|https%3A%2F%2Fseekingalpha.com%2Farticle%2F4087082-cbs-dividend-growth-will-drive-shares-higher; _ig_nump=7:3:0:7:0:5; _ig_sess=3:0:0; _ig=63191520-412d-4340-c43b-4088d5de8830");

        CloseableHttpResponse response1 = httpclient.execute(get);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            String html = EntityUtils.toString(entity1, Charset.forName("UTF-8"));
            return html;
        } finally {
            response1.close();
            httpclient.close();
        }
    }
}
