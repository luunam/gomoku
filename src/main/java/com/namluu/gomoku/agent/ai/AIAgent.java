package com.namluu.gomoku.agent.ai;

import java.util.Map;

import com.namluu.gomoku.agent.Agent;
import com.namluu.gomoku.game.Game;
import com.namluu.gomoku.game.IGameState;
import com.namluu.gomoku.heuristic.HeuristicEvaluator;
import com.namluu.gomoku.heuristic.ThreatEvaluator;
import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.Move;
import com.namluu.gomoku.model.PlayerSymbol;
import com.namluu.gomoku.utility.CheckGoalStateResult;

public class AIAgent implements Agent {
	
	private Game game;
	private PlayerSymbol symbol;
	private int depth;
	private IGameState currentState;
	private Boundary boundary;
	private Minimax minimax;
	
	public AIAgent(Game game, PlayerSymbol symbol) {
		this.game = game;
		this.symbol = symbol;
		this.depth = 2;
		
		boundary = new Boundary(-1, Integer.MAX_VALUE, -1, Integer.MAX_VALUE);
	}
	
	public AIAgent(Game game, PlayerSymbol symbol, Minimax minimax) {
		this.game = game;
		this.symbol = symbol;
		this.depth = 2;
		this.minimax = minimax;
		boundary = new Boundary(-1, Integer.MAX_VALUE, -1, Integer.MAX_VALUE);
	}
	
	public void requestMove(Move lastMove) {
		if (lastMove != null) {
			
			boundary = boundary.getNewBoundary(lastMove);
			GameState currentState = new GameState(game.getCurrentState(), symbol, boundary);
			
			Move move = searchForThreat();
			if (move == null) {
				MinimaxPair evaluate = this.search(0, currentState, 0, -1000000, 1000000);
				System.out.println("point: " + evaluate.successorValue);
				
				game.handleMove(evaluate.action, symbol);
			} else {
				System.out.println(move.getX() + " " + move.getY());
				game.handleMove(move, symbol);
			}
			
		} else {
			// We move first so choose a random move
			int boardSize = game.getBoardSize();
			int ranX = (int) (Math.random() * boardSize);
			int ranY = (int) (Math.random() * boardSize);
			
			game.handleMove(new Move(ranX, ranY, symbol), symbol);
		}
	}
	
	private Move searchForThreat() {
		ThreatEvaluator evaluator = new ThreatEvaluator(symbol);
		game.getCurrentState().traverseBoard(evaluator);
		Move counterMove = evaluator.getCounterMove();
		
		if (counterMove != null) {
			System.out.println("found counter move!");
			System.out.println(counterMove.getX() + " " + counterMove.getY());
		}
		return evaluator.getCounterMove();
	}
	
	public Move request(Move lastMove) {
		Move returnMove = null;
		
		if (lastMove != null) {
			
			boundary = boundary.getNewBoundary(lastMove);
			minimax.setBounary(boundary);
			
			return minimax.getNextMove(currentState);
			
		} else {
			// We move first so choose a random move
			int boardSize = game.getBoardSize();
			int ranX = (int) (Math.random() * boardSize);
			int ranY = (int) (Math.random() * boardSize);
			
			returnMove = new Move(ranX, ranY, symbol);
		}
		
		return returnMove;
	}
	
	public PlayerSymbol getSymbol() {
		return this.symbol;
	}
	
	private int evaluate(GameState state) {
		Board board = state.getState();
		CheckGoalStateResult goalStateResult = game.checkGoalState(state.getState());
		if (goalStateResult.isGameFinished()) {
			if (goalStateResult.getWinner() == symbol)
				return 1000;
			else
				return -1000;
		} else {
			
			// Traverse column after column:
			HeuristicEvaluator evaluator = new HeuristicEvaluator(symbol);
			board.traverseBoard(evaluator);
			
			return - 2*evaluator.getOpponentOpenThree() - evaluator.getOpponentFour();
		}
	}
	
	MinimaxPair search(int depth, GameState gameState, int noMinAgent, int alpha, int beta) {
		if (depth == this.depth) {
			return new MinimaxPair(evaluate(gameState), null);
		} else {
			if (noMinAgent == 0) {
				return maxValue(gameState, depth, alpha, beta);
			} else {
				return minValue(gameState, depth, alpha, beta);
			}
		}
	}
	
	MinimaxPair minValue(GameState gameState, int depth, int alpha, int beta) {
		int v = 1000000;
		Map<GameState, Move> possibleMoves = gameState.generateSuccessors(PlayerSymbol.CROSS);
		MinimaxPair ret = null;
		
		for (GameState state : possibleMoves.keySet()) {
		
			MinimaxPair stateValue = search(depth+1, state, 0, alpha, beta);
			if (stateValue.successorValue < v) {
				v = stateValue.successorValue;
				ret = new MinimaxPair(v, possibleMoves.get(state));
				
				if (v < alpha) {
					return ret;
				}
				beta = Math.min(beta, v);
			}
		}

		return ret;
	}
	
	MinimaxPair maxValue(GameState gameState, int depth, int alpha, int beta) {
		int v = -1000000;
		
		Map<GameState, Move> possibleMoves = gameState.generateSuccessors(PlayerSymbol.NOUGHT);
		MinimaxPair ret = null;
		
		for (GameState state : possibleMoves.keySet()) {
			
			MinimaxPair stateValue = search(depth, state, 1, alpha, beta);
			if (stateValue.successorValue > v) {
				v = stateValue.successorValue;
				ret = new MinimaxPair(v, possibleMoves.get(state));
				
				if (v > beta) {
					return ret;
				}
				alpha = Math.max(alpha, v);
			}
		}
		return ret;
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
