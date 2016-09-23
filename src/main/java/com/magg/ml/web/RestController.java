package com.magg.ml.web;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magg.ml.classification.BayesianFilter;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private BayesianFilter filter;

	@RequestMapping(path="/accept", consumes="text/plain")
	public void accept(@RequestBody String text){
		Collection<String> words = Arrays.asList(text.split("\\s"));
		filter.train(words, true);
	}

	@RequestMapping(path="/reject", consumes="text/plain")
	public void reject(@RequestBody String text){
		Collection<String> words = Arrays.asList(text.split("\\s"));
		filter.train(words, false);
	}

	@RequestMapping(path="/evaluate", produces="application/json", consumes="text/plain")
	@ResponseBody
	public String evaulate(@RequestBody String text){
		Collection<String> words = Arrays.asList(text.split("\\s"));
		double good = filter.evaluate(words, true);
		double bad = filter.evaluate(words, false);
		
		String str = "{";
		str += "\"good\":" + good +",";
		str += "\"bad\":" + bad + "}";
		return str;
	}
}
