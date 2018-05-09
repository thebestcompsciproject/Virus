package Main;

import javax.swing.JFrame;

public class Main {
	
	private JFrame frame;
	private GamePanel g;
	private final static int width;
	private final static int height;
	
	public Main() {
		frame = new JFrame();
		g = new GamePanel();
		frame.add(g);
		frame.addKeyListener(g);
		frame.addMouseListener(g);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setup();
	}
	
	private void setup() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocation(0,0);
		frame.setVisible(true);
		g.start(); //starts the timer
	}
	
}


