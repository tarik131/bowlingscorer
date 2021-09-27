package com.manstar.bowlingscorer.models;

import java.util.Objects;

/**
 * This is a model class to hold Frame information
 */
public class Frame {

	private int round;
	private Roll firstRoll;
	private Roll secondRoll;
	private Roll thirdRoll;
	private Roll bonusRoll;
	
	public Frame() {
		super();
	}
	
	public Frame(int round) {
		super();
		this.round = round;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Frame frame = (Frame) o;
		return round == frame.round;
	}

	@Override
	public int hashCode() {
		return Objects.hash(round);
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Roll getFirstRoll() {
		return firstRoll;
	}

	public void setFirstRoll(Roll firstRoll) {
		this.firstRoll = firstRoll;
	}

	public Roll getSecondRoll() {
		return secondRoll;
	}

	public void setSecondRoll(Roll secondRoll) {
		this.secondRoll = secondRoll;
	}

	public Roll getThirdRoll() {
		return thirdRoll;
	}

	public void setThirdRoll(Roll thirdRoll) {
		this.thirdRoll = thirdRoll;
	}

	public int getFrameFinalScore() {
		return (this.getFirstRoll() != null ? this.getFirstRoll().getScore() : 0)
				+ (this.getSecondRoll() != null ? this.getSecondRoll().getScore() : 0)
				+ (this.getThirdRoll() != null ? this.getThirdRoll().getScore() : 0)
				+ (this.getBonusRoll() != null ? this.getBonusRoll().getScore() : 0);
	}

	public Roll getBonusRoll() {
		return bonusRoll;
	}

	public void setBonusRoll(Roll bonusRoll) {
		this.bonusRoll = bonusRoll;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("First Roll: ")
				.append(this.getFirstRoll() != null ? this.getFirstRoll().getScore() : 0).append(", ")
				.append("Second Roll: ").append(this.getSecondRoll() != null ? this.getSecondRoll().getScore() : 0)
				.append(", ").append("Third Roll: ")
				.append(this.getThirdRoll() != null ? this.getThirdRoll().getScore() : 0).append(", ")
				.append("Bonus Roll: ").append(this.getBonusRoll() != null ? this.getBonusRoll().getScore() : 0)
				.append(", ")
				.toString();
	}
}
