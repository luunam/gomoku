package com.gomoku.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.gomoku.game.Game;
import com.gomoku.game.IGameState;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	private final Game game;
	private IGameState gameState;
	
	public GamePanel(Game newGame) {
		this.game = newGame;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
//		if (gameState != null) {
//			gameState.draw((Graphics2D) g);
//		}
	}
	
	public void setState(IGameState state) {
		this.gameState = state;
	}
}
