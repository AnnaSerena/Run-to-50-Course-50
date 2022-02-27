package view.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.GuiController;
import utils.GuiComponent;
import utils.GuiConstant;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final int width = 1200, height = 900;
	
	private JPanel panel;
	private String folder;
	private String serverName;
	private GuiController controller;
	
	public void init() throws Exception {
		this.setTitle(GuiConstant.TITLE+" Adresse Serveur ["+serverName+"]");
		this.setSize(width, height);
		this.setIconImage(GuiComponent.createIcon(folder, GuiConstant.ICON).getImage());
		this.setResizable(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
	}

	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	public static void main(String[] args) throws Exception {
		MainFrame fenetre = new MainFrame();
		fenetre.setFolder("src/main/resources/");
		fenetre.setPanel(new JPanel());
		fenetre.init();
		
		String player1Name = JOptionPane.showInputDialog(null, "Joueur 1", "Veuillez saisir le nom", 
				JOptionPane.QUESTION_MESSAGE);
		
		String player2Name = JOptionPane.showInputDialog(null, "Joueur 2", "Veuillez saisir le nom", 
				JOptionPane.QUESTION_MESSAGE);
		
		System.err.println(player1Name+"\t"+player2Name);
	}

	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public GuiController getController() {
		return controller;
	}
	public void setController(GuiController controller) {
		this.controller = controller;
	}
}
