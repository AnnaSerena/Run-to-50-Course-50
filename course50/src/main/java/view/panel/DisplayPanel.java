package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import utils.MessageConsole;

public class DisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextPane textPane = new JTextPane();
	
	
	public DisplayPanel() {
		//Definition du scroll Pane pour la log
		JScrollPane scroll = new JScrollPane(textPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		
		init();
	}
	
	public void init(){
		MessageConsole console = new MessageConsole(textPane);
		console.setMessageLines(15000);
        console.redirectOut();
        console.redirectErr(Color.RED, null);
	}
}
