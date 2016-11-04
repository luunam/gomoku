package com.namluu.gomoku.utility.traversal;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.utility.visitor.CellVisitor;

public class ColumnTraversal<T> extends MatrixTraversal<T> {

	public ColumnTraversal(Board cells, CellVisitor<T> visitor, T result) {
		super(cells, visitor, result);
	}
	
	public T traverse() {
		for (int rowIdx = 0; rowIdx < boardSize; rowIdx++) {
			for (int colIdx = 0; colIdx < boardSize; colIdx++) {
				if (cellVisitor.visit(board, rowIdx, colIdx, result)) {
					return result;
				}
			}
			cellVisitor.reset();
		}
		return result;
	}
}
