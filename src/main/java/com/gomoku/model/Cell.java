package com.gomoku.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * This class is package private
 * @author namluu
 *
 */
class Cell {
	private PlayerSymbol symbol;
	private int size;
	
	// the grid x and y coordinate
	private int x; 
	private int y; 
	
	// the real x and y coordinate 
	private int graphicX;
	private int graphicY;
	
	// offset from the size of cross and nought compare to the size of the grid
	private int offset;
	
	Cell(int size, int x, int y) {
		this.size = size;
		this.offset = size/10;
		
		this.x = x;
		this.y = y;
		
		this.graphicX = this.x * size;
		this.graphicY = this.y * size;
		
		this.symbol = null;
	}
	
	Cell(Cell cell) {
		this.size = cell.size;
		this.offset = this.size / 10;
		
		this.x = cell.x;
		this.y = cell.y;
		
		this.graphicX = this.x * size;
		this.graphicY = this.y * size;
		
		
		this.symbol = cell.symbol;
	}
	
	void setValue(PlayerSymbol state) {
		this.symbol = state;
	}
	
	PlayerSymbol getValue() {
		return this.symbol;
	}
	
	void draw(Graphics2D graphic) {
		Rectangle2D boundary = new Rectangle2D.Double(graphicX, graphicY, size, size);
		graphic.setBackground(Color.WHITE);
		graphic.draw(boundary);
		if (symbol != null) {
			switch (symbol) {
			case CROSS:
				drawCross(graphic);
				break;
			case NOUGHT:
				drawNought(graphic);
				break;
			}
		}
	}
	
	private void drawCross(Graphics2D graphic) {
		// translate the coordinate system to the current grid
		graphic.setColor(Color.BLUE);
		int offset = 2*this.offset;
		graphic.translate(graphicX, graphicY);
		graphic.drawLine(offset, offset, size - offset, size - offset);
		graphic.drawLine(size - offset, offset, offset, size - offset);
		
		// After we are done drawing, translate the coordinate system back to the origin
		graphic.translate(-graphicX, -graphicY);
		
		graphic.setColor(Color.BLACK);
		
	}
	
	private void drawNought(Graphics2D graphic) {
		// translate the coordinate system to the current grid
		graphic.setColor(Color.RED);
		graphic.translate(graphicX, graphicY);
		int radius = size/2 - offset;
		
		graphic.drawOval(size/2 - radius/2, size/2 - radius/2, radius, radius);
		
		// After we are done drawing, translate the coordinate system back to the origin
		graphic.translate(-graphicX, -graphicY);
		graphic.setColor(Color.BLACK);
	}
	
	boolean isNought() {
		return symbol == PlayerSymbol.NOUGHT;
	}
	
	boolean isCross() {
		return symbol == PlayerSymbol.CROSS;
	}
	
	boolean isOccupied() {
		return isNought() || isCross();
	}
	
	void setSymbol(PlayerSymbol state) {
		this.symbol = state;
	}
	
	PlayerSymbol getSymbol() {
		return this.symbol;
	}
}
