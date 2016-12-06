package com.gomoku.game;

import java.awt.Graphics2D;
import java.util.List;

import com.gomoku.heuristic.HeuristicCalculator;
import com.gomoku.model.Move;

public interface IGameState {
	
	public IGameState generateSuccessor(Move move);
	
	public boolean isValidMove(Move move);
	
	public List<Move> getLegalMove();
	
	public GameResult isGoalState();
	
	public void draw(Graphics2D g);
	
	public int getHeuristic(HeuristicCalculator calculator);
	
	enum GameResult {
		WIN, LOSS, UNFINISHED;
	}
}
