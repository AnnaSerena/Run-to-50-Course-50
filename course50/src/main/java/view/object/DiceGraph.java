package view.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import model.Dice;

public class DiceGraph implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int DICE_W_H = 80;
    private static final int SIDE = 20;
    private static final int ROUND = 20;
    
	private Dice dice;

	private Color color;
	private int x, y;
		
	public DiceGraph(Dice dice, Color color, int x, int y) {
		this.dice = dice;
		this.color = color;
		this.x = x;
		this.y = y;
	}
		
	public void drawDice(Graphics g) {
		Graphics2D graph = (Graphics2D)g;
		
//		graph.setColor(color);
//		graph.drawRoundRect(x-1, y-1, DICE_W_H+1, DICE_W_H+1, ROUND, ROUND);
		graph.setColor(color);
		graph.fillRoundRect(x, y, DICE_W_H, DICE_W_H, ROUND, ROUND);
		graph.setColor(Color.white);
		
		switch (dice.getValue()) {
		case 1:
			graph.fillOval(x+(DICE_W_H - SIDE)/2, y+(DICE_W_H - SIDE)/2, SIDE, SIDE);
			break;
		case 2:
			graph.fillOval(x+5, y+5, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			break;
		case 3:
			graph.fillOval(x+5, y+5, SIDE, SIDE);
			graph.fillOval(x+(DICE_W_H - SIDE)/2, y+(DICE_W_H - SIDE)/2, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			break;
		case 4:
			graph.fillOval(x+5, y+5, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+5, SIDE, SIDE);
			graph.fillOval(x+5, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			break;
		case 5:
			graph.fillOval(x+5, y+5, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+5, SIDE, SIDE);
			graph.fillOval(x+(DICE_W_H - SIDE)/2, y+(DICE_W_H - SIDE)/2, SIDE, SIDE);
			graph.fillOval(x+5, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			break;
		case 6:
			graph.fillOval(x+5, y+5, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+5, SIDE, SIDE);
			graph.fillOval(x+5, y+(DICE_W_H - SIDE)/2, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+(DICE_W_H - SIDE)/2, SIDE, SIDE);
			graph.fillOval(x+5, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			graph.fillOval(x+DICE_W_H-5-SIDE, y+DICE_W_H-5-SIDE, SIDE, SIDE);
			break;
		}
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public static int getDiceWH() {
		return DICE_W_H;
	}
	
	public Dice getDice() {
		return dice;
	}
	public void setDice(Dice dice) {
		this.dice = dice;
	}
	
	public String toString() {
		return "DiceGraph [x="+x+", y="+y+", color="+color+"] Dice ["+dice+"]";
    }
}
