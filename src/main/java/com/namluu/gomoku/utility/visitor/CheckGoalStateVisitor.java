package com.namluu.gomoku.utility.visitor;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.PlayerSymbol;
import com.namluu.gomoku.utility.CheckGoalStateResult;

public class CheckGoalStateVisitor implements CellVisitor<CheckGoalStateResult> {
	int currentCount;
	PlayerSymbol currentState;
	
	public boolean visit(Board board, int x, int y, CheckGoalStateResult result) {
		PlayerSymbol thisSymbol = board.getSymbol(x, y);
		if (thisSymbol != null) {
			if (thisSymbol.equals(currentState)) {
				currentCount += 1;
				if (currentCount == 5) {
					result.setGameFinished(true);
					result.setWinner(currentState);
					
					return true;
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
		return false;
	}

	public void reset() {
		currentState = null;
		currentCount = 0;
	}

}
