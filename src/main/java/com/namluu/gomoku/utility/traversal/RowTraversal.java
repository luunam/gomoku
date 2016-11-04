package com.namluu.gomoku.utility.traversal;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.utility.visitor.CellVisitor;

public class RowTraversal<T> extends MatrixTraversal<T>{
	
	public RowTraversal(Board board, CellVisitor<T> cellVisitor, T result) {
		super(board, cellVisitor, result);
	}
	
	public T traverse() {
		for (int colIdx = 0; colIdx < boardSize; colIdx++) {
			for (int rowIdx = 0; rowIdx < boardSize; rowIdx++) {
				if (cellVisitor.visit(board, rowIdx, colIdx, result)) {
					return result;
				}
			}
			cellVisitor.reset();
		}
		return result;
	}
	
}
