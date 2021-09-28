package com.manstar.bowlingscorer.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manstar.bowlingscorer.models.Frame;
import com.manstar.bowlingscorer.models.Roll;

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
	
	@Test
	public void testValidSpareFrame() {
		Frame f = new Frame();
		f.setFirstRoll(new Roll(5));
		f.setSecondRoll(new Roll(5));
		assertTrue(BowlingUtils.isSpare(f));
	}
	
	@Test
	public void testInvalidSpareFrame() {
		Frame f = new Frame();
		f.setFirstRoll(new Roll(5));
		f.setSecondRoll(new Roll(4));
		assertFalse(BowlingUtils.isSpare(f));
	}
	
	@Test
	public void testValidStrikeFrame() {
		Frame f = new Frame();
		f.setFirstRoll(new Roll(10));
		assertTrue(BowlingUtils.isStrike(f));
	}
	
	@Test
	public void testInvalidStrikeFrame() {
		Frame f = new Frame();
		f.setFirstRoll(new Roll(5));
		f.setSecondRoll(new Roll(4));
		assertFalse(BowlingUtils.isStrike(f));
	}
	
	@Test
	public void testIsLastRound() {
		assertTrue(BowlingUtils.isLastRound(10));
	}
	
	@Test
	public void testIsNotLastRound() {
		assertFalse(BowlingUtils.isLastRound(5));
	}	
	
	@Test
	public void testValidFirstRoll() {
		Frame f = new Frame();
		assertTrue(BowlingUtils.isFirstRollOfFrame(f));
	}
	
	@Test
	public void testInvalidFirstRoll() {
		Frame f = new Frame();
		f.setFirstRoll(new Roll(5));
		assertFalse(BowlingUtils.isFirstRollOfFrame(f));
	}
}
