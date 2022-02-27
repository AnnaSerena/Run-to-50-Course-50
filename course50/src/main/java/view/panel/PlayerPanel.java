package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GuiController;
import exec.DiceRun;
import model.Player;
import utils.GuiComponent;
import utils.GuiConstant;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static ImageIcon rollIcon;
	public static ImageIcon waitIcon;
	
	private Player player;
	private GuiController controller;
	private JLabel labelScore;
	private JLabel labelState;
	private JLabel labelName;
	
	public PlayerPanel(GuiController controller) throws Exception {
		String playerName = JOptionPane.showInputDialog(null, "Joueur "+(Player.count+1), 
				"Veuillez saisir le nom", JOptionPane.QUESTION_MESSAGE);
		
		this.controller = controller;
		this.controller.getPlayerPanels().add(this);
		
		player = new Player(playerName == null ? 
				"Joueur_"+((Player.count+1)+(DiceRun.count == 1 ? 0 : DiceRun.count))
				:playerName, 0);

		if(controller.getFolder() == null) {
			
		}
		
		rollIcon = GuiComponent.createIcon(controller.getFolder(), GuiConstant.DICE_ROLL_ICON);
		waitIcon = GuiComponent.createIcon(controller.getFolder(), GuiConstant.DICE_WAIT_ICON);
		
		init();
	}

	public void init() {
		JPanel panelName = new JPanel();
		JPanel panelScore = new JPanel();
		
		labelName = new JLabel(player.getName());
		labelScore = new JLabel(""+player.getScore());
		
		labelName.setFont(GuiConstant.FONT);
		labelScore.setFont(GuiConstant.FONT);
		
		/**
		 * Expression ternaire equivalente au if / else 
		 */
		labelState = player.getPlayerPosition() == 1 ? //Si c'est le premier joueur
				new JLabel(rollIcon) // Si vrai on met l'icon playing
				: new JLabel(waitIcon); //Sinon on met l'icon waiting
		
		labelState.setBackground(Color.white);
		labelState.setOpaque(true);
		
		panelName.add(labelName);
		panelScore.add(labelScore);
		
		panelName.setBackground(GuiConstant.skinColor1);
		panelScore.setBackground(GuiConstant.skinColor2);
		
		panelName.setBorder(GuiConstant.BORDER);
		panelScore.setBorder(GuiConstant.BORDER);
		labelState.setBorder(GuiConstant.BORDER);
		
		this.setLayout(new BorderLayout());
		this.add(panelName, BorderLayout.NORTH);
		this.add(labelState, BorderLayout.CENTER);
		this.add(panelScore, BorderLayout.SOUTH);
	}

	public void updatePanel() {
		labelName.setText(player.getName());
		labelScore.setText(""+player.getScore());
		repaint();
	}
	
	public void updateScore(int total) {
		//Si on est sur le panneau actif
		if(player.isActive()) {
			//Si le total vaut 0, alors on a eu un 1, on remet le total à 0 et desactive ce player
			if(total == 0) {
				player.setScore(total);
				player.setActive(false);
			}
			//Si le total vaut -1, alors on a eu deux 1 on arrête le jeu
			else if (total == - 1) {
				if(controller.isConnect())
					controller.sendFinish("Jeu terminé, le joueur "+player.getName()+" a perdu");
				
				JOptionPane.showMessageDialog(controller.getDicePanel(), 
						"Jeu terminé, le joueur "+player.getName()+" a perdu");
				PlayPausePanel.getStop().doClick();
			}
			//Si c'est -2, alors l'utilisateur a passé son tour on garde son score et on le désactive
			else if(total == -2) {
				player.setScore(player.getScore());
				player.setActive(false);
			}
			//Sinon alors on laisse le player actif et on incrémente sont total
			else {
				player.setScore(player.getScore()+total);
				
				labelScore.setText(""+player.getScore());
				repaint();
				
				if(player.getScore() >= 50) {
					if(controller.isConnect())
						controller.sendFinish("Jeu terminé, le joueur "+player.getName()+" a gagné");
					
					JOptionPane.showMessageDialog(controller.getDicePanel(), 
							"Jeu terminé, le joueur "+player.getName()+" a gagné");
					
					PlayPausePanel.getStop().doClick();
				}
			}
				
		}else {
			if(total == 0 || total == -2) {
				player.setActive(true);
			}
		}
		
		//Si on est connecté
		if(controller.isConnect()) {
			//Si le player Actuel correspond à celui de l'application et est désactivé, 
			// on enlève le listener et on demande a l'adversaire de s'activer
			if((controller.current.equals(player.getName())) && !player.isActive() && 
					controller.getDicePanel().getMouseListeners().length >0) {
				controller.disableDicePanel(false);
				try {
					controller.getProcessor().sendEnableDicePanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		labelScore.setText(""+player.getScore());
		repaint();
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        labelState.setIcon(player.isActive() ? rollIcon : waitIcon);
        labelState.setBackground(player.isActive() ? Color.GREEN : Color.white);
        
    }
    
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
