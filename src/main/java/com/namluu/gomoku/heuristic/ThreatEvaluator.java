package com.namluu.gomoku.heuristic;

import java.util.ArrayList;
import java.util.List;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.Move;
import com.namluu.gomoku.model.PlayerSymbol;

public class ThreatEvaluator implements CellEvaluator {
	
	private int opponentFour = 0;
	
	int currentCount = 0;
	boolean oneEndOpening = false;
	PlayerSymbol currentSymbol = null;
	PlayerSymbol symbol = null;
	
	List<Pair> pairs = new ArrayList<Pair>();
	
	Move counterMove = null;
	Pair openSquare = null;
	
	boolean finish = false;
	public ThreatEvaluator(PlayerSymbol symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public boolean finish() {
		return finish;
	}
	
	@Override
	public void examine(Board board, int x, int y) {
		
		if (board.getSymbol(x, y) == null) {
			
			if (currentCount >= 4) {
				if (currentSymbol != symbol) {	
					counterMove = new Move(x, y, symbol);
					finish = true;
				}
			}
			
			currentCount = 0;
			oneEndOpening = true;
			openSquare = new Pair(x, y);
			currentSymbol = null;
			
		} else {
			if (board.getSymbol(x, y) != currentSymbol) {
				
				System.out.println("different symbol " + currentSymbol);
				System.out.println("oneEndOpening: " + oneEndOpening);
				if (oneEndOpening && currentCount >= 4) {
					if (currentSymbol != null && currentSymbol != symbol) {
						counterMove = new Move(openSquare.x, openSquare.y, symbol);
						finish = true;
					}
				}
				
				if (currentSymbol != null) {
					oneEndOpening = false;
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
		System.out.println("current symbol:       " + currentSymbol);
		System.out.println("oneEndOpening:       " + oneEndOpening);
	}
	
	public int getOpponentFour() {
		return opponentFour;
	}
	
	public Move getCounterMove() {
		return counterMove;
	}
	
	class Pair {
		int x;
		int y;
		
		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
