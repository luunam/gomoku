package com.gomoku.heuristic;

import com.gomoku.game.GomokuGameState;
import com.gomoku.game.IGameState;
import com.gomoku.game.IGameState.GameResult;
import com.gomoku.model.Board;
import com.gomoku.model.PlayerSymbol;
import com.gomoku.utility.CheckGoalStateResult;

public class GomokuHeuristicCalculator implements HeuristicCalculator {
	
	PlayerSymbol symbol;
	public GomokuHeuristicCalculator(PlayerSymbol symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public int calculate(IGameState gameState) {
		
		GomokuGameState gomoku = (GomokuGameState) gameState;
		Board board = gomoku.getBoard();
		
		IGameState.GameResult gameResult = gomoku.isGoalState();
		if (gameResult == GameResult.WIN) {
			return 1000;
		} else if (gameResult == GameResult.LOSS) {
			return -1000;
		} else {
			// Traverse column after column:
			HeuristicEvaluator evaluator = new HeuristicEvaluator(symbol);
			board.traverseBoard(evaluator);
			
			return - 2*evaluator.getOpponentOpenThree() - evaluator.getOpponentFour();
		}
	}
}
