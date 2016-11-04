package com.namluu.gomoku.model;

public class Move {
	int x;
	int y;
	PlayerSymbol symbol;
	
	public Move(int x, int y, PlayerSymbol symbol) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public PlayerSymbol getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
