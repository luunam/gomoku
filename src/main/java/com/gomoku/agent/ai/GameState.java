package com.gomoku.agent.ai;

import java.util.HashMap;
import java.util.Map;

import com.gomoku.model.Board;
import com.gomoku.model.Move;
import com.gomoku.model.PlayerSymbol;

class GameState {
	private final Board board;
	private Boundary boundary;
	
	GameState(Board cells, PlayerSymbol symbol, Boundary boundary) {
		this.board = cells;
		this.boundary = boundary;
	}
	
	
	Map<GameState, Move> generateSuccessors(PlayerSymbol symbol) {
		int boardSize = board.getBoardSize();
		
		Map<GameState, Move> successors = new HashMap<>();
		
		int minX = Math.max(0, boundary.getMinX() - 1);
		int maxX = Math.min(boardSize - 1, boundary.getMaxX() + 1);
		int minY = Math.max(0, boundary.getMinY() - 1);
		int maxY = Math.min(boardSize - 1, boundary.getMaxY() + 1);
		
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				if (board.getSymbol(i, j) == null) {
					Board newBoard = new Board(board);
					newBoard.set(i, j, symbol);
					Move move = new Move(i, j, symbol);
					Boundary newBoundary = boundary.getNewBoundary(move);
					successors.put(new GameState(newBoard, symbol, newBoundary), move);
				}
			}
		}
		return successors;
	}
	
	Board getState() {
		return board;
	}
}
