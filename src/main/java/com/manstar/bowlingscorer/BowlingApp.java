package com.manstar.bowlingscorer;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.manstar.bowlingscorer.interfaces.BowlingBoardService;
import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.interfaces.ScoreValidatorService;

@SpringBootApplication
public class BowlingApp implements ApplicationRunner {
	
	private static final Logger logger = LogManager.getLogger(BowlingApp.class);
	
	private static String _QUIT = "q";
	private static String PROVIDE_INPUT = "Please provide an input: ";
	private static String INVALID_INPUT = "Your input: {%s} was invalid, please try again !";
	private static String EXITING_GAME = "Game abort requested, Exiting Game.";
	private static String DEFAULT_EXCPETION = "Encountered an unexpected exception, terminating system: ";

	@Autowired
	private BowlingBoardService bowlingBoardService;

	@Autowired
	private ScoreKeeperService scoreKeeperService;
	
	@Autowired
	private ScoreValidatorService scoreValidatorService;

	public static void main(String[] args) {
		SpringApplication.run(BowlingApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		
		logger.info("Starting App");
		
		scoreKeeperService.startGame();

		System.out.println("*******************************Instructions*******************************");
		System.out.println("1. When Prompted, Provide a valid Input between 0-10 to simulate a roll.");
		System.out.println("2. At any time, press q to quit the game.");
		System.out.println();

		Scanner in = new Scanner(System.in);
		int rollScore;

		try {
			while (scoreKeeperService.isGameOpen()) {
				System.out.print(PROVIDE_INPUT);
			
				if (in.hasNextInt()) {
					rollScore = Integer.parseInt(in.next());
					if (!scoreValidatorService.validateScore(rollScore)) {
						System.out.println(String.format(INVALID_INPUT, rollScore));
						continue;
					} else {
						scoreKeeperService.processRoll(rollScore);
					}
				} else {
					String input = in.next();
					if (_QUIT.equalsIgnoreCase(input)) {
						logger.info(EXITING_GAME);
						System.out.println(EXITING_GAME);
						System.exit(0);
					}
					System.out.println(String.format(INVALID_INPUT, input));
					continue;
				}
				
				bowlingBoardService.showPinFalls();
				bowlingBoardService.showScore();
			}
		}catch(Exception e) {
			logger.error(DEFAULT_EXCPETION);
			logger.error(e.getMessage(), e);
			System.out.println(DEFAULT_EXCPETION);
			
			System.exit(1);
		}finally {
			in.close();
		}
	}
}
