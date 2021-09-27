package com.manstar.bowlingscorer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manstar.bowlingscorer.interfaces.BowlingBoardService;
import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.models.Frame;

/**
 * Service to print score after every roll
 */
@Service
public class BowlingBoardPrinter implements BowlingBoardService {

	@Autowired
	private ScoreKeeperService scoreKeeperService;

	public void showPinFalls() {

		List<Frame> scores = scoreKeeperService.getFrames();

		System.out.print("Pin falls: ");
		for (Frame f : scores) {
			if (f.getFirstRoll() != null) {
				System.out.print(String.format("{%s %s %s}", getFrameRollscore(f,1), getFrameRollscore(f,2), getFrameRollscore(f,3)));
			} else {
				// there may not always be a score to show as the frame may have been advanced but there would be no roll.
				continue;
			}
		}
		System.out.println("");
	}

	public void showScore() {
		List<Frame> scores = scoreKeeperService.getFrames();
		System.out.print("Score: ");
		int runningTotal = 0;
		for (Frame f : scores) {
			if (f.getFirstRoll() != null) {
				runningTotal += f.getFrameFinalScore();
				System.out.print("{" + runningTotal + "}");
			} else {
				// there may not always be a score to show as the frame may have been advanced but there would be no roll.
				continue;
			}
		}
		System.out.println("");
	}
	
	public String getFrameRollscore(Frame f, int roll) {
		switch(roll) {
		case 1:
			return f.getFirstRoll() != null ? String.valueOf(f.getFirstRoll().getScore()) : "";
		case 2:
			return f.getSecondRoll() != null ? String.valueOf(f.getSecondRoll().getScore()) : "";
		case 3:
			return f.getThirdRoll() != null ? String.valueOf(f.getThirdRoll().getScore()) : "";
		default:
			return null;			
		}
	}
}
