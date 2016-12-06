package com.gomoku.agent;

import com.gomoku.model.Move;
import com.gomoku.model.PlayerSymbol;

public interface Agent {
	
	public void requestMove(Move move);
	
	public PlayerSymbol getSymbol();
	
}
