package com.namluu.gomoku.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.namluu.gomoku.agent.ai.Boundary;
import com.namluu.gomoku.heuristic.CheckGoalStateEvaluator;
import com.namluu.gomoku.heuristic.HeuristicCalculator;
import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.Move;
import com.namluu.gomoku.model.PlayerSymbol;

public class GomokuGameState implements IGameState {
	private Board board;
	private PlayerSymbol currentTurn;
	private Boundary boundary;
	
	public GomokuGameState(int boardSize, int cellSize, PlayerSymbol currentTurn) {
		board = new Board(boardSize, cellSize);
		this.currentTurn = currentTurn;
	}
	
	private GomokuGameState(Board board, PlayerSymbol currentTurn) {
		this.board = board;
		this.currentTurn = currentTurn;
	}
	
	@Override
	public IGameState generateSuccessor(Move move) {
		Board newBoard = new Board(board);
		newBoard.set(move.getX(), move.getY(), move.getSymbol());
		currentTurn = currentTurn.next();
		return new GomokuGameState(newBoard, currentTurn);
	}

	@Override
	public boolean isValidMove(Move move) {
		if (board == null) {
			System.out.println("null");
		}
		return !board.isOccupied(move.getX(), move.getY());
	}

	@Override
	public List<Move> getLegalMove() {
		int minX = 0;
		int minY = 0;
		int maxX = board.getBoardSize() - 1;
		int maxY = board.getBoardSize() - 1;
		
		if (boundary != null) {
			minX = Math.max(0, boundary.getMinX() - 1);
			maxX = Math.min(maxX, boundary.getMaxX() + 1);
			minY = Math.max(0, boundary.getMinY() - 1);
			maxY = Math.min(maxY, boundary.getMaxY() + 1);
		}
		
		List<Move> move = new ArrayList<>();
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				if (!board.isOccupied(x, y)) {
					move.add(new Move(x, y, currentTurn));
				}
			}
		}
		return move;
	}

	@Override
	public GameResult isGoalState() {
		CheckGoalStateEvaluator checkGoalState = new CheckGoalStateEvaluator();
		board.traverseBoard(checkGoalState);
		
		if (checkGoalState.getWinner() == null) {
			return GameResult.UNFINISHED;
		} else if (checkGoalState.getWinner() == currentTurn) {
			return GameResult.WIN;
		} else {
			return GameResult.LOSS;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		board.draw(g);
	}

	@Override
	public int getHeuristic(HeuristicCalculator calculator) {
		
		return 0;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoundary(Boundary boundary) {
		this.boundary = boundary;
	}
}
