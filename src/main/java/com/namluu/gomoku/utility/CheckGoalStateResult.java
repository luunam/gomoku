package com.namluu.gomoku.utility;

import com.namluu.gomoku.model.PlayerSymbol;

public class CheckGoalStateResult {
	private boolean isGameFinished;
	private PlayerSymbol winner;
	
	
	public boolean isGameFinished() {
		return isGameFinished;
	}
	public void setGameFinished(boolean isGameFinished) {
		this.isGameFinished = isGameFinished;
	}
	
	public PlayerSymbol getWinner() {
		return winner;
	}
	public void setWinner(PlayerSymbol winner) {
		this.winner = winner;
	}
}
