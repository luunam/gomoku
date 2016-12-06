package com.gomoku.main;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.gomoku.game.Game;
import com.gomoku.gui.GamePanel;

public class Application {
	
	private static int boardSize = 15;
	private static int cellSize = 50;
	
	public static void main(String[] args) {
		
		Game game = new Game(boardSize, cellSize);
		JFrame jframe = new JFrame("Gomoku");
		Container container = jframe.getContentPane();
		
		GamePanel gamePanel = new GamePanel(game);
		int panelSize = boardSize * cellSize;
		gamePanel.setPreferredSize(new Dimension(panelSize, panelSize));
		
		container.add(gamePanel);
		jframe.pack();
		jframe.setVisible(true);
		
		game.setGamePanel(gamePanel);

		game.start();
		//game.startGame();
	}
}
