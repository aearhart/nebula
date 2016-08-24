import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

// Instructions
// Informational
// Player Information


public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller control;
	private Player player;
	
	private String string1;
	private String string2;
	private char [] chars;
	
	private JTextArea textArea;
	
	public InfoPanel(Controller ctrl) {
		control = ctrl;

		this.setPreferredSize(new Dimension (300,1000));
		

		string1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut ";
		
		textArea = new JTextArea();

		Font f1 = new Font("Arial", Font.PLAIN, 35);
		
		textArea.setFont(f1);
		
		textArea.setColumns(10);
		textArea.setLineWrap(true);
		textArea.setRows(1);
		textArea.setWrapStyleWord(true);
		textArea.setText(string1);
		
		this.add(textArea);
		
		
		
	}
	
	public void printToInfoPanel(String s) {
		textArea.setText(s);
	}
	
	/*
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Font f1 = new Font("Arial", Font.PLAIN, 10);
		g.setFont(f1);
		
		/*FontMetrics fm = g.getFontMetrics();

		int msgWidth = fm.stringWidth(string1);
		int msgAscent = fm.getAscent();

		int msgX = getWidth() / 2 - msgWidth / 2;
		int msgY = getHeight() / 2 + msgAscent / 2;
		g.drawString(string1, msgX, msgY);*/
		
		//g.drawString(string1, 20, 55);
		/*
		
	}*/

	
}
