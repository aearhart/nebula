import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

// Contains:
//   Instruction Text Area
//   Player Text Area


public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientController control; 
	private Player player;
	
	private String string1;
	private String string2;
	private char [] chars;
	
	private JTextArea instructionArea;
	private JTextArea playerArea;
	
	public InfoPanel(ClientController clientController) {
		control = clientController;

		this.setPreferredSize(new Dimension (300,1000));
		
		this.setLayout(new GridLayout(2, 1, 0, 5));

		string1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut ";
		
		instructionArea = new JTextArea();
		playerArea = new JTextArea();

		Font f1 = new Font("Arial", Font.PLAIN, 35);
		
		instructionArea.setFont(f1);
		
		instructionArea.setColumns(10);
		instructionArea.setLineWrap(true);
		instructionArea.setRows(1);
		instructionArea.setWrapStyleWord(true);
		instructionArea.setText(string1);
		
		this.add(instructionArea);
		
		playerArea.setFont(f1);
		
		playerArea.setColumns(10);
		playerArea.setLineWrap(true);
		playerArea.setRows(1);
		playerArea.setWrapStyleWord(true);
		playerArea.setText(string1);
		
		this.add(playerArea);
		
		
		
		
	}
	
	public void printToInstructionArea(String s) {
		instructionArea.setText(s);
	}
	
	public void printToPlayerArea(String s){
		playerArea.setText(s);
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
