package com.magg.mca.provider;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.magg.mca.Article;
import com.magg.mca.scrap.Scrapper;


public class SeekingAlphaCrawler {

    private static final String BASE_PATH = "C:/Research/Dividend/data/";

    public static void main(String[] args) throws Exception {
        SeekingAlphaCrawler crawler = new SeekingAlphaCrawler();
        //        crawler.process(true);
        crawler.process(false);
    }

    private final Scrapper scraper = new Scrapper();

    private void process(boolean positive) throws Exception {
        String fileName = BASE_PATH + (positive ? "positive" : "negative") + "-urls.txt";
        Path path = FileSystems.getDefault().getPath(fileName);
        System.out.println(path);
        Stream<String> urls = Files.lines(path);

        int alreadyProcessed = progressInfo(positive);
        Stream<String> toBeProcessed = urls.skip(alreadyProcessed).sequential();
        AtomicInteger index = new AtomicInteger(alreadyProcessed);
        toBeProcessed.forEach((url) -> {
            try {
                System.out.println("Processing:" + positive + ":" + index.get());
                Article article = scraper.scrap(url);
                save(article, positive, index.incrementAndGet());
                updateProgress(positive, index.get());
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception encountered while crawling.Exiting");
                System.exit(0);
            }
        });
        urls.close();
    }

    private int progressInfo(boolean positive) {
        int index = 0;
        String progressFileName = BASE_PATH + (positive ? ".positive" : ".negative") + ".progress.txt";
        try {
            Path filePath = FileSystems.getDefault().getPath(progressFileName);
            if (Files.exists(filePath)) {
                String number = Files.readAllLines(filePath).get(0);
                index = Integer.parseInt(number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return index;
    }

    private void updateProgress(boolean positive, int index) {
        String progressFileName = BASE_PATH + (positive ? ".positive" : ".negative") + ".progress.txt";
        try {
            Path filePath = FileSystems.getDefault().getPath(progressFileName);
            if (!Files.exists(filePath)) {
                filePath = Files.createFile(filePath);
            }
            Files.write(filePath, Arrays.asList(new String[] { index + "" }));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            lines.add(1 + "");
            lines.add(article.getHeading());
            lines.add("----");
            lines.add(article.getParagraphs().size() + "");
            lines.addAll(article.getParagraphs());

            Files.write(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
