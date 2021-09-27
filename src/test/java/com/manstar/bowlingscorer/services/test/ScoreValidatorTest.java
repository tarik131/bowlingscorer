package com.manstar.bowlingscorer.services.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.interfaces.ScoreValidatorService;
import com.manstar.bowlingscorer.services.ScoreKeeper;
import com.manstar.bowlingscorer.services.ScoreValidator;

@SpringBootTest(classes = {ScoreKeeper.class, ScoreValidator.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ScoreValidatorTest {
	
	@Autowired
	ScoreKeeperService scoreKeeper;	

	@Autowired
	ScoreValidatorService scoreValidator;

	@Before
	public void setUp() throws Exception {
		scoreKeeper.startGame();
	}
	
	@After
    public void teardown() {
		scoreKeeper.getFrames().clear();
    }
	
	@Test
	public void inValidScoreTest() {
		assertFalse(scoreValidator.validateScore(65));
	}
	
	@Test
	public void isStrikeValidTest() {
		assertTrue(scoreValidator.validateScore(10));
	}
	
	@Test
	public void inValidSecondRollTest() {
		scoreKeeper.processRoll(5);
		assertFalse(scoreValidator.validateScore(6));
	}

}
