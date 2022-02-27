package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.Dice;
import model.DiceProcessor;
import model.DiceServer;
import view.panel.DicePanel;
import view.panel.InstructionPanel;
import view.panel.MainPanel;
import view.panel.OptionPanel;
import view.panel.PlayPausePanel;
import view.panel.PlayerPanel;

public class GuiController {
	
	private ArrayList<PlayerPanel> playerPanels = new ArrayList<PlayerPanel>();

	private String folder;
	
	private DicePanel dicePanel;
	private MainPanel mainPanel;
	private PlayPausePanel playPausePanel;
	private OptionPanel optionPanel;
	private InstructionPanel instructionPanel;
	private DiceProcessor processor;
	private DiceServer server;
	public String current;
	
	boolean isConnect;

	public GuiController(String folder) {
		this.folder = folder;
	}
	
	public void sendMetrics(List<Dice> diceList) throws Exception {
		int total = 0;
		int nbrZero=0;
		
		for(Dice dice : diceList)
			if(dice.getValue() == 1) {
				nbrZero++;
				total = nbrZero == 1 ? 0 : -1;
			} else {
				//Si on a déjà 1 dé qui vaut 1, on laisse le total à zero
				//Si on a aucun dé qui vaut 1, on incrémente le total actuel
				//Sinon, donc si on a plus de 1 dé qui vaut 1, on met -1 pour arrêter le jeu
				total = nbrZero == 0 ? total+dice.getValue() : nbrZero == 1 ? 0 : -1;
			}
				
		updateScore(total);
	}
	
	public void updateScore(int total) throws Exception {
		for(int i=0; i<playerPanels.size();i++) {
			playerPanels.get(i).updateScore(total); //MAJ du score sur le panneaux joueurs
			
			//Si on joue en mode connecté, il faut transférer la MAJ au joueur connecté
			if(isConnect) {
				processor.sendPlayer(i);
			}
		}
	}
	
	public void updateUI() {
		optionPanel.updateUI();
		dicePanel.updateUI();
		playPausePanel.updateUI();
		mainPanel.updateUI();
	}
	public void updateDicesToDicePanel(int diceNumber) {
		ArrayList<Dice> diceList = new ArrayList<Dice>();
		
		for(int i=0;i<diceNumber;i++)
			diceList.add(new Dice(6,diceNumber));
		
		dicePanel.drawDice(diceList);
		dicePanel.repaint();
		
		if(isConnect)
			try {
				processor.updateDicesToDiceServer(diceList);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void startGame() {
		dicePanel.setSize(this.getInstructionPanel().getSize());
		mainPanel.setDicePanel(dicePanel);
		mainPanel.setOptionPanel(optionPanel);
		mainPanel.refresh();
		optionPanel.init();
		if(isConnect)
			try {
				sendDice(dicePanel.getDiceList());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void stopGame() {
		for(int i=0;i<playerPanels.size();i++) {
			playerPanels.get(i).getPlayer().setScore(0);
			playerPanels.get(i).getPlayer().setActive(i==0);
			playerPanels.get(i).repaint();
		}
		
		mainPanel.setOptionPanel(new JPanel());
		mainPanel.setDicePanel(this.getInstructionPanel());
		mainPanel.refresh();
	}
	
	public void connect(String host, int port) throws Exception {
		processor = new DiceProcessor();
		processor.connect(host, port);
		processor.setController(this);
		processor.open();
		current = playerPanels.get(0).getPlayer().getName();
		
		processor.getPlayer(1);
		processor.sendPlayer(0);
		sendDice(dicePanel.getDiceList());
		processor.sendDisableDicePanel();
		isConnect = true;
	}
	
	public void disableDicePanel(boolean enable) {
		if(enable)
			dicePanel.addMouseListener(dicePanel.getDpml());
		else
			dicePanel.removeMouseListener(dicePanel.getMouseListeners()[0]);
	}
	
	public void sendDice(ArrayList<Dice> diceList) throws Exception {
		for(int i=0;i<diceList.size();i++)
			processor.sendDice(i);
	}
	
	public MainPanel getMainPanel() {
		return mainPanel;
	}
	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public DicePanel getDicePanel() {
		return dicePanel;
	}
	public void setDicePanel(DicePanel dicePanel) {
		this.dicePanel = dicePanel;
	}
	public ArrayList<PlayerPanel> getPlayerPanels() {
		return playerPanels;
	}
	public void setPlayerPanels(ArrayList<PlayerPanel> playerPanels) {
		this.playerPanels = playerPanels;
	}

	public PlayPausePanel getPlayPausePanel() {
		return playPausePanel;
	}
	public void setPlayPausePanel(PlayPausePanel playPausePanel) {
		this.playPausePanel = playPausePanel;
	}

	public OptionPanel getOptionPanel() {
		return optionPanel;
	}
	public void setOptionPanel(OptionPanel optionPanel) {
		this.optionPanel = optionPanel;
	}

	public InstructionPanel getInstructionPanel() {
		return instructionPanel;
	}
	public void setInstructionPanel(InstructionPanel instructionPanel) {
		this.instructionPanel = instructionPanel;
	}

	public DiceProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(DiceProcessor processor) {
		this.processor = processor;
	}
	
	public DiceServer getServer() {
		return server;
	}
	public void setServer(DiceServer server) {
		this.server = server;
		server.setController(this);
	}

	public boolean isConnect() {
		return isConnect;
	}
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public void sendFinish(String text) {
		try {
			processor.send("Send-Finish-"+text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
