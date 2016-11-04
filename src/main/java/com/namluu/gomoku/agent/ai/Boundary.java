package com.namluu.gomoku.agent.ai;

import com.namluu.gomoku.model.Move;

public class Boundary {
	private final int maxX;
	private final int maxY;
	private final int minX;
	private final int minY;
	
	public Boundary(int maxX, int minX, int maxY, int minY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;
	}
	
	public Boundary getNewBoundary(Move move) {
		int lastMoveX = move.getX();
		int lastMoveY = move.getY();
		
		int newMaxX = Math.max(maxX, lastMoveX);
		int newMinX = Math.min(minX, lastMoveX);
		
		int newMaxY = Math.max(maxY, lastMoveY);
		int newMinY = Math.min(minY, lastMoveY);
		
		return new Boundary(newMaxX, newMinX, newMaxY, newMinY);
	}
	
	public void print() {
		System.out.format("maxX: %d, minX: %d, maxY: %d, minY: %d\n", maxX, minX, maxY, minY);
	}
	
	public boolean inside(int x, int y) {
		return minX <= x && x <= maxX && minY <= y && y <= maxY;
	}
	
	public int getMaxX() { return maxX; }
	public int getMaxY() { return maxY; }
	public int getMinX() { return minX; }
	public int getMinY() { return minY; }
}
