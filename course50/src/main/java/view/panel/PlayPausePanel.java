package view.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.GuiController;
import utils.GuiComponent;
import utils.GuiConstant;

public class PlayPausePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String[] actionCommands = {"start", "stop", "skip", "quit"};
	private static JButton start, stop, skip, quit;
	
	public PlayPausePanel(GuiController controller) throws Exception {
		controller.setPlayPausePanel(this);
		
		start = GuiComponent.createIcoButton(controller.getFolder(), GuiConstant.STARTICON);
		stop = GuiComponent.createIcoButton(controller.getFolder(), GuiConstant.STOPICON);
		skip = GuiComponent.createIcoButton(controller.getFolder(), GuiConstant.SKIPICON);
		quit = GuiComponent.createIcoButton(controller.getFolder(), GuiConstant.QUITICON);
		
		int i=0;
		
		this.setLayout(new GridLayout(1,4));
		
		for(JButton button : new JButton[] {start, stop, skip, quit}) {
			button.setActionCommand(actionCommands[i]);
			button.setText(actionCommands[i++].toUpperCase());
			button.setFont(GuiConstant.FONT);
			button.addActionListener(e ->{
				JButton src = (JButton)e.getSource();
				
				stop.setEnabled(src == start);
				start.setEnabled(src == stop);
				
				if(src == start) {
					controller.startGame();
					if(controller.isConnect())
						try {
							controller.getProcessor().sendStart();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}else if(src == stop) {
					controller.stopGame();
					if(controller.isConnect())
						try {
							controller.getProcessor().sendStop();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			});
			JPanel panel = new JPanel();
			
			panel.setLayout(new BorderLayout());
			panel.add(button, BorderLayout.CENTER);
			panel.setBorder(GuiConstant.BORDER);
			panel.setBackground(GuiConstant.skinColor2);
			
			this.add(panel);
		}
		
		stop.setEnabled(false);
		skip.setEnabled(false);
		quit.setEnabled(false);
	}

	public static JButton getStart() {
		return start;
	}
	public static void setStart(JButton start) {
		PlayPausePanel.start = start;
	}
	
	public static JButton getStop() {
		return stop;
	}
	public static void setStop(JButton stop) {
		PlayPausePanel.stop = stop;
	}
	
	public static JButton getSkip() {
		return skip;
	}
	public static void setSkip(JButton skip) {
		PlayPausePanel.skip = skip;
	}

	public static JButton getQuit() {
		return quit;
	}
	public static void setQuit(JButton quit) {
		PlayPausePanel.quit = quit;
	}
	
	
}
