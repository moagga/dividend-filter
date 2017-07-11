package com.magg.mca.provider;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SeekingAlphaPositiveUrlsCollector {

    public static void main(String[] args) throws Exception {

        SeekingAlphaPositiveUrlsCollector alpha = new SeekingAlphaPositiveUrlsCollector();

        List<String> negativeUrls = new ArrayList<String>();
        negativeUrls.addAll(alpha.crawl());

        Path path = FileSystems.getDefault().getPath("", "src", "main", "resources", "positive-urls.txt");
        Path nyse = Files.createFile(path);
        Files.write(nyse, negativeUrls);

    }

    private List<String> crawl() {
        long timestamp = 1499194061l;
        String baseurl = "https://seekingalpha.com/market-news/ajax_get_market_currents?current_tag=dividends&until=";

        List<String> urls = new ArrayList<String>();

        for (int i = 0; i < 21; i++) {
            try {
                String url = baseurl + timestamp;
                System.out.println("Fetching..." + url);
                String json = exec(url);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(json);
                String html = node.get("market_currents").asText();
                timestamp = node.get("last_ts").asLong() - 1;

                Document doc = Jsoup.parse(html);
                Elements els = doc.select("div.title a");
                System.out.println(els.size() + " elements found");
                urls.addAll(els.eachAttr("href"));

                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return urls;
    }

    private String exec(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
        get.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        get.addHeader("Accept-Language", "en-US,en;q=0.5");
        get.addHeader("Referer", "https://seekingalpha.com/dividends/dividend-news");
        get.addHeader("X-Requested-With", "XMLHttpRequest");
        get.addHeader(
                "Cookie",
                "_px=p9nV1WMAEfB+h4ftuHlwSGLtiWhqjJFjrEUgVfqJaQZDphXVbwX0WYxVwVRL4mHq2z3OdABGL49gg58YKdxZEw==:1000:0T69ki496grxWTfWop7mavXowCGqoMHGACK29FChUYm0mWJUtj4NsBuZd/gRKAaLOWHjAYm/lxi1+R3j1VBiBGl3VRA7/FMPmLjcl2FJ+H8goB80XpX8KkIDxKHBNw3E0TyLteYA8iDXWOSDG5T8xpRc2j80lV6ptv5YIuc60MDPcmvie+GiGUH2Bc4crThIADpZTf+vIORJcMRQmc1cSzkiI/o3zJ013jHwPjY8FQB2XIHmORuSqJwLAH+eBoZB9ql9cGsNfSlPJZO2fuo4qQ==; _px2=eyJ1IjoiMTBmYmU5NjAtNjU2MC0xMWU3LWJmZDctNTUzNjJjNWE3NWM5IiwidiI6IjcyYjZlNjEwLTdmZWItMTFlNi04NTRhLWM3NTcyOTc1NmNlZCIsInQiOjE0OTk2ODUzOTcwODAsImgiOiI1MTg0ZjIzYTdlYjE1ODBiOTQ3MWYwYWIwMzhhYjQ4N2VhOTQ0YzAwYWIyZDQzNjEyNjFlZTE1Y2U1MzRkNGY3In0=; _sp_id=36b1a7bd-5704-4672-83c6-386d5986006e; _sp_ses=53029e9d-b42b-4536-bb2f-5fcf4dbbbad7; machine_cookie=1736451149724; __utma=150447540.410067729.1499675730.1499675730.1499675730.1; __utmb=150447540.62.9.1499684892534; __utmz=150447540.1499675730.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __gads=ID=5635d0614187837a:T=1499675731:S=ALNI_MYDks5G7Bhh1_SQ0fQPpd6OsaucwA; bknx_ss=1499683139192; __utmc=150447540; bknx_fa=1499675729582; __utmt=1");

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
