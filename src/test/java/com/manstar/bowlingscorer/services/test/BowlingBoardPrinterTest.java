package com.manstar.bowlingscorer.services.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manstar.bowlingscorer.interfaces.BowlingBoardService;
import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.services.BowlingBoardPrinter;
import com.manstar.bowlingscorer.services.ScoreKeeper;

@SpringBootTest(classes = {ScoreKeeper.class, BowlingBoardPrinter.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BowlingBoardPrinterTest {
	
	private final ByteArrayOutputStream testOut= new ByteArrayOutputStream();
	private final PrintStream realOut = System.out;
	
	@Autowired
	ScoreKeeperService scoreKeeper;
	
	@Autowired
	BowlingBoardService scorePrinter;

	@Before
	public void setUp() throws Exception {
		 System.setOut(new PrintStream(testOut));
		 scoreKeeper.startGame();
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(realOut);
		scoreKeeper.getFrames().clear();
	}
	
	@Test
	public void noScoreTest() {
		scorePrinter.showScore();
		assertEquals("Score: \r\n", testOut.toString());
	}
	
	@Test
	public void noPinFallsTest() {
		scorePrinter.showPinFalls();
		assertEquals("Pin falls: \r\n", testOut.toString());
	}
	
	@Test
	public void StrikeTest() {
		scoreKeeper.processRoll(10);
		scorePrinter.showScore();
		assertEquals("Score: {10}\r\n", testOut.toString());
	}
	
	@Test
	public void StrikePinFallsTest() {
		scoreKeeper.processRoll(10);
		scorePrinter.showPinFalls();
		assertEquals("Pin falls: {10  }\r\n", testOut.toString());
	}
}
