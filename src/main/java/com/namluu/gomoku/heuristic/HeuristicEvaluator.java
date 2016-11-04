package com.namluu.gomoku.heuristic;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.PlayerSymbol;

public class HeuristicEvaluator implements CellEvaluator {
	private int opponentOpenThree = 0;
	private int ourOpenThree = 0;
	
	private int opponentFour = 0;
	private int ourFour = 0;
	
	int currentCount = 0;
	boolean oneEndOpening = false;
	PlayerSymbol currentSymbol = null;
	PlayerSymbol symbol = null;
	
	public HeuristicEvaluator(PlayerSymbol symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public boolean finish() {
		return false;
	}
	
	@Override
	public void examine(Board board, int x, int y) {
		if (board.getSymbol(x, y) == null) {
			if (oneEndOpening && currentCount == 3) {
				currentCount = 0;
				if (symbol == currentSymbol) {
					ourOpenThree++;
				} else if (currentSymbol != null) {
					opponentOpenThree++;
				}
			} else if (currentCount >= 4) {
				if (symbol == currentSymbol) {
					ourFour++;
				} else if (currentSymbol != null) {
					opponentFour++;
				}
			}
			currentCount = 0;
			oneEndOpening = true;
			currentSymbol = null;
		} else {
			if (board.getSymbol(x, y) != currentSymbol) {
				if (oneEndOpening && currentCount >= 4) {
					if (currentSymbol == symbol) {
						ourFour++;
					} else if (currentSymbol != null) {
						opponentFour++;
					}
				}
				currentSymbol = board.getSymbol(x, y);
				currentCount = 1;
			} else {
				currentCount++;
			}
		}
	}
	
	@Override
	public void reset() {
		currentCount = 0;
		oneEndOpening = false;
		currentSymbol = null;
		
	}
	
	public void printRecord() {
		System.out.println("Our open three:      " + ourOpenThree);
		System.out.println("Our four:            " + ourFour);
		System.out.println("Opponent open three: " + opponentOpenThree);
		System.out.println("Opponent four:       " + opponentFour);
	}
	
	public int getOpponentOpenThree() {
		return opponentOpenThree;
	}
	public int getOurOpenThree() {
		return ourOpenThree;
	}
	public int getOpponentFour() {
		return opponentFour;
	}
	public int getOurFour() {
		return ourFour;
	}
}
