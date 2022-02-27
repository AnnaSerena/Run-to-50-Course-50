package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.GuiController;
import view.panel.PlayPausePanel;
import view.panel.PlayerPanel;

public class DiceProcessor implements Runnable{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private GuiController controller;
	private String action = "";
	
	boolean isRunning = true;
	
	public DiceProcessor() {
		this(new Socket());
	}
	
	public DiceProcessor(Socket socket) {
		this.socket = socket;
	}

	public void connect(String host, int port) throws Exception {
		socket = new Socket();
		socket.connect(new InetSocketAddress(host, port), 30);
	}
	
	public void send(Object toSend) throws Exception {
		System.err.println("Envoi de l'objet : "+toSend);
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(toSend);
		oos.flush();
	}
	
	public void get() throws IOException {
		int i=0;
		while(!socket.isClosed()) {
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				Object object = ois.readObject();
				
				if(object instanceof String) {
					action = object.toString();
					
//					System.out.println("Exécution de l'action "+object);
					
					//Recupération des éléments player
					if(object.toString().startsWith("GET-Player-")) {
						int position = Integer.parseInt(((String) object).split("-")[2]);
						controller.current = controller.getPlayerPanels().get(position).getPlayer().getName();
						sendPlayer(position);
					}
					if(object.toString().startsWith("SEND-Start")) {
						controller.startGame();
						PlayPausePanel.getStop().setEnabled(true);
						PlayPausePanel.getStart().setEnabled(false);
					}
					if(object.toString().startsWith("SEND-Stop")) {
						controller.stopGame();
						PlayPausePanel.getStop().setEnabled(false);
						PlayPausePanel.getStart().setEnabled(true);
					}
					if(object.toString().startsWith("Send-Finish-")) {
						String text = object.toString().split("-")[2];
						JOptionPane.showMessageDialog(controller.getDicePanel(), text);
						PlayPausePanel.getStop().doClick();
					}
					if(object.toString().startsWith("SEND-Disable-Dice-Panel")) {
						controller.disableDicePanel(false);
					}
					if(object.toString().startsWith("SEND-Enable-Dice-Panel")) {
						controller.disableDicePanel(true);
					}
				} 
				if (object instanceof Player) {
					System.out.println("Recupération du player "+object);
					int position = Integer.parseInt(action.split("-")[2]);
					controller.getPlayerPanels().get(position).setPlayer((Player)object);
					for(PlayerPanel playerPanel : controller.getPlayerPanels())
						playerPanel.updatePanel();
				}
				if (object instanceof Dice) {
					System.out.println("Recupération du Dice "+object);
					int position = Integer.parseInt(action.split("-")[2]);
					controller.getDicePanel().getDiceList().get(position)
					.setValue(((Dice)object).getValue());
					controller.getDicePanel().repaint();
				}
				if(object instanceof ArrayList<?>) {
					@SuppressWarnings("unchecked")
					ArrayList<Dice> diceList = (ArrayList<Dice>)object;
					
					controller.getDicePanel().drawDice(diceList);
					controller.getDicePanel().repaint();
				}
			
			}catch(Exception e) {
				if(i==1)
					socket.close();
				e.printStackTrace();
				i++;
				
			}
		}
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void open() {
		Thread t = new Thread(this, this.getClass().getSimpleName());
		t.start();
	}
	
	public void close() {
		isRunning = false;
	}
	
	public void run() {
		try {
			while(isRunning) {
				get();
			}
			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public GuiController getController() {
		return controller;
	}
	public void setController(GuiController controller) {
		this.controller = controller;
	}
	
	public String toString() {
		return ""+socket.getRemoteSocketAddress();
	}

	public void getPlayer(int position) throws Exception {
		send("GET-Player-"+position);
	}

	public void sendPlayer(int position) throws Exception {
		Player player = controller.getPlayerPanels().get(position).getPlayer();
		send("SEND-Player-"+position);
		send(player);
	}

	public void sendDice(int position) throws Exception {
		Dice dice = controller.getDicePanel().getDiceList().get(position);
		send("SEND-Dice-"+position);
		send(dice);
	}

	public void sendStart() throws Exception {
		send("SEND-Start");
	}
	
	public void sendStop() throws Exception {
		send("SEND-Stop");
	}
	public void sendDisableDicePanel() throws Exception {
		send("SEND-Disable-Dice-Panel");
	}
	public void sendEnableDicePanel() throws Exception {
		send("SEND-Enable-Dice-Panel");
	}

	public void updateDicesToDiceServer(ArrayList<Dice> diceList) throws Exception {
		send(diceList);
	}
}
