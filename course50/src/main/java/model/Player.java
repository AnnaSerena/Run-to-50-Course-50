package model;

import java.io.Serializable;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//Compteur statique permettant de savoir le nombre de joueurs
	public static int count = 0;
			
	private String name;
	private int score;
	private boolean isActive;
	private int playerPosition;
	
	public Player(String playerName, int score) {
		setName(playerName);
		setScore(score);
		setPlayerPosition(++count);
		setActive(count == 1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getPlayerPosition() {
		return playerPosition;
	}
	public void setPlayerPosition(int playerPosition) {
		this.playerPosition = playerPosition;
	}
	
	public String toString() {
		return "[Player: "+name+" postion: "+playerPosition+" score: "+score+" isActive: "+isActive+"]";
	}
}
