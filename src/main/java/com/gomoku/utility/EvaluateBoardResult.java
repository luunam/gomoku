package com.gomoku.utility;

public class EvaluateBoardResult {
	private int opponentOpenThree = 0;
	private int ourOpenThree = 0;
	
	private int opponentFour = 0;
	private int ourFour = 0;
	
	public int getOpponentOpenThree() {
		return opponentOpenThree;
	}
	public void incOpponentOpenThree() {
		this.opponentOpenThree++;
	}
	public int getOurOpenThree() {
		return ourOpenThree;
	}
	public void incOurOpenThree() {
		this.ourOpenThree++;
	}
	public int getOpponentFour() {
		return opponentFour;
	}
	public void incOpponentFour() {
		this.opponentFour++;
	}
	public int getOurFour() {
		return ourFour;
	}
	public void incOurFour() {
		this.ourFour++;
	}
	
	public void printRecord() {
		System.out.println("Our open three:      " + ourOpenThree);
		System.out.println("Our four:            " + ourFour);
		System.out.println("Opponent open three: " + opponentOpenThree);
		System.out.println("Opponent four:       " + opponentFour);
	}
}
