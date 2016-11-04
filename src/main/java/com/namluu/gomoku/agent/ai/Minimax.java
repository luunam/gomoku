package com.namluu.gomoku.agent.ai;

import java.util.List;

import com.namluu.gomoku.game.IGameState;
import com.namluu.gomoku.heuristic.HeuristicCalculator;
import com.namluu.gomoku.model.Move;
import com.namluu.gomoku.model.PlayerSymbol;

public class Minimax {
	
	int depth;
	PlayerSymbol symbol;
	HeuristicCalculator calculator;
	Boundary boundary;
	
	public Minimax(int depth, PlayerSymbol symbol, Boundary boundary) {
		this.depth = depth;
		this.symbol = symbol;
		this.boundary = boundary;
	}
	
	Move getNextMove(IGameState state) {
		MinimaxPair result = this.value(0, state, 0, -1000000, 1000000);
		return result.action;
	}
	
	MinimaxPair value(int depth, IGameState gameState, int noMinAgent, int alpha, int beta) {
		if (depth == this.depth) {
			return new MinimaxPair(gameState.getHeuristic(calculator), null);
		} else {
			if (noMinAgent == 0) {
				return maxValue(gameState, depth, alpha, beta);
			} else {
				return minValue(gameState, depth, alpha, beta);
			}
		}
	}
	
	MinimaxPair minValue(IGameState gameState, int depth, int alpha, int beta) {
		int v = 1000000;
		List<Move> possibleMove = gameState.getLegalMove(); 
		MinimaxPair ret = null;
		
		for (Move move : possibleMove) {
			if (boundary.inside(move.getX(), move.getY())) {
				IGameState successorState = gameState.generateSuccessor(move);
				MinimaxPair stateValue = value(depth+1, successorState, 0, alpha, beta);
				if (stateValue.successorValue < v) {
					v = stateValue.successorValue;
					ret = new MinimaxPair(v, move);
					
					if (v < alpha) {
						return ret;
					}
					beta = Math.min(beta, v);
				}
			}
		}
		return ret;
	}
	
	MinimaxPair maxValue(IGameState gameState, int depth, int alpha, int beta) {
		int v = -1000000;
		
		List<Move> possibleMove = gameState.getLegalMove(); 
		MinimaxPair ret = null;
		
		for (Move move : possibleMove) {
			if (boundary.inside(move.getX(), move.getY())) {
				IGameState successorState = gameState.generateSuccessor(move);
				MinimaxPair stateValue = value(depth, successorState, 1, alpha, beta);
				if (stateValue.successorValue > v) {
					v = stateValue.successorValue;
					ret = new MinimaxPair(v, move);
					
					if (v > beta) {
						return ret;
					}
					alpha = Math.max(alpha, v);
				}
			}
		}
		return ret;
	}
	
	public void setBounary(Boundary boundary) {
		this.boundary = boundary;
	}
	
	class MinimaxPair {
		int successorValue = 0;
		Move action;
		
		MinimaxPair(int successorValue, Move action) {
			this.successorValue = successorValue;
			this.action = action;
		}
		
		public String toString() {
			if (action != null) {
				return "successor value: " + successorValue + " action " + action.getX() + ", " + action.getY();
			} else {
				return "successor value: " + successorValue + " action null";
			}
		}
	}
}
