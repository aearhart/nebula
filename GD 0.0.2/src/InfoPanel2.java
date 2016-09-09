import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

// Contains:
//   Hover Text Area


public class InfoPanel2 extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientController control; 
	private Player player;
	
	private String string1;
	private String string2;
	private char [] chars;
	
	private JTextArea hoverArea;
	
	public InfoPanel2(ClientController clientController) {
		control = clientController;

		this.setPreferredSize(new Dimension (300,1000));
		
		this.setLayout(new GridLayout(2, 1, 0, 5));

		string1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut ";
		
		hoverArea = new JTextArea();

		Font f1 = new Font("Arial", Font.PLAIN, 35);
		
		hoverArea.setFont(f1);
		
		hoverArea.setColumns(10);
		hoverArea.setLineWrap(true);
		hoverArea.setRows(1);
		hoverArea.setWrapStyleWord(true);
		hoverArea.setText(string1);
		
		this.add(hoverArea);
		
		
		
		
	}
	
	public void printToHoverArea(String s) {
		hoverArea.setText(s);
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
