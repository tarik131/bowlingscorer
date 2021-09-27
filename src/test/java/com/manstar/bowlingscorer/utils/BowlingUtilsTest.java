package com.manstar.bowlingscorer.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BowlingUtilsTest {
	
	@Test
	public void testValidStrikeRoll() {
		assertTrue(BowlingUtils.isStrikeRoll(10));
	}
	
	@Test
	public void testInvalidStrikeRoll() {
		assertFalse(BowlingUtils.isStrikeRoll(5));
	}
	
	@Test
	public void testValidNormalRoll() {
		assertTrue(BowlingUtils.isNormalRoll(5));
	}
	
	@Test
	public void testInvalidNormalRoll() {
		assertFalse(BowlingUtils.isNormalRoll(10));
	}
}
