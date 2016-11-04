package com.namluu.gomoku.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.SwingUtilities;

import com.namluu.gomoku.agent.Agent;
import com.namluu.gomoku.agent.HumanAgent;
import com.namluu.gomoku.agent.ai.AIAgent;
import com.namluu.gomoku.gui.GamePanel;
import com.namluu.gomoku.heuristic.CheckGoalStateEvaluator;
import com.namluu.gomoku.model.Board;
import com.namluu.gomoku.model.Move;
import com.namluu.gomoku.model.PlayerSymbol;
import com.namluu.gomoku.utility.CheckGoalStateResult;
import com.namluu.gomoku.utility.traversal.AuxilaryDiagonalTraversal;
import com.namluu.gomoku.utility.traversal.ColumnTraversal;
import com.namluu.gomoku.utility.traversal.MainDiagonalTraversal;
import com.namluu.gomoku.utility.traversal.RowTraversal;
import com.namluu.gomoku.utility.visitor.CheckGoalStateVisitor;

public class Game {
	
	private final int BOARD_SIZE;
	private final int CELL_SIZE;
	
	private Board board;
	
	private PlayerSymbol currentTurn;
	
	private List<Agent> agents = new ArrayList<Agent>();
	
	private EnumMap<PlayerSymbol, Agent> maps = new EnumMap<PlayerSymbol, Agent>(PlayerSymbol.class);
	
	private GamePanel gamePanel;
	
	private Move lastMove;
	
	private IGameState currentState;
	
	private Agent currentAgent;
	
	public Game(int boardSize, int cellSize) {
		this.BOARD_SIZE = boardSize;
		this.CELL_SIZE = cellSize;
		
		board = new Board(boardSize, cellSize);
		
		setCurrentTurn(PlayerSymbol.CROSS);
		
		Agent humanAgent = new HumanAgent(this, PlayerSymbol.CROSS);
		Agent aiAgent = new AIAgent(this, PlayerSymbol.NOUGHT);
		
		currentAgent = humanAgent;
		
		agents.add(humanAgent);
		agents.add(aiAgent);
		
		currentState = new GomokuGameState(boardSize, cellSize, currentTurn);
		
		initializeMap();
	}
	
	public Game(int boardSize, int cellSize, GamePanel gamePanel) {
		this.BOARD_SIZE = boardSize;
		this.CELL_SIZE = cellSize;
		
		board = new Board(boardSize, cellSize);
		
		setCurrentTurn(PlayerSymbol.CROSS);
		
		Agent humanAgent = new HumanAgent(this, PlayerSymbol.CROSS);
		Agent aiAgent = new AIAgent(this, PlayerSymbol.NOUGHT);
		
		currentAgent = humanAgent;
		
		agents.add(humanAgent);
		agents.add(aiAgent);
		
		currentState = new GomokuGameState(boardSize, cellSize, currentTurn);
		gamePanel.setState(currentState);
		
		initializeMap();
	}
	
	public void start() {
		maps.get(getCurrentTurn()).requestMove(lastMove);
	}
	
	public void startGame() {
//		while(true) {
//			// main game loop
//			lastMove = maps.get(getCurrentTurn()).request(lastMove);
//			if (currentState.isValidMove(lastMove)) {
//				currentState = currentState.generateSuccessor(lastMove);
//				gamePanel.setState(currentState);
//				currentAgent = getNextAgent();
//				setCurrentTurn(getCurrentTurn().next());
//				repaint();
//			}
//		}
	}
	
	private Agent getNextAgent() {
		int agentIdx = agents.indexOf(currentAgent);
		int nextAgentIdx = (agentIdx + 1) % agents.size();
		return agents.get(nextAgentIdx);
	}
	
	private void initializeMap() {
		for (Agent agent : agents) {
			maps.put(agent.getSymbol(), agent);
		}
	}
	
	public int getBoardSize() {
		return this.BOARD_SIZE;
	}
	
	public int getCellSize() {
		return this.CELL_SIZE;
	}
	
	public void render(Graphics g) {
		board.draw((Graphics2D) g);
	}
	
	/*
	 * Handle move event, return true if this is valid move
	 */
	public void handleMove(Move move, PlayerSymbol turn) {
		if (turn.equals(this.getCurrentTurn())) {
			int x = move.getX();
			int y = move.getY();
			
			if (!board.isOccupied(x, y)) {
				lastMove = move;
				board.set(x, y, turn);
				
				repaint();
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if (isGoalState(Game.this.board)) {
							System.out.println(getCurrentTurn() + " WIN");
							System.exit(0);
						}
						setCurrentTurn(getCurrentTurn().next());
						maps.get(getCurrentTurn()).requestMove(lastMove);
					}
				});
				
			} else {
				maps.get(getCurrentTurn()).requestMove(lastMove);
			}
		}
	}
	
	public void repaint() {
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void setGamePanel(GamePanel panel) {
		this.gamePanel = panel;
		gamePanel.setState(currentState);
		for (Agent agent : agents) {
			if (agent instanceof HumanAgent) {
				gamePanel.addMouseListener((HumanAgent)agent);
			}
		}
	}
	
	public Board getCurrentState() {
		return board;
	}
	
	/*
	 * Check if this is a goal state without knowing what the last move is
	 */
	public boolean isGoalState(Board gameState) {
		
		CheckGoalStateEvaluator evaluator = new CheckGoalStateEvaluator();
		gameState.traverseBoard(evaluator);
		
		return evaluator.finish();
	}
	
	public CheckGoalStateResult checkGoalState(Board gameState) {
		CheckGoalStateResult result = new CheckGoalStateResult();
		CheckGoalStateVisitor cellVisitor = new CheckGoalStateVisitor();
		
		RowTraversal<CheckGoalStateResult> rowTraversal = 
				new RowTraversal<CheckGoalStateResult>(gameState, cellVisitor, result);
		ColumnTraversal<CheckGoalStateResult> columnTraversal = 
				new ColumnTraversal<CheckGoalStateResult>(gameState, cellVisitor, result);
		MainDiagonalTraversal<CheckGoalStateResult> mainDiagonalTraversal = 
				new MainDiagonalTraversal<CheckGoalStateResult>(gameState, cellVisitor, result);
		AuxilaryDiagonalTraversal<CheckGoalStateResult> auxilaryDiagonalTraversal = 
				new AuxilaryDiagonalTraversal<CheckGoalStateResult>(gameState, cellVisitor, result);
		
		rowTraversal.traverse();
		columnTraversal.traverse();
		mainDiagonalTraversal.traverse();
		auxilaryDiagonalTraversal.traverse();
		
		return result;
	}

	public PlayerSymbol getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(PlayerSymbol currentTurn) {
		this.currentTurn = currentTurn;
	}
}
