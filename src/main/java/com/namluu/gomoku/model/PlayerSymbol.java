package com.namluu.gomoku.model;

public enum PlayerSymbol {
	NOUGHT, CROSS;
	
	private static PlayerSymbol[] vals = values();
	public PlayerSymbol next() {
		return vals[(this.ordinal() + 1) % vals.length];
	}
}
