package com.manstar.bowlingscorer.services.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.services.ScoreKeeper;

@SpringBootTest(classes = {ScoreKeeper.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ScoreKeeperTest {
	
	@Autowired
	ScoreKeeperService scoreKeeperService;

	@Before
	public void setUp() throws Exception {
		scoreKeeperService = new ScoreKeeper();
		scoreKeeperService.startGame();
	}
	
	@After
    public void teardown() {
		scoreKeeperService.getFrames().clear();
    }

	@Test
	public void TurkeyTest() {
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		assertEquals(30, scoreKeeperService.getFrames().get(0).getFrameFinalScore());
	}
	
	@Test
	public void DoubleTest() {
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(4);
		assertEquals(24, scoreKeeperService.getFrames().get(0).getFrameFinalScore());
	}
	
	@Test
	public void StrikeTest() {
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(5);
		scoreKeeperService.processRoll(4);
		assertEquals(19, scoreKeeperService.getFrames().get(0).getFrameFinalScore());
	}
	
	@Test
	public void SpareTest() {
		scoreKeeperService.processRoll(8);
		scoreKeeperService.processRoll(2);
		scoreKeeperService.processRoll(4);
		assertEquals(14, scoreKeeperService.getFrames().get(0).getFrameFinalScore());
	}
	
	@Test
	public void NormalScoreTest() {
		scoreKeeperService.processRoll(4);
		scoreKeeperService.processRoll(2);
		assertEquals(6, scoreKeeperService.getFrames().get(0).getFrameFinalScore());
	}
	
	@Test
	public void PerfectScoreTest() {
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		scoreKeeperService.processRoll(10);
		assertEquals(30, scoreKeeperService.getFrames().get(0).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(1).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(2).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(3).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(4).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(5).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(6).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(7).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(8).getFrameFinalScore());
		assertEquals(30, scoreKeeperService.getFrames().get(9).getFrameFinalScore());
	}

}
