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

		/*
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
		// skip button 
		c.gridx = 1; c.gridy = 1;
		c.ipadx = 5; c.ipady = 10;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		// testing with buttons
		JButton b2 = new JButton("Upgrade selected sat.");
		b2.setMnemonic('u');
		b2.setActionCommand("upgrade");
		b2.addActionListener(this);
		this.add(b2, c);*/
		
		// OurButton TEST
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		GridBagConstraints pC = new GridBagConstraints();

		pC.gridx = 0; pC.gridy = 0;
		pC.gridwidth = 2; pC.gridheight = 1;
		OurButton oB1 = new OurButton(control, 255, 75, "Test 1");
		oB1.setPreferredSize(new Dimension(oB1.getWidth() + 1, oB1.getHeight() + 1));
		panel.add(oB1, pC);

		pC.gridx = 0; pC.gridy = 1;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton oB2 = new OurButton(control, "Test 2");
		oB2.setPreferredSize(new Dimension(oB2.getWidth() + 1, oB2.getHeight() + 1));
		panel.add(oB2, pC);
		
		pC.gridx = 1; pC.gridy = 1;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton oB3 = new OurButton(control, "Test 3");
		oB3.setPreferredSize(new Dimension(oB3.getWidth() + 1, oB3.getHeight() + 1));
		panel.add(oB3, pC);

		pC.gridx = 0; pC.gridy = 2;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton oB4 = new OurButton(control, "Test 4");
		oB4.setPreferredSize(new Dimension(oB4.getWidth() + 1, oB4.getHeight() + 1));
		panel.add(oB4, pC);
		
		pC.gridx = 1; pC.gridy = 2;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton oB5 = new OurButton(control, "Test 5");
		oB5.setPreferredSize(new Dimension(oB5.getWidth() + 1, oB5.getHeight() + 1));
		panel.add(oB5, pC);

		
		c.gridx = 0; c.gridy = 1;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(panel, c);
		
		
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
