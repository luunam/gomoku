package com.gomoku.heuristic;

import com.gomoku.game.IGameState;

public interface HeuristicCalculator {
	public int calculate(IGameState gameState);
}
