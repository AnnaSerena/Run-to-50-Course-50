package utils;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GuiComponent {

	public static JButton createIcoButton(String folder, String ico) throws Exception {
		JButton button = new JButton(createIcon(folder, ico));
		button.setHideActionText(true);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		
		return button;
	}
	
	public static ImageIcon createIcon(String folder, String ico) throws Exception {
		Image start = folder == null ?
				ImageIO.read(GuiComponent.class.getResourceAsStream("/resources/"+ico)) :
				ImageIO.read(new File(folder+ico));
				
		return new ImageIcon(start);
	}
	
}
