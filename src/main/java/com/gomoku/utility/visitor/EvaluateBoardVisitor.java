package com.gomoku.utility.visitor;

import com.gomoku.model.Board;
import com.gomoku.model.PlayerSymbol;
import com.gomoku.utility.EvaluateBoardResult;

public class EvaluateBoardVisitor implements CellVisitor<EvaluateBoardResult> {

	int currentCount = 0;
	boolean oneEndOpening = false;
	PlayerSymbol currentSymbol = null;
	PlayerSymbol symbol = null;
	
	public EvaluateBoardVisitor(PlayerSymbol symbol) {
		this.symbol = symbol;
	}
	
	public boolean visit(Board board, int x, int y, EvaluateBoardResult result) {
		
		if (board.getSymbol(x, y) == null) {
			if (oneEndOpening && currentCount == 3) {
				currentCount = 0;
				if (symbol == currentSymbol) {
					result.incOurOpenThree();
				} else if (currentSymbol != null) {
					result.incOpponentOpenThree();
				}
			} else if (currentCount >= 4) {
				if (symbol == currentSymbol) {
					result.incOurFour();
				} else if (currentSymbol != null) {
					result.incOpponentFour();
				}
			}
			currentCount = 0;
			oneEndOpening = true;
			currentSymbol = null;
		} else {
			if (board.getSymbol(x, y) != currentSymbol) {
				if (oneEndOpening && currentCount >= 4) {
					if (currentSymbol == symbol) {
						result.incOurFour();
					} else if (currentSymbol != null) {
						result.incOpponentFour();
					}
				}
				currentSymbol = board.getSymbol(x, y);
				currentCount = 1;
			} else {
				currentCount++;
			}
		}
		return false;
	}

	public void reset() {
		currentCount = 0;
		oneEndOpening = false;
		currentSymbol = null;
	}

}
