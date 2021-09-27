package com.manstar.bowlingscorer.services;

import static com.manstar.bowlingscorer.utils.BowlingUtils.isFirstRollOfFrame;
import static com.manstar.bowlingscorer.utils.BowlingUtils.isLastRound;
import static com.manstar.bowlingscorer.utils.BowlingUtils.isSpare;
import static com.manstar.bowlingscorer.utils.BowlingUtils.isStrike;
import static com.manstar.bowlingscorer.utils.BowlingUtils.isStrikeRoll;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.manstar.bowlingscorer.enums.BowlingConstants;
import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.models.Frame;
import com.manstar.bowlingscorer.models.Roll;

/**
 * Maintains the game score and provides game score data to the rest of the app
 */

@Service
public class ScoreKeeper implements ScoreKeeperService {
	
	private static final Logger logger = LogManager.getLogger(ScoreKeeper.class);

	int currentFrame = BowlingConstants.STARTING_FRAME.getValue();
	int currentStrikeRoll = 0;
	private boolean onStrike, onDouble, gameEnded = false;
	ArrayList<Frame> game = new ArrayList<Frame>();

	@Override
	public void processRoll(int score) {
		
		
		logger.info("Frame: " + getCurrentFrameNo() + ", score: " + score);

		if (isFirstRollOfFrame(getCurrentFrame())) {
			
			getCurrentFrame().setFirstRoll(new Roll(score));			
			logger.debug("Frame: " + getCurrentFrameNo() + ", First Roll score: " + score);

			// check if previous frame was a spare and set bonus score
			if (game.size() > 1 && isSpare(getPreviousFrame())) {
				getPreviousFrame().setBonusRoll(new Roll(score));
			}

			// a strike can only occur on the 1st roll, check if this roll is a strike
			if (isStrikeRoll(score)) {
				currentStrikeRoll++;
				switch (currentStrikeRoll) {
				case 1:
					onStrike = true;
					break;
				case 2:
					onDouble = true;
					break;
				case 3:
					processTurkey(score);
					break;
				default:
					break;
				}
				if (!isLastRound(getCurrentFrameNo())) {
					advanceFrame();
				} 
			}else {
				//process non strike roll - calculate previous frames if on a double. 
				if (onDouble) {
					processDouble(score);
				}
			}
		} else {
			//Processing of second and third rolls
			if (isLastRound(getCurrentFrameNo()) && getCurrentFrame().getSecondRoll() != null) {
				// this is the last round and the third roll;
				getCurrentFrame().setThirdRoll(new Roll(score));
				advanceFrame();
			} else {
				// this is the 2nd roll
				getCurrentFrame().setSecondRoll(new Roll(score));
				// a spare can only occur on the 2nd roll - we need to check if this is a spare
				if (onStrike) {
					// this can happen only if previous round was a strike and the current round is normal.
					// or if its the last round score of 10 + 10
					processStrike(score);
				}
				if (!isLastRound(getCurrentFrameNo())) {
					advanceFrame();
				} else if(!isSpare(getCurrentFrame()) && !isStrike(getCurrentFrame())) {
					// if its the last round then advance only if this round is normal
					advanceFrame();
				}
			}
		}
	}

	@Override
	public boolean isGameOpen() {
		return !gameEnded && currentFrame <= 10;
	}
	
	@Override
	public List<Frame> getFrames() {
		return game;
	}

	@Override
	public Frame getCurrentFrame() {
		Frame currentFrame = game.get(game.size() - 1);
		return currentFrame;
	}

	@Override
	public int getCurrentFrameNo() {
		return game.size();
	}
	
	@Override
	public void startGame() {
		if(game.size()==0) {
			advanceFrame();
		}
	}
	
	
	private void processTurkey(int score) {		
		logger.debug("Processing Turkey.");
		game.get(getCurrentFrameNo() - 3).setBonusRoll(new Roll(game.get(getCurrentFrameNo() - (2)).getFirstRoll().getScore()+ game.get(getCurrentFrameNo() - (1)).getFirstRoll().getScore()));
		currentStrikeRoll--;
	}
	
	private void processDouble(int score) {	
		logger.debug("Processing Double.");
		
		if (isLastRound(getCurrentFrameNo())) {
			// last round and second roll, process score for earlier frames
			if (isStrike(getCurrentFrame()) || isSpare(getCurrentFrame())) {
				getPreviousFrame().setBonusRoll(new Roll(getCurrentFrame().getFirstRoll().getScore() + score));
				currentStrikeRoll--;
			} else {
				advanceFrame();
			}
		}
		//check if we need to process previous frames for a double or turkey
		if(!isStrikeRoll(score)){
			game.get(game.size() - 3).setBonusRoll(new Roll(getPreviousFrame().getFirstRoll().getScore() + score));
			currentStrikeRoll--;
		}
		
		onDouble = false;
	}

	
	private void processStrike(int score) {
			getPreviousFrame().setBonusRoll(new Roll(getCurrentFrame().getFirstRoll().getScore() + getCurrentFrame().getSecondRoll().getScore()));
			currentStrikeRoll--;
			onStrike = false;
	}

	private Frame getPreviousFrame() {
		return game.get(game.size() - 2);
	}
	
	private void completeGame() {
		this.gameEnded = true;
	}
	
	private void advanceFrame() {
		if (game.size() < 10) {
			Frame newFrame = new Frame(game.size()+1);
			game.add(newFrame);
		} else {
			completeGame();
		}

		logger.debug("Frame advanced to: " + getCurrentFrame().getRound());
	}
}
