package com.namluu.gomoku.model;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.namluu.gomoku.game.IGameState;
import com.namluu.gomoku.heuristic.CellEvaluator;

public class Board {
	private final int BOARD_SIZE;
	private final int CELL_SIZE;
	
	private Cell[][] cells;
	public Board(int boardSize, int cellSize) {
		this.BOARD_SIZE = boardSize;
		this.CELL_SIZE = cellSize;
		
		cells = new Cell[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				cells[i][j] = new Cell(cellSize, i, j);
			}
		}
	}
	
	public Board(String fileName) {
		int boardSize = 0;
		int cellSize = 0;
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		try (Scanner scanner = new Scanner(file)) {
			boardSize = Integer.parseInt(scanner.nextLine());
			cellSize = 0;
			
			cells = new Cell[boardSize][boardSize];
			
			for (int i = 0; i < boardSize; i++) {
				String line = scanner.nextLine();
				for (int j = 0; j < line.length(); j++) {
					cells[i][j] = new Cell(0, i, j);
					if (line.charAt(j) == 'x') 
						cells[i][j].setSymbol(PlayerSymbol.CROSS);
					else if (line.charAt(j) == 'o') 
						cells[i][j].setSymbol(PlayerSymbol.NOUGHT);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} finally {
			this.BOARD_SIZE = boardSize;
			this.CELL_SIZE = cellSize;
		}
	}
	
	// Copy constructor
	public Board(Board board) {
		this.BOARD_SIZE = board.getBoardSize();
		this.CELL_SIZE = board.getCellSize();
		
		cells = new Cell[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				cells[i][j] = new Cell(board.get(i, j));
			}
		}
	}
	
	public int getBoardSize() {
		return BOARD_SIZE;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				cells[i][j].draw(g);
			}
		}
	}
	
	public Cell get(int x, int y) {
		return cells[x][y];
	}
	
	public void set(int x, int y, PlayerSymbol symbol) {
		cells[x][y].setSymbol(symbol);
	}
	
	public String toString() {
		return null;
	}
	
	public boolean isOccupied(int x, int y) {
		return cells[x][y].isOccupied();
	}
	
	public PlayerSymbol getSymbol(int x, int y) {
		return cells[x][y].getSymbol();
	}
	
	public Cell[][] getCells() {
		return this.cells;
	}
	
	public int getCellSize() {
		return this.CELL_SIZE;
	}
	
	public void traverseBoard(CellEvaluator evaluator) {
		traverseVertically(evaluator);
		traverseHorizontally(evaluator);
		traverseDiagonallyInMainDirection(evaluator);
		traverseDiagonallyInAuxilaryDirection(evaluator);
	}
	
	public void traverseHorizontally(CellEvaluator evaluator) {
		for (int colIdx = 0; colIdx < BOARD_SIZE; colIdx++) {
			for (int rowIdx = 0; rowIdx < BOARD_SIZE; rowIdx++) {
				evaluator.examine(this, rowIdx, colIdx);
				if (evaluator.finish()) {
					return;
				}
			}
			evaluator.reset();
		}
	}
	
	public void traverseVertically(CellEvaluator evaluator) {
		for (int rowIdx = 0; rowIdx < BOARD_SIZE; rowIdx++) {
			for (int colIdx = 0; colIdx < BOARD_SIZE; colIdx++) {
				evaluator.examine(this, rowIdx, colIdx);
				if (evaluator.finish()) {
					return;
				}
			}
			evaluator.reset();
		}
	}
	
	public void traverseDiagonallyInMainDirection(CellEvaluator evaluator) {
		for (int diff = -(BOARD_SIZE - 1); diff <= BOARD_SIZE - 1; diff++) {
			int xMax = Math.min(BOARD_SIZE-1, BOARD_SIZE + diff - 1);
			int xMin = Math.max(0, 0 + diff);
			for (int x = xMin; x <= xMax; x++) {
				int y = x - diff;
				evaluator.examine(this, x, y);
				if (evaluator.finish()) {
					return;
				}
			}
			evaluator.reset();
		}
	}
	
	public void traverseDiagonallyInAuxilaryDirection(CellEvaluator evaluator) {
		for (int sum = 0; sum <= 2*(BOARD_SIZE-1); sum++) {
			int xMax = Math.min(sum, BOARD_SIZE-1);
			int xMin = Math.max(0, sum - BOARD_SIZE + 1);
			for (int x = xMin; x <=xMax; x++) {
				int y = sum - x;
				evaluator.examine(this, x, y);
				if (evaluator.finish()) {
					return;
				}
			}
			evaluator.reset();
		}
	}
}
