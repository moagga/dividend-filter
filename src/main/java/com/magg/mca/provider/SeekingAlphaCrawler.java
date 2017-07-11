package com.magg.mca.provider;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.magg.mca.Article;
import com.magg.mca.scrap.Scrapper;


public class SeekingAlphaCrawler {

    private static final String BASE_PATH = "C:/Research/Dividend/data/";

    public static void main(String[] args) throws Exception {
        SeekingAlphaCrawler crawler = new SeekingAlphaCrawler();
        crawler.process(true);
        crawler.process(false);
    }

    private Scrapper scraper;

    private void process(boolean positive) throws Exception {
        String fileName = BASE_PATH + (positive ? "positive" : "negative") + "-urls.txt";
        Path path = FileSystems.getDefault().getPath(fileName);
        System.out.println(path);
        Stream<String> urls = Files.lines(path);

        int alreadyProcessed = progressInfo();
        Stream<String> toBeProcessed = urls.skip(alreadyProcessed).sequential();
        AtomicInteger index = new AtomicInteger(0);
        toBeProcessed.forEach((url) -> {
            Article article = new Article("www.a.com");
            //            Article article = scraper.scrap(url);
            save(article, positive, index.incrementAndGet());
            updateProgress();
            //            try {
            //                Thread.sleep(5000);
            //            } catch (Exception e) {
            //            }
        });
        urls.close();
    }

    private int progressInfo() {
        return 0;
    }

    private void updateProgress() {

    }

    private void save(Article article, boolean positive, int index) {
        String dirName = BASE_PATH + "raw/" + (positive ? "positive" : "negative");
        String fileName = index + ".dat";
        Path filePath = FileSystems.getDefault().getPath(dirName, fileName);
        Path file;
        try {
            file = Files.createFile(filePath);

            List<String> lines = new ArrayList<String>();
            lines.add(article.getUrl());
            lines.add(article.getHeadings().size() + "");
            lines.addAll(article.getHeadings());
            lines.add("----");
            lines.add(article.getParagraphs().size() + "");
            lines.addAll(article.getParagraphs());

            Files.write(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
