package com.manstar.bowlingscorer.services;

import static com.manstar.bowlingscorer.utils.BowlingUtils.isFirstRollOfFrame;
import static com.manstar.bowlingscorer.utils.BowlingUtils.isLastRound;
import static com.manstar.bowlingscorer.utils.BowlingUtils.isStrike;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manstar.bowlingscorer.interfaces.ScoreKeeperService;
import com.manstar.bowlingscorer.interfaces.ScoreValidatorService;

/**
 * Validates the score rolled
 */
@Service
public class ScoreValidator implements ScoreValidatorService {
	
	private static final Logger logger = LogManager.getLogger(ScoreValidator.class);

	private static String INVALID_INPUT = "Invalid Integer value provided: {%s}";
	private static String INVALID_TOTAL = "The sum of scores in the first: {%s} and second: {%s} rolls should not exceed 10";
	
	@Autowired
	private ScoreKeeperService scoreKeeperService;

	@Override
	public boolean validateScore(int score) {

		if (score < 0 || score > 10) {
			// valid range ?
			logger.info(String.format(INVALID_INPUT, score));
			return false;
		}

		if (isFirstRollOfFrame(scoreKeeperService.getCurrentFrame())) {
			// valid first roll
			return score <= 10;
		}

		if (!isFirstRollOfFrame(scoreKeeperService.getCurrentFrame()) && !isLastRound(scoreKeeperService.getCurrentFrameNo())) {
			// valid second roll 
			if(score + scoreKeeperService.getCurrentFrame().getFirstRoll().getScore() <= 10) {
				return true;
			}else {
				logger.info(String.format(String.format(INVALID_TOTAL,  scoreKeeperService.getCurrentFrame().getFirstRoll().getScore(), score)));
				return false;
			}
		}
		// special treatment for last round as we may have an extra strike here
		if (isLastRound(scoreKeeperService.getCurrentFrameNo())) {
			if ((scoreKeeperService.getCurrentFrame().getFirstRoll() != null && scoreKeeperService.getCurrentFrame().getSecondRoll() != null)) {
				// Last round third roll
				return score <= 10;
			} else {
				// last round second roll
				if (isStrike(scoreKeeperService.getCurrentFrame())) {
					return score <= 10;
				} else {
					return score + scoreKeeperService.getCurrentFrame().getFirstRoll().getScore() <= 10;
				}
			}
		}
		return true;
	}
}
