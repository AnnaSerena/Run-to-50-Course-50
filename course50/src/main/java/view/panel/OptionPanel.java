package view.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import controller.GuiController;
import utils.GuiConstant;

public class OptionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GuiController controller;
	private JRadioButton firstRadio;
	private String host = "";
	private String port = "";
	
	public OptionPanel(GuiController controller) {
		this.controller = controller;
		this.controller.setOptionPanel(this);
		
		this.setLayout(new GridLayout(1,2));
		this.add(createChoicePanel());
		this.add(createConnectionPanel());
	}

	private JPanel createChoicePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel labelDice = new JLabel("Dés ");
		labelDice.setFont(GuiConstant.FONT);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBorder(GuiConstant.BORDER);
		labelPanel.add(labelDice);

		JPanel  center = new JPanel();
		center.setLayout(new GridLayout(1,4));
		center.setBorder(GuiConstant.BORDER);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		for(int i=2;i<6;i++) {
			JRadioButton radioButton = new JRadioButton(""+i);
			
			radioButton.addActionListener(e ->{
				JRadioButton r = (JRadioButton)e.getSource();
				int diceNumber = Integer.parseInt(r.getText());
				controller.updateDicesToDicePanel(diceNumber);
			});
			
			if(i==2) firstRadio = radioButton;
			
			buttonGroup.add(radioButton);
			center.add(radioButton);
		}
		
		panel.add(labelPanel, BorderLayout.WEST);
		panel.add(center, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createConnectionPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel connectPanel = new JPanel();
		connectPanel.setLayout(new GridLayout(1, 2));
		
		int i=0;
		
		JTextPane[] textPanes = new JTextPane[2];
		
		for(String key : new String[] {"Host", "Port"}) {
			JLabel label = new JLabel(key+" ");
			label.setFont(GuiConstant.FONT);
			
			JTextPane textPane = new JTextPane();
			textPanes[i++] = textPane;
			textPanes[0].setText("localhost");
			host = "localhost";
			port = "1";
			
			textPane.addCaretListener(e->{
				if(e.getSource() == textPanes[0]) {
					host = ((JTextPane)e.getSource()).getText();
				}else {
					port = ((JTextPane)e.getSource()).getText();
				}
			});
			
			label.setBorder(GuiConstant.BORDER);
			textPane.setBorder(GuiConstant.BORDER);
			
			JPanel subPanel = new JPanel();
			subPanel.setLayout(new BorderLayout());
			subPanel.add(label, BorderLayout.WEST);
			subPanel.add(textPane, BorderLayout.CENTER);
			
			connectPanel.add(subPanel);
		}

		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(e->{
			if(host.isBlank() || port.isBlank()) {
				JOptionPane.showMessageDialog(controller.getDicePanel(), "Le host ou le port ne sont pas renseigné", 
						"Connection Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					int portInt = Integer.parseInt(port);
					Thread th = new Thread(new Runnable() {
						boolean isRunning = true;
						public void run() {
							while(isRunning) {
								try {
									controller.connect(host, portInt);
									isRunning = !controller.getProcessor().getSocket().isConnected();
								} catch (Exception e) {
									isRunning = false;
									e.printStackTrace();
								}
							}
							JOptionPane.showMessageDialog(controller.getDicePanel(), 
									"Vous êtes bien connecté à "+controller.getProcessor());
						}
					});
					th.start();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(controller.getDicePanel(), "Le port doit être un entier compris "
							+ "entre 1 et 65000", 
							"Connection Error", JOptionPane.ERROR_MESSAGE);
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex,"Connection Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JPanel boutonPanel = new JPanel();
		boutonPanel.setLayout(new BorderLayout());
		boutonPanel.setBorder(GuiConstant.BORDER);
		boutonPanel.add(connectButton, BorderLayout.CENTER);
		
		panel.add(connectPanel, BorderLayout.CENTER);
		panel.add(boutonPanel, BorderLayout.EAST);
		return panel;
	}

	public void init() {
		firstRadio.isSelected();
		firstRadio.doClick();
	}
	
	public class OptionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println(e);
		}
	}
}
