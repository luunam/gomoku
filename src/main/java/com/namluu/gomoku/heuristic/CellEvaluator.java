package com.namluu.gomoku.heuristic;

import com.namluu.gomoku.model.Board;

public interface CellEvaluator {
	
	public boolean finish();
	public void examine(Board board, int x, int y);
	public void reset();
}
