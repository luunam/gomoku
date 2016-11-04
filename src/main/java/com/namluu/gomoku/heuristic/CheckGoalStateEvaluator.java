package com.namluu.gomoku.heuristic;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.PlayerSymbol;

public class CheckGoalStateEvaluator implements CellEvaluator {
	
	private int currentCount;
	private PlayerSymbol currentState;
	
	private boolean isGameFinished;
	private PlayerSymbol winner;
	
	@Override
	public boolean finish() {
		return isGameFinished;
	}

	@Override
	public void examine(Board board, int x, int y) {
		PlayerSymbol thisSymbol = board.getSymbol(x, y);
		if (thisSymbol != null) {
			if (thisSymbol.equals(currentState)) {
				currentCount += 1;
				if (currentCount == 5) {
					isGameFinished = true;
					winner = currentState;
				}
			}
			else {
				currentState = thisSymbol;
				currentCount = 1;
			}
		} else {
			currentState = thisSymbol;
			currentCount = 0;
		}
	}

	@Override
	public void reset() {
		currentState = null;
		currentCount = 0;
	}
	
	public PlayerSymbol getWinner() {
		return winner;
	}

}
