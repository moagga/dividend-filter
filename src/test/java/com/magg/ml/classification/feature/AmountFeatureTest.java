package com.magg.ml.classification.feature;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class AmountFeatureTest {

	@Test
	public void testAmountFeature(){
		AmountFeature af = new AmountFeature();

		boolean result = af.exists(Arrays.asList(new String[]{"$0.2110"}));
		Assert.assertTrue(result);
		
		result = af.exists(Arrays.asList(new String[]{"25cents"}));
		Assert.assertTrue(result);

	}
}
