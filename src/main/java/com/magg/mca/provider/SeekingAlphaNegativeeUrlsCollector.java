package com.magg.mca.provider;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SeekingAlphaNegativeeUrlsCollector {

    public static void main(String[] args) throws Exception {

        SeekingAlphaNegativeeUrlsCollector alpha = new SeekingAlphaNegativeeUrlsCollector();
        List<String> negativeUrls = alpha.crawl();

        Path path = FileSystems.getDefault().getPath("", "src", "main", "resources", UUID.randomUUID().toString() + ".txt");
        Path nyse = Files.createFile(path);
        Files.write(nyse, negativeUrls);

    }

    private int index = 1;

    private List<String> crawl() {
        List<String> negativeUrls = new ArrayList<>();

        while (index < 21) {
            try {
                String url = createUrl();
                System.out.println("Fetching..." + url);

                Document doc = Jsoup.parse(exec(url));
                //                Document doc = Jsoup.connect(url).get();
                Elements els = doc.select("li.article a.a-title");
                System.out.println(els.size() + " elements found");
                negativeUrls.addAll(els.eachAttr("href"));
                Thread.sleep(3000);
            } catch (HttpStatusException e) {
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

    private String createUrl() {
        String url = index <= 10 ? "https://seekingalpha.com/dividends/dividend-ideas" : "https://seekingalpha.com/dividends/dividend-strategy";
        url = url + "?page=" + index;
        return url;
    }

    private String exec(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        get.addHeader("Accept-Language", "en-US,en;q=0.5");
        get.addHeader("Referer", "https://seekingalpha.com/dividends/dividend-ideas");
        get.addHeader(
                "Cookie",
                "_px=P7mk7m5I3rWWhYY5Oka9o0Aaa+5UmDQxiI2qm9hkfe2kmTG1iSf4Tk9XieANu9eKoWwqMXrObAGst629IcizKg==:1000:D7qlTOxxU4AIiLx/jERY/8lOL+956/fm3T+oZXVDcDuCGO2NP74VpxTgFuhxchxvK/zXebhZdzRSDaEphICfSyH2mkwg81axVs9ISl9F4O8V7zMf0i51xv6G8El61SmLfugtBXhl5g+QuRwRpXYSznVqmS/0Jk1GuEb2SvX6Oh3qc8Ft79M5sijcQW+36QQC5qb1rMlUnafZ/SZXUT1gKJjiQx1LNnVh9EC9sMJE3mFw6GhgHL2C1R98P3tVDkbdWVU7O5FZ+SY44zYpaWH4rA==; _px2=eyJ1IjoiYmI3Mzk5MzAtNjU0YS0xMWU3LWFlYWUtNzNlOTdmNGUzNzBiIiwidiI6IjcyYjZlNjEwLTdmZWItMTFlNi04NTRhLWM3NTcyOTc1NmNlZCIsInQiOjE0OTk2NzYyNTEzOTQsImgiOiIxMWVjYTcxN2NkNmVjMWQ3ZWQ5NzczYWFmNDExZDQyYzkzZDBhYjYyZjY4YTZiNmIzN2I1NGI0NTk1NWU5NDg1In0=; _sp_id=36b1a7bd-5704-4672-83c6-386d5986006e; _sp_ses=53029e9d-b42b-4536-bb2f-5fcf4dbbbad7; machine_cookie=1736451149724; __utma=150447540.410067729.1499675730.1499675730.1499675730.1; __utmb=150447540.2.9.1499675730; __utmc=150447540; __utmz=150447540.1499675730.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; bknx_fa=1499675729582; bknx_ss=1499675729582; __gads=ID=5635d0614187837a:T=1499675731:S=ALNI_MYDks5G7Bhh1_SQ0fQPpd6OsaucwA");

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
