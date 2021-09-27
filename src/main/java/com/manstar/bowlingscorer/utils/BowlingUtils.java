package com.manstar.bowlingscorer.utils;

import com.manstar.bowlingscorer.enums.BowlingConstants;
import com.manstar.bowlingscorer.models.Frame;

public final class BowlingUtils {

	public static boolean isStrikeRoll(int score) {
		return score == 10;
	}

	public static boolean isSpareRoll(int firstRollScore, int secondRollScore) {
		if (isNormalRoll(firstRollScore) && isNormalRoll(secondRollScore)) {
			return firstRollScore + secondRollScore == 10;
		}
		return BowlingUtils.isStrikeRoll(secondRollScore);
	}

	public static boolean isNormalRoll(int rollScore) {
		return rollScore >= 0 && rollScore <= 9;
	}

	public static boolean isSpare(Frame f) {
		if (f.getFirstRoll() != null && f.getSecondRoll() != null) {
			return f.getFirstRoll().getScore() + f.getSecondRoll().getScore() == 10;
		}
		return false;
	}

	public static boolean isStrike(Frame f) {
		if (f.getFirstRoll() != null) {
			return f.getFirstRoll().getScore() == 10;
		}
		return false;
	}

	public static boolean isLastRound(int frameRound) {
		return frameRound == BowlingConstants.LAST_ROUND.getValue();
	}

	public static boolean isFirstRollOfFrame(Frame f) {
		return f.getFirstRoll() == null;
	}
}
