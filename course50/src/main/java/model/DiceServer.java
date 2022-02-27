package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import controller.GuiController;

public class DiceServer implements Runnable{
	ServerSocket server;
	DiceProcessor processor;
	GuiController controller;

	boolean isRunning = true;
	int i=1;

	public DiceServer(int port) throws IOException {
		processor = new DiceProcessor();
		server = new ServerSocket(port,10);
		this.open();
	}
	
	public DiceServer(String host, int port) throws IOException {
		processor = new DiceProcessor();
		server = new ServerSocket(port, 10, InetAddress.getByName(host));
		this.open();
	}

	public void close() {
		isRunning = false;
	}
	
	public void run() {
		try {
			while(isRunning) {
				Socket client = server.accept();
				InetSocketAddress rsaddress = (InetSocketAddress) client.getRemoteSocketAddress();
		        
				JOptionPane.showMessageDialog(controller.getDicePanel(), "Client N°"+(i++)+
						" connected with id:"+client.hashCode()+ ", Hostname:"+
						rsaddress.getHostName()+":"+rsaddress.getPort());
				
				processor = new DiceProcessor(client);
				processor.setController(controller);
				controller.setConnect(true);
				controller.setProcessor(processor);
				Thread t = new Thread(processor);
				t.start();
			}
			server.close();
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

	public void open() {
		Thread t = new Thread(this, this.getClass().getSimpleName());
		t.start();
	}
	
	public String toString(){
		InetSocketAddress address = (InetSocketAddress)server.getLocalSocketAddress();
		return ""+address.getHostName()+"\n"+address.getHostString()+"\n"+address.getPort();
	}
}
