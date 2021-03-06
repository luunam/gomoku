package com.gomoku.utility.visitor;

import com.gomoku.model.Board;

public interface CellVisitor<T> {
	
	public boolean visit(Board board, int x, int y, T result);
	
	public void reset();
}
