package com.gomoku.utility.traversal;

import com.gomoku.model.Board;
import com.gomoku.utility.visitor.CellVisitor;

public class MainDiagonalTraversal<T> extends MatrixTraversal<T> {

	public MainDiagonalTraversal(Board board, CellVisitor<T> cellVisitor, T result) {
		super(board, cellVisitor, result);
	}
	
	public T traverse() {
		for (int diff = -(boardSize - 1); diff <= boardSize - 1; diff++) {
			int xMax = Math.min(boardSize-1, boardSize + diff - 1);
			int xMin = Math.max(0, 0 + diff);
			for (int x = xMin; x <= xMax; x++) {
				int y = x - diff;
				if (cellVisitor.visit(board, x, y, result)) {
					return result;
				}
			}
			cellVisitor.reset();
		}
		return result;
	}
	
}
