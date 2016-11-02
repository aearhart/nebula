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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// Contains:
//   Hover Text Area


public class InfoPanel2 extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private ClientController control; 
	private Player player;
	
	private String hoverText = "Hover over a satellite for more info.";
	private char [] chars;
	
	private JTextArea hoverArea;
	public ChatBox chatBox;
	public Satellite selectedSatellite = null;
	
	public InfoPanel2(ClientController clientController) {
		control = clientController;
		
		this.setPreferredSize(new Dimension (300,1000));
		
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(0, 0, 0, 0));
		// Hover Area
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2; c.gridheight= 1;
		c.weightx = 1.0; c.weighty = 1.0;
		c.insets = new Insets(5, 5, 5, 5);
		
		hoverArea = new JTextArea(20, 3);
		hoverArea.setPreferredSize(new Dimension(100, 300));
		hoverArea.setFont(Globals.f);
		hoverArea.setForeground(Globals.textColor);
		hoverArea.setLineWrap(true);
		hoverArea.setRows(1);
		hoverArea.setWrapStyleWord(true);
		hoverArea.setText(hoverText);
		hoverArea.setEditable(false);
		//hoverArea.setOpaque(false); 
		this.add(hoverArea, c);
		
		// skip button
		c.gridx = 0; c.gridy = 1;
		c.ipadx = 5; c.ipady = 10;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		// testing with buttons
		JButton b1 = new JButton("Wait turn.");
		b1.setMnemonic('w');
		b1.setActionCommand("wait");
		b1.addActionListener(this);
		this.add(b1, c);
	
		// done button
		c.gridx = 1; c.gridy = 1;
		c.ipadx = 5; c.ipady = 10;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		// testing with buttons
		JButton b2 = new JButton("Upgrade selected sat.");
		b2.setMnemonic('u');
		b2.setActionCommand("upgrade");
		b2.addActionListener(this);
		this.add(b2, c);
		
		// chat box
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 2;
		c.gridwidth = 2; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0; c.weighty = 1.0;
		c.insets = new Insets(5, 5, 5, 5);
		
		chatBox = new ChatBox(control);
		this.add(chatBox, c);
		
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	public void printToHoverArea(String s) {
		hoverArea.setText(padRight(s, 280));
	}

	public void selectSatellite (Satellite sat) {
		System.out.println("selectedSatellite  " + control.getStatus());
		selectedSatellite = sat;
	
		switch (control.getStatus()) {
		case "Claiming": {
			// planet or station
			if (sat.getType().equals("S")) {
				// station, print something
				if (sat.owner == control.getOpponent()) {
					control.printToHoverArea("This station is owned by " + control.getOpponent().getName() + ". 100% off limits."); }
				else {  // not owned
					control.printToHoverArea("You do not own this space station. But you could, for the price of a click of a button!" + ((Station) sat).info()); }
				return;	
			}
			else if (! sat.getType().equals("O")) {
				// planet, print something
				control.printToHoverArea(((Planet) sat).info());
			}
			break;
		}
		case "Wait": {
			// planet or station
			if (sat.getType().equals("S")) {
				// station, print something
				String str = "";
				if (sat.owner != null) {
					str = " owned by " + sat.owner.getName();
				}
				control.printToHoverArea("A level " + sat.level + " space station");
				return;
			}
			else if (! sat.getType().equals("O")) {
				// planet, print something
				control.printToHoverArea(((Station) sat).info());
			}			
			break;
		}
		case "Upgrade": {
			// planet or station
			if (sat.getType().equals("S")) {
				// station, print something
				if(sat.owner == control.getPlayer()) { // draw AoI
					control.printToHoverArea(((Station) sat).info());
					}
				else if (sat.owner == control.getOpponent()) {
					control.printToHoverArea("This station is owned by " + control.getOpponent().getName() + ". ");
				}
				else {control.printToHoverArea("This space station is unmanned! Would you like to build here?" + ((Station) sat).info()); }
				return;
			}
			else if (! sat.getType().equals("O")) {
				// planet, print something
				if (sat.owner == control.getOpponent()) { // owned by opponent
					control.printToHoverArea("This planet is already owned by " + sat.owner.getName());
				}
				else if (((Planet) sat).planetWithinAoI() == false) { // outside AoI
					control.printToHoverArea(((Planet) sat).info("This planet is too far away to build on."));
				}
				else if (sat.owner == null) { // not owned
					control.printToHoverArea(((Planet) sat).info("Not currently owned! Invest away."));
				}
				else { // owned by current player
					control.printToHoverArea(((Planet) sat).info("You own this planet!"));
				}
				return;
			}
			break;
		}
		default: {
			control.printToHoverArea(sat.info());
		}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("wait".equals(e.getActionCommand())) {
			switch (control.getStatus()) {
			case "Claiming": {
				// do nothing
				return;
				}
			case "Upgrade": { // Sun taking place as "skip" option.
				// 
				control.getPlayer().addEventChance(0.25);
				control.setStatus("collectResources");
				control.printToInstructionArea("Skipping turn.");
				control.printToPlayerArea();
				return;
				}
			}
		}
		if ("upgrade".equals(e.getActionCommand())) {
			if (selectedSatellite != null) {
				Boolean endTurn = false;
				switch(control.getStatus()) {
				case "Claiming":{
					if (selectedSatellite.getType().equals("S")) // act only upon station claim
						endTurn = ((Station) selectedSatellite).claimStation();
					break;
				}
				case "Wait": {
					break; // do nothing
				}
				case "Upgrade": {
					endTurn = selectedSatellite.upgradeSatelliteToNextLevel();
					break;
				}
				}
				if (endTurn) {
					control.setStatus("endTurn");
				}
			}
			else {
				printToHoverArea("Nothing selected. Select a satellite for more info.");
			}

		}
		
	}

	public ChatBox getChatbox() {
		// TODO Auto-generated method stub
		return chatBox;
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
