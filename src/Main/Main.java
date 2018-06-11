//Muruhathasan
package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main implements ActionListener{
	
	JFrame frame;
	GamePanel panel;
	int width = 2080;
	int height = 1300;
	Timer t;
	int repeat = 15;
	
	public static void main(String args[]) {
		Main main = new Main();
		main.Jsetup();
	}
	
	private void Jsetup() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocation(0,0);
		frame.setVisible(true);
		panel.start();
		t.start();
	}
	
	public Main() {
		t = new Timer(repeat, this);
		frame = new JFrame();
		panel= new GamePanel(width,height);
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.addMouseListener(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panel.updateInfo(frame.getWidth(), frame.getHeight(), frame.getX(), frame.getY());
	}
}
