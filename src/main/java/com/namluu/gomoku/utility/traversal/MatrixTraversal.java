package com.namluu.gomoku.utility.traversal;

import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.utility.visitor.CellVisitor;

public abstract class MatrixTraversal<T> {
	Board board;
	int boardSize;
	CellVisitor<T> cellVisitor;
	T result;
	
	public MatrixTraversal(Board board, CellVisitor<T> cellVisitor, T result) {
		this.board = board;
		this.cellVisitor = cellVisitor;
		this.boardSize = board.getBoardSize();
		this.result = result;
	}
	
	public abstract T traverse();
}
