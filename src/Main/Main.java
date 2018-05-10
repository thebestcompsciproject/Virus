//Muruhathasan
package Main;

import javax.swing.JFrame;

public class Main {
	
	JFrame frame;
	GamePanel panel;
	final static int width = 1000;
	final static int height = 800;
	
	public static void main(String args[]) {
		Main main = new Main();
		main.Jsetup();
	}
	
	void Jsetup() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocation(0,0);
		frame.setVisible(true);
		panel.start();
	}
	
	public Main() {
		frame = new JFrame();
		panel= new GamePanel();
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.addMouseListener(panel);
	}
}
