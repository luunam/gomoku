package com.gomoku.utility.traversal;

import com.gomoku.model.Board;
import com.gomoku.utility.visitor.CellVisitor;

public class AuxilaryDiagonalTraversal<T> extends MatrixTraversal<T> {

	public AuxilaryDiagonalTraversal(Board board, CellVisitor<T> cellVisitor, T result) {
		super(board, cellVisitor, result);
	}
	
	public T traverse() {
		for (int sum = 0; sum <= 2*(boardSize-1); sum++) {
			int xMax = Math.min(sum, boardSize-1);
			int xMin = Math.max(0, sum - boardSize + 1);
			for (int x = xMin; x <=xMax; x++) {
				int y = sum - x;
				if (cellVisitor.visit(board, x, y, result)) {
					return result;
				}
			}
			cellVisitor.reset();
		
		}
		return result;
	}
	
}
