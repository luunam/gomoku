package com.namluu.gomoku.agent;

import com.namluu.gomoku.model.Move;
import com.namluu.gomoku.model.PlayerSymbol;

public interface Agent {
	
	public void requestMove(Move move);
	
	public PlayerSymbol getSymbol();
	
}
