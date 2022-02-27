package view.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import controller.GuiController;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel dicePanel, playerPanel_1,playerPanel_2, optionPanel, 
	playPausePanel, tempCenterPanel, panel;
	
	public MainPanel(GuiController controller) {
		controller.setMainPanel(this);
		
		tempCenterPanel = new JPanel();
		tempCenterPanel.setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		this.setDicePanel(controller.getInstructionPanel());
		this.setOptionPanel(new JPanel());
		this.setPlayerPanel_1(controller.getPlayerPanels().get(0));
		this.setPlayerPanel_2(controller.getPlayerPanels().get(1));
		this.setPlayPausePanel(controller.getPlayPausePanel());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, new DisplayPanel());
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5d);
		
		this.setLayout(new BorderLayout());
		this.add(splitPane, BorderLayout.CENTER);
		
		panel.add(tempCenterPanel, BorderLayout.CENTER);
		panel.add(playerPanel_1, BorderLayout.WEST);
		panel.add(playerPanel_2, BorderLayout.EAST);
		
		refresh();
	}
	
	public void refresh() {
		tempCenterPanel.removeAll();
		
		tempCenterPanel.add(optionPanel, BorderLayout.NORTH);
		tempCenterPanel.add(dicePanel, BorderLayout.CENTER);
		tempCenterPanel.add(playPausePanel, BorderLayout.SOUTH);
		
		this.updateUI();
	}
	
	public JPanel getDicePanel() {
		return dicePanel;
	}
	public void setDicePanel(JPanel dicePanel) {
		this.dicePanel = dicePanel;
	}

	public JPanel getPlayerPanel_1() {
		return playerPanel_1;
	}
	public void setPlayerPanel_1(JPanel playerPanel_1) {
		this.playerPanel_1 = playerPanel_1;
	}

	public JPanel getPlayerPanel_2() {
		return playerPanel_2;
	}
	public void setPlayerPanel_2(JPanel playerPanel_2) {
		this.playerPanel_2 = playerPanel_2;
	}

	public JPanel getOptionPanel() {
		return optionPanel;
	}
	public void setOptionPanel(JPanel optionPanel) {
		this.optionPanel = optionPanel;
	}

	public JPanel getPlayPausePanel() {
		return playPausePanel;
	}
	public void setPlayPausePanel(JPanel playPausePanel) {
		this.playPausePanel = playPausePanel;
	}
}
