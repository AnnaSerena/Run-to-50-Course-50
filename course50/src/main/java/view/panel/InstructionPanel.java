package view.panel;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import controller.GuiController;
import utils.GuiConstant;

public class InstructionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static String htmlString = "<h1 style=\"text-align: justify;\"><span style=\"color: #7f0055;\"><strong><span style=\"text-decoration: underline;\">Notice d'utilisation</span> </strong></span></h1>\r\n" + 
			"<p style=\"text-align: justify;\"><span style=\"color: #000000;\">Bienvenue sur la course &agrave; 50. Les r&egrave;gles sont simples :</span></p>\r\n" + 
			"<ol TYPE=I style=\"list-style-type: upper-roman; text-align: justify;\">\r\n" + 
			"	<li><span style=\"color: #ff6600;\">Lancement du jeu</span></li>\r\n" + 
			"	<ol style=\"text-align: justify;\">\r\n" + 
			"		<li><span style=\"color: #000000;\">Veuillez renseignez votre nom et le nom de l'adversaire</span></li>\r\n" + 
			"		<li><span style=\"color: #000000;\">Une fois arriv&eacute; sur la page de jeu, utiliser le bouton play pour le lancer</span></li>\r\n" + 
			"		<li><span style=\"color: #000000;\">En cours de jeu vous pourrez utiliser le bouton stop pour arr&ecirc;ter la partie</span></li>\r\n" + 
			"	</ol>\r\n" + 
			"</ol>\r\n" + 
			"<ol TYPE=I START=2 style=\"list-style-type: upper-roman; text-align: justify;\">\r\n" + 
			"	<li>&nbsp;<span style=\"color: #ff6600;\">Comment jouer<br /></span></li>\r\n" + 
			"	<ol style=\"text-align: justify;\">\r\n" + 
			"		<li><span style=\"color: #000000;\">Utilisez le double-clique du bouton gauche de la souris pour lancer les d&eacute;s<br /></span></li>\r\n" + 
			"		<li><span style=\"color: #000000;\">Utilisez le double-clique du bouton droit de la souris pour laisser la main &agrave; l'adversaire<br /></span></li>\r\n" + 
			"		<li><span style=\"color: #000000;\">Cliquez n'importe quel autre bouton pour arr&ecirc;ter le roll<br /></span></li>\r\n" + 
			"	</ol>\r\n" + 
			"</ol>\r\n" + 
			"\r\n" + 
			"<ol TYPE=I START=3 style=\"list-style-type: upper-roman; text-align: justify;\">\r\n" + 
			"	<li>&nbsp;<span style=\"color: #ff6600;\">Quelles sont les r&egrave;gles pour gagner<br /></span></li>\r\n" + 
			"	<span style=\"color: #000000;\">A chaque lanc&eacute;, si vous obtenez: <br /></span>\r\n" + 
			"	<ol style=\"text-align: justify;\">\r\n" + 
			"		<li><span style=\"color: #000000;\">Un 1, votre score total est remis &agrave; 0</span></li>\r\n" + 
			"		<li><span style=\"color: #000000;\">Deux 1, vous perdez et le jeu s'arrête</span></li>\r\n" + 
			"		<li><span style=\"color: #000000;\">Tout autre nombre, la somme est ajout&eacute;e &agrave; votre total</span></li>\r\n" + 
			"	</ol>\r\n" + 
			"	<p style=\"padding-left: 0px; text-align: justify;\"><span style=\"color: #000000;\">Celui qui le premier atteind le score de 50 est le vainqueur et la partie s'arr&ecirc;te</span></p>\r\n" + 
			"</ol>";
	
	public InstructionPanel(GuiController controller) {
		JEditorPane jEditorPane = new JEditorPane();
		HTMLEditorKit kit = new HTMLEditorKit();
		
		jEditorPane.setEditable(false);
        jEditorPane.setEditorKit(kit);
        
        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
        styleSheet.addRule("h1 {color: blue;}");
        styleSheet.addRule("h2 {color: #ff0000;}");
        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
        
        Document doc = kit.createDefaultDocument();
        jEditorPane.setDocument(doc);
        
        jEditorPane.setText(htmlString);
		
		
		this.setBorder(GuiConstant.BORDER);
		controller.setInstructionPanel(this);
		
		this.setLayout(new BorderLayout());
		this.add(jEditorPane, BorderLayout.CENTER);
	}
	
}
