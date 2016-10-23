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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Contains:
//   Instruction Text Area
//   Player Text Area


public class InfoPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private ClientController control; 
	private Player player;
	
	private String instructionText = "Welcome to Game Demo 0.0.3! This is where you will find information about your current turn and available actions.";
	private String playerText;// = "Hello " + control.getPlayer().getName() + ", you can find your stats here.";
	private char [] chars;
	
	private JTextArea instructionArea;
	private JTextArea playerArea;
	
	public InfoPanel(ClientController clientController) {
		control = clientController;
		playerText = "Hello " + control.getPlayer().getName() + ", you can find your stats here.";
		this.setPreferredSize(new Dimension (300,1000));
		
		//this.setLayout(new GridLayout(3, 1, 0, 5));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		// PANEL 1
		instructionArea = new JTextArea();
		instructionArea.setPreferredSize(new Dimension(100, 300));
		instructionArea.setFont(Globals.f);
		instructionArea.setLineWrap(true);
		instructionArea.setWrapStyleWord(true);
		printToInstructionArea(instructionText);
		instructionArea.setEditable(false);
		instructionArea.setBackground(Globals.backgroundColor);
		instructionArea.setForeground(Globals.textColor);
		JScrollPane instructionScrollPane = new JScrollPane(instructionArea);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0; c.weighty = 1.0;
		c.gridheight = 1; c.gridwidth = 2;
		this.add(instructionScrollPane, c);
		
		
		// BUTTON 1
		JButton b1 = new JButton("?");
		b1.setMnemonic('h');
		b1.setActionCommand("help");
		b1.setFont(Globals.f);
		b1.setForeground(Globals.textColor);
		b1.setBackground(Globals.backgroundColor);
		b1.addActionListener(this);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 1;
		c.gridheight = 1; c.gridwidth = 1; 
		c.weightx = 1.0; c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(b1, c);
		
		// BUTTON 2
		JButton b2 = new JButton("Done");
		b2.setMnemonic('d');
		b2.setActionCommand("done");
		b2.setFont(Globals.f);
		b2.setForeground(Globals.textColor);
		b2.setBackground(Globals.backgroundColor);
		b2.addActionListener(this);

		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1; c.gridy = 1;
		c.gridheight = 1; c.gridwidth = 1; 
		c.weightx = 1.0; c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;this.add(b2, c);
		
		// PANEL 2
		playerArea = new JTextArea();
		playerArea.setPreferredSize(new Dimension(100, 300));
		playerArea.setFont(Globals.f);
		playerArea.setLineWrap(true);
		playerArea.setWrapStyleWord(true);
		printToPlayerArea(playerText);
		playerArea.setEditable(false);
		playerArea.setBackground(Globals.backgroundColor);
		playerArea.setForeground(Globals.textColor);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 2;
		c.gridheight = 1; c.gridwidth = 2;
		c.weightx = 1.0; c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		this.add(playerArea, c);
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	public void printToInstructionArea(String s) {
		//instructionArea.setText(padRight(s, 280));
		instructionArea.setText(s);
	}
	
	public void printToPlayerArea(String s){
		//playerArea.setText(padRight(s, 280));
		playerArea.setText(s);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("help".equals(e.getActionCommand())) {
			printToInstructionArea("Help coming your way?");
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
