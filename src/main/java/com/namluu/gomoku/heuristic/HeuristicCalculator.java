package com.namluu.gomoku.heuristic;

import com.namluu.gomoku.game.IGameState;

public interface HeuristicCalculator {
	public int calculate(IGameState gameState);
}
