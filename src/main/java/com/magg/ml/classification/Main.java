package com.magg.ml.classification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.magg.ml"})
public class Main {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Main.class, args);
		BayesianFilter bf = ac.getBean(BayesianFilter.class);
		trainFilter(bf, true);
		trainFilter(bf, false);
	}
	
	private static void trainFilter(BayesianFilter filter, boolean good){
		String name = (good ? "good" : "bad") + ".samples.txt";
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		try {
			while ((line = br.readLine()) != null){
				line = line.trim();
				filter.train(Arrays.asList(line.split("\\s")), good);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				in.close();
			} catch (IOException e) {
			}
		}
	}

}
