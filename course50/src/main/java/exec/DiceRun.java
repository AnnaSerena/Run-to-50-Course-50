package exec;

import javax.swing.JOptionPane;

import controller.GuiController;
import model.DiceServer;
import view.frame.MainFrame;
import view.panel.DicePanel;
import view.panel.InstructionPanel;
import view.panel.MainPanel;
import view.panel.OptionPanel;
import view.panel.PlayPausePanel;
import view.panel.PlayerPanel;

public class DiceRun {
	public static int count = 1;
	
	public static void main(String[] args) throws Exception {
		String resourceFolder = args.length == 0 ? null : args[0];
		
		GuiController controller = new GuiController(resourceFolder); //Initialisation du controller
		
		boolean connect = true;
		
		int i=0;
		
		while(connect && i<2) {
//			String portString = ""+(i+1);
			String portString = JOptionPane.showInputDialog(null, "Initialisation Serveur Tentative "+(i+1), 
					"Port sur lequel démarrer le serveur", 
					JOptionPane.QUESTION_MESSAGE);
					
			portString = portString == null ? "" : portString;
			
			if(portString.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Le port n'est pas renseigné", "Connection Error", 
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					int port = Integer.parseInt(portString);
					DiceServer server = new DiceServer(port);
					controller.setServer(server);
					connect = false;
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Le port doit être un entier compris "
							+ "entre 1 et 65000", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}catch(Exception ex) {
					count++;
					JOptionPane.showMessageDialog(null, ex,"Connection Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			i++;
		}
		
		//Si on n'a pas pu se connecter le jeu ne démarre pas
		if(controller.getServer() == null)
			System.exit(0);
				
		OptionPanel optionPanel = new OptionPanel(controller); //Vu des options
		new PlayerPanel(controller); //Vu joueur 1
		new PlayerPanel(controller); //Vu joueur 2
		new DicePanel(controller); //Vu des dés
		new PlayPausePanel(controller); //Vu des conmmades de jeu
		new InstructionPanel(controller); //Panneau de consigne
		
		//Initialisation du MainPanel
		MainPanel mainPanel = new MainPanel(controller);
				
		//Initialisation de la fenetre principale
		MainFrame mainFrame = new MainFrame();
		mainFrame.setServerName(""+controller.getServer());
		mainFrame.setFolder(controller.getFolder()); 
		mainFrame.setPanel(mainPanel);
		mainFrame.setController(controller);
		mainFrame.init(); //Start du program
		
		optionPanel.init();
		
//		PlayPausePanel.getStart().doClick();
		
		System.out.println("\n\n\n");
	}
}
