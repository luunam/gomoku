package com.namluu.gomoku.utility.visitor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.namluu.gomoku.heuristic.CheckGoalStateEvaluator;
import com.namluu.gomoku.heuristic.HeuristicEvaluator;
import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.PlayerSymbol;
import com.namluu.gomoku.utility.EvaluateBoardResult;


public class TestEvaluateBoardVisitor {
	
	PlayerSymbol ourSymbol;
	PlayerSymbol opponentSymbol;
	
	@Before
	public void setup() {
		
		ourSymbol = PlayerSymbol.CROSS;
		opponentSymbol = PlayerSymbol.NOUGHT;
	}
	
	@Test
	public void testBoard1() {
		Board board = new Board("board1");
		
		HeuristicEvaluator evaluator = new HeuristicEvaluator(ourSymbol);
		board.traverseBoard(evaluator);
		
		evaluator.printRecord();
		
		Assert.assertTrue(evaluator.getOpponentFour() == 1);
		Assert.assertTrue(evaluator.getOurOpenThree() == 1);
		Assert.assertTrue(evaluator.getOpponentOpenThree() == 0);
		Assert.assertTrue(evaluator.getOurFour() == 0);
	}
	
	@Test
	public void testBoard2() {
		System.out.println("Test board 2");
		Board board = new Board("board2");
		
		HeuristicEvaluator evaluator = new HeuristicEvaluator(ourSymbol);
		board.traverseBoard(evaluator);
		
		evaluator.printRecord();
		
		Assert.assertTrue(evaluator.getOpponentFour() == 2);
		Assert.assertTrue(evaluator.getOurOpenThree() == 1);
		Assert.assertTrue(evaluator.getOpponentOpenThree() == 0);
		Assert.assertTrue(evaluator.getOurFour() == 1);
		
	}
	
	@Test
	public void testBoard3() {
		System.out.println("Test board 3");
		Board board = new Board("board3");
		HeuristicEvaluator evaluator = new HeuristicEvaluator(ourSymbol);
		
		board.traverseBoard(evaluator);
		
		
		Assert.assertTrue(evaluator.getOpponentFour() == 3);
		Assert.assertTrue(evaluator.getOurOpenThree() == 1);
		Assert.assertTrue(evaluator.getOpponentOpenThree() == 4);
		Assert.assertTrue(evaluator.getOurFour() == 1);
	}
	
}
