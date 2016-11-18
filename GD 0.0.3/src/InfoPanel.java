import java.awt.Color;
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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

// Contains:
//   Instruction Text Area
//   Player Text Area


public class InfoPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private ClientController control; 
	private Player player;
	
	private String instructionText = "Welcome to Game Demo 0.0.3! This is where you will find information about your current turn and available actions.";
	private String playerText;// = "Hello " + control.getPlayer().getName() + ", you can find your stats here.";
	private char [] chars;
	
	private JTextArea instructionArea;
	private JTextArea playerArea;
	private OurButton b0;
	private OurButton b1;
	
	private int selectPhase = 0;
	
	int B0 = 0;
	int B1 = 1;
	
	public InfoPanel(ClientController clientController) {
		control = clientController;
		playerText = "Hello " + control.getPlayer().getName() + ", you can find your stats here.";
		this.setPreferredSize(new Dimension (300,1000));
		this.setOpaque(false);
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
		instructionArea.setForeground(Globals.textColor);
		instructionArea.setEditable(false);
		instructionArea.setOpaque(false);
		//instructionArea.setBackground(Globals.backgroundColor);
		//instructionArea.setForeground(Globals.textColor);
		JScrollPane instructionScrollPane = new JScrollPane(instructionArea);
		instructionScrollPane.setOpaque(false);
		instructionScrollPane.getViewport().setOpaque(false);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0; c.weighty = 1.0;
		c.gridheight = 1; c.gridwidth = 2;
		this.add(instructionScrollPane, c);
		
		
		// BUTTON 1
		b0 = new OurButton(this, B0, KeyStroke.getKeyStroke('a'));
		b0.setText("Test1");
		b0.setPreferredSize(new Dimension(b0.getWidth() + 1, b0.getHeight() + 1));
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 1;
		c.gridheight = 1; c.gridwidth = 1; 
		c.weightx = 1.0; c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(b0, c);
		
		// BUTTON 2
		b1 = new OurButton(this, B1, KeyStroke.getKeyStroke('s'));
		b1.setText("Test2");
		b1.setPreferredSize(new Dimension(b1.getWidth() + 1, b1.getHeight() + 1));
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1; c.gridy = 1;
		c.gridheight = 1; c.gridwidth = 1; 
		c.weightx = 1.0; c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;this.add(b1, c);
		
		// PANEL 2
		playerArea = new JTextArea();
		playerArea.setPreferredSize(new Dimension(100, 300));
		playerArea.setFont(Globals.f);
		playerArea.setLineWrap(true);
		playerArea.setWrapStyleWord(true);
		printToPlayerArea(playerText);
		playerArea.setEditable(false);
		//playerArea.setBackground(Globals.backgroundColor);
		playerArea.setForeground(Globals.textColor);
		playerArea.setOpaque(false);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 2;
		c.gridheight = 1; c.gridwidth = 2;
		c.weightx = 1.0; c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		this.add(playerArea, c);
		
		// key bindings
		b0.getInputMap(Globals.IFW).put(b0.getKey(), "keyB0");
		Action keyB0 = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed Test 1");
				b0.action();
			}};
		b0.getActionMap().put("keyB0", keyB0);
		
		b1.getInputMap(Globals.IFW).put(b1.getKey(), "keyB1");
		Action keyB1 = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed Test 2");
				b1.action();
			}};
		b1.getActionMap().put("keyB1", keyB1);
		
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	public void printToInstructionArea(String s) {
		//instructionArea.setText(padRight(s, 280));
		instructionArea.setText("Gameplay:\n\n" + s);
	}
	
	public void printToPlayerArea(String s){
		//playerArea.setText(padRight(s, 280));
		playerArea.setText("Player Information:\n\n" + s);
	}
	
	
	public void clicked(int command) {
		if (command == B0) {

			}
		else if (command == B1) {
			//printToInstructionArea("I guess you're done.");
			if (selectPhase == 0) {
				selectPhase++;
				control.infoPanel2.getSelectPanel().mainPhase();
			}
			else if (selectPhase == 1){
				selectPhase++;
				control.infoPanel2.getSelectPanel().spaceshipPhase();
			}
			else {
				selectPhase = 0;
				control.infoPanel2.getSelectPanel().claimPhase();
			}
		}
	}
	
}
