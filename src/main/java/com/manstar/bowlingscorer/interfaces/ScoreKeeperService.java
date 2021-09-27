package com.manstar.bowlingscorer.interfaces;

import java.util.List;

import com.manstar.bowlingscorer.models.Frame;

public interface ScoreKeeperService {
	
	public boolean isGameOpen();

	public void processRoll(int score);

	public List<Frame> getFrames();
	
	public Frame getCurrentFrame();
	
	public int getCurrentFrameNo();
	
	public void startGame();

}
