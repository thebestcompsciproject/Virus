package Main;

import javax.swing.ImageIcon;

public class Button {

	private double x;
	private double y;
	private double width;
	private double height;
	private String buttonName1;
	private String buttonName2;
	
	private ImageIcon upButton;
	private ImageIcon downButton;
	

	public Button(String file1, String file2) {
		buttonName1 = file1 + ".png";
		buttonName2 = file2 + ".png";	
		upButton = new ImageIcon(buttonName1);
		downButton = new ImageIcon(buttonName2);
	}
	
	
	/*
	 * Check if the cursor is in the area of the button
	 * 
	 * 	**INCOMPLETE**
	 */
	public boolean cursorContains(double x, double y)
	{
		return true;
	}
	
	
	/*
	 * Constructs the button in the JPanel
	 * 
	 * 	**INCOMPLETE**
	 */
	public void constructButton() {
		
	}
	
	/*
	 * Switches the buttons
	 * 
	 * 	**INCOMPLETE**
	 */
	public void switchButton() {
		
	}
	
}
