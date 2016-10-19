import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// Contains:
//   Instruction Text Area
//   Player Text Area


public class InfoPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private ClientController control; 
	private Player player;
	
	private String instructionText = "Welcome to Game Demo 0.0.3! \nPlease wait while we are setting up the game.";
	private String playerText;// = "Hello " + control.getPlayer().getName() + ", you can find your stats here.";
	private char [] chars;
	
	private JTextArea instructionArea;
	private JTextArea playerArea;
	
	public InfoPanel(ClientController clientController) {
		control = clientController;
		playerText = "Hello "; //+ control.getPlayer().getName() + ", you can find your stats here.";
		this.setPreferredSize(new Dimension (300,1000));
		
		//this.setLayout(new GridLayout(3, 1, 0, 5));
		this.setLayout(new GridBagLayout());

		// BUTTON 1
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		c.gridx = 0; c.gridy = 0;
		//c.gridwidth =1; c.gridheight =1;
		c.ipadx = 5; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		// testing with buttons
		JButton b1 = new JButton("?");
		b1.setMnemonic('h');
		b1.setActionCommand("help");
		b1.addActionListener(this);
		this.add(b1, c);
		
		// PANEL 1
		c.gridx = 0; c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		instructionArea = new JTextArea(20, 3);
		playerArea = new JTextArea(20, 3);
		instructionArea.setPreferredSize(new Dimension(100, 300));
		playerArea.setPreferredSize(new Dimension(100, 200));
		//playerArea.setColumns(40);
		Font f1 = new Font("Consolas", Font.PLAIN, 22);
		
		instructionArea.setFont(f1);
		
		//instructionArea.setColumns(10);
		//instructionArea.setRows(1);
		instructionArea.setLineWrap(true);
		instructionArea.setWrapStyleWord(true);
		instructionArea.setText(instructionText);
		instructionArea.setEditable(false);
		this.add(instructionArea, c);
		
		
		// BUTTON 2
		c.gridx = 0; c.gridy = 2;
		//c.gridwidth =1; c.gridheight =1;
		c.ipadx = 5; c.ipady = 10;
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		// testing with buttons
		JButton b2 = new JButton("Done");
		b2.setMnemonic('d');
		b2.setActionCommand("done");
		b2.addActionListener(this);
		this.add(b2, c);
		
		// PANEL 2
		c.gridx = 0; c.gridy = 3;
		//c.ipady = 10;		
		c.ipadx = 5; c.ipady = 100;
		c.insets = new Insets(5, 5, 5, 5);
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		playerArea.setFont(f1);

		//playerArea.setRows(1);
		//playerArea.setColumns(10);
		playerArea.setLineWrap(true);
		playerArea.setWrapStyleWord(true);
		playerArea.setText(playerText);
		playerArea.setEditable(false);
		
		this.add(playerArea, c);
		
		
		
		
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	public void printToInstructionArea(String s) {
		instructionArea.setText(padRight(s, 280));
	}
	
	public void printToPlayerArea(String s){
		playerArea.setText(padRight(s, 280));
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("help".equals(e.getActionCommand())) {
			printToInstructionArea("Help coming your way?/n whatever");
		}
		else if ("done".equals(e.getActionCommand())) {
			printToInstructionArea("I guess you're done.");
		}
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
