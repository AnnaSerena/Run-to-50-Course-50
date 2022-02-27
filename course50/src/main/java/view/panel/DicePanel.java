package view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.Border;

import controller.GuiController;
import model.Dice;
import utils.GuiConstant;
import view.object.DiceGraph;

public class DicePanel extends JPanel {

	private static final long serialVersionUID = 1L;

    private static int index = 1;
    
    private ArrayList<Dice> diceList;

	private ArrayList<DiceGraph> diceGraphList;
    private DiceGraph movableGraph;
    private GuiController controller;

	private Timer t = new Timer(100, null);
    
    private Border border = BorderFactory.createEtchedBorder();
    private DicePanelMouseListener dpml;
    
    public DicePanel(GuiController controller) {
    	this.controller = controller;
    	this.controller.setDicePanel(this);
    	this.setBorder(border);
    	this.setPreferredSize(new Dimension(600, 400));
    	
    	diceList = new ArrayList<Dice>();
    	diceGraphList = new ArrayList<DiceGraph>();
    	
    	//Ajout du listener des actions de la souris
    	dpml = new DicePanelMouseListener();
    	this.addMouseListener(dpml);
    	
    	addExecutionToTimer();
    	
    	InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        
    	//Gestion des actions du clavier
    	for(String k : new String[] {"right", "left", "up", "down"}) {
    		im.put(KeyStroke.getKeyStroke(k.toUpperCase()), "shift"+k);
    		
    		getActionMap().put("shift"+k, new AbstractAction(k){
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					moveDice(""+this.getValue("Name"));
                }
            });
    	}
    }
    
    public void drawDice(ArrayList<Dice> dices) {
    	diceGraphList = new ArrayList<DiceGraph>();
    	this.diceList = dices;
    	
		for(int i=0;i<diceList.size();i++) {
			Color color;
			
			switch (i) {
				case 0:
					color = new Color(195,150,0);
					break;
				case 1:
					color = new Color(118,19,90);
					break;
				case 2:
					color = new Color(128,128,95);
					break;
				case 3:
					color = new Color(192,192,192);
					break;
				default:
					color = new Color(255/i,255/i,255/i);
					break;
			}
			
			int width = this.getWidth()/diceList.size(), 
					height = (this.getHeight()-DiceGraph.DICE_W_H)/2;
        	diceGraphList.add(new DiceGraph(diceList.get(i), color, i==0? 10 : i*width, height));
        }
    }
    
    public void addExecutionToTimer() {
    	t.addActionListener(e-> {
    		for(Dice dice : diceList) 
            	dice.roll();
    		
    		repaint();
    		
    		if(controller.isConnect())
	    		try {
					controller.sendDice(diceList);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
    		
    		if(++index == 11) {
    			index = 1;
    			try {
					controller.sendMetrics(diceList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    			t.stop();
    		}
        });
    }
    
	public void moveDice(String value) {
		int shift = 5;
		if(movableGraph != null) {
			movableGraph.setX(value.equals("right") ? movableGraph.getX()+shift : 
				value.equals("left") ? movableGraph.getX()-shift : movableGraph.getX());
			movableGraph.setY(value.equals("down") ? movableGraph.getY()+shift : 
				value.equals("up") ? movableGraph.getY()-shift : movableGraph.getY());
		}
		repaint();
	}
	
    public void rollDices() {
        t.start();
    }
    
    public void selectDice(MouseEvent e) {
    	this.requestFocus();
    	
    	movableGraph = null;
    	for(DiceGraph g : diceGraphList) {
    		int diffX = e.getX() - g.getX(), diffY = e.getY() - g.getY(), wh = DiceGraph.getDiceWH();
    		
    		if((0 <= diffX) && (0 <= diffY) && (diffX <= wh) && (diffY <= wh))
    			movableGraph = g;
    	}
    }
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(GuiConstant.skinColor3);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        for(DiceGraph gr : diceGraphList) {
        	gr.drawDice(g);
        }
    }
	
    public ArrayList<DiceGraph> getDiceGraphList() {
		return diceGraphList;
	}

	public void setDiceGraphList(ArrayList<DiceGraph> diceGraphList) {
		this.diceGraphList = diceGraphList;
	}

	public ArrayList<Dice> getDiceList() {
		return diceList;
	}

	public void setDiceList(ArrayList<Dice> diceList) {
		this.diceList = diceList;
	}

	public DicePanelMouseListener getDpml() {
		return dpml;
	}

	public void setDpml(DicePanelMouseListener dpml) {
		this.dpml = dpml;
	}

	public class DicePanelMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			selectDice(e); //On selection un dé si on clique dessus
			
			//Si double-clique sur le bouton 1 on lance les dés sinon on arrête
			if(e.getButton() == MouseEvent.BUTTON1 && (e.getClickCount() > 1)) {
				rollDices(); 
			} else if(e.getClickCount() > 1)
				try {
					controller.updateScore(-2);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			else 
				t.stop();  
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}
