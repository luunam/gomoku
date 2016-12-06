package com.gomoku.agent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.gomoku.game.Game;
import com.gomoku.model.Move;
import com.gomoku.model.PlayerSymbol;

public class HumanAgent extends MouseAdapter implements Agent {

	private Game game;
	private Move currentMove;
	private Boolean waiting;
	private PlayerSymbol symbol;
	private boolean moveIsReady;
	private int counter = 0;
	private int oldcounter = 0;
	
	public HumanAgent(Game game, PlayerSymbol symbol) {
		this.game = game;
		this.waiting = false;
		this.symbol = symbol;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (waiting) {
			System.out.println("click");
			waiting = false;
			
			int mouseX = e.getX();
			int mouseY = e.getY();
			
			int cellSize = game.getCellSize();
			int cellX = mouseX / cellSize;
			int cellY = mouseY / cellSize;
			
			currentMove = new Move(cellX, cellY, symbol);
			counter++;
			moveIsReady = true;
			System.out.println("move is ready");
			game.handleMove(currentMove, symbol);
		}
	}
	
	public void requestMove(Move lastMove) {
		this.waiting = true;
	}
	
	public PlayerSymbol getSymbol() {
		// TODO Auto-generated method stub
		return this.symbol;
	}
	
	public Move request(Move lastMove) {
		int tmp = counter;
		waiting = true;
		while (counter == oldcounter) {
			//System.out.println(1);
		}
		System.out.println("get move " + currentMove.getX() + " " + currentMove.getY());
		moveIsReady = false;
		return currentMove;
	}
	
}
