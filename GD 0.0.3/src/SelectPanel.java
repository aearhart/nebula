import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;


public class SelectPanel extends JPanel{
	private ClientController control;
	
	private String phase;
	public Satellite selectedSatellite = null;
	
	private JTextArea selectText;
	private String defaultSelectText = "Select an item for more information.";

	
	private OurButton mainButton;
	
	private OurButton button1;
	private OurButton button2;
	private OurButton button3;
	private OurButton button4;
	
	int MAIN = 0;
	int B1 = 1;
	int B2 = 2;
	int B3 = 3;
	int B4 = 4;
	 
	public SelectPanel(ClientController clientController) {
		control = clientController;
		
		this.setPreferredSize(new Dimension(300, 500));
		
		this.setLayout(new GridBagLayout()); 
		this.setBackground(new Color(0, 128, 128, 255));
		
		GridBagConstraints c = new GridBagConstraints();
		
		
		// select text area
		selectText = new JTextArea(20, 3);
		selectText.setBounds(0, 0, 100, 400);
		selectText.setFont(Globals.f);
		selectText.setForeground(Globals.textColor);
		selectText.setLineWrap(true);
		selectText.setWrapStyleWord(true);
		selectText.setText(defaultSelectText);
		selectText.setEditable(false);
		selectText.setOpaque(false);
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1; c.gridheight= 1;
		c.weightx = 1.0; c.weighty = 1.0;
		c.insets = new Insets(5, 5, 5, 5);
		this.add(selectText, c);
		
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		GridBagConstraints pC = new GridBagConstraints();

		// Main Button
		pC.gridx = 0; pC.gridy = 0;
		pC.gridwidth = 2; pC.gridheight = 1;
		mainButton = new OurButton(this, 0, KeyStroke.getKeyStroke(' '), 255, 50);
		mainButton.setPreferredSize(new Dimension(mainButton.getWidth() + 1, mainButton.getHeight() + 1));
		
		panel.add(mainButton, pC);
 
		// Button 1
		pC.gridx = 0; pC.gridy = 1;
		pC.gridwidth = 1; pC.gridheight = 1;
		button1 = new OurButton(this, 1, KeyStroke.getKeyStroke('1'));
		button1.setPreferredSize(new Dimension(button1.getWidth() + 1, button1.getHeight() + 1));
		panel.add(button1, pC);
		
		// Button 2
		pC.gridx = 1; pC.gridy = 1;
		pC.gridwidth = 1; pC.gridheight = 1;
		button2 = new OurButton(this, 2, KeyStroke.getKeyStroke('2'));
		button2.setPreferredSize(new Dimension(button2.getWidth() + 1, button2.getHeight() + 1));
		panel.add(button2, pC);

		// Button 3
		pC.gridx = 0; pC.gridy = 2;
		pC.gridwidth = 1; pC.gridheight = 1;
		button3 = new OurButton(this, 3, KeyStroke.getKeyStroke('3'));
		button3.setPreferredSize(new Dimension(button3.getWidth() + 1, button3.getHeight() + 1));
		panel.add(button3, pC);
		
		// Button 4
		pC.gridx = 1; pC.gridy = 2;
		pC.gridwidth = 1; pC.gridheight = 1;
		button4 = new OurButton(this, 4, KeyStroke.getKeyStroke('4'));
		button4.setPreferredSize(new Dimension(button4.getWidth() + 1, button4.getHeight() + 1));
		panel.add(button4, pC);
		
		// Button Panel
		c.gridx = 0; c.gridy = 1;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(panel, c);
		
		
		// keyBindings

		mainButton.getInputMap(Globals.IFW).put(mainButton.getKey(), "keyMAIN");
		Action keyMAIN = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed MAIN");
				mainButton.action();
			}};
		mainButton.getActionMap().put("keyMAIN", keyMAIN);

		button1.getInputMap(Globals.IFW).put(button1.getKey(), "keyB1");
		Action keyB1 = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed B1");
				button1.action();
			}};
		button1.getActionMap().put("keyB1", keyB1);
		
		button2.getInputMap(Globals.IFW).put(button2.getKey(), "keyB2");
		Action keyB2 = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed B2");
				button2.action();
			}};
		button2.getActionMap().put("keyB2", keyB2);
		
		button3.getInputMap(Globals.IFW).put(button3.getKey(), "keyB3");
		Action keyB3 = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed B3");
				button3.action();
			}};
		button3.getActionMap().put("keyB3", keyB3);
		
		button4.getInputMap(Globals.IFW).put(button4.getKey(), "keyB4");
		Action keyB4 = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed B4");
				button4.action();
			}};
		button4.getActionMap().put("keyB4", keyB4);
		
		//set phase
		claimPhase();
	}
	
	public void claimPhase() {
		phase = "Claim";
		selectedSatellite = null;
		printToSelectText(defaultSelectText);
		// mainButton = claim station
		mainButton.setText("Claim Station");
		mainButton.disable();
		// remove buttons
		noButtons();
	}
	
	public void mainPhase() {
		phase = "Main";
		selectedSatellite = null;
		printToSelectText(defaultSelectText);
		// mainButton = wait turn
		mainButton.setText("Pass");
		mainButton.enable();
		
		noButtons();
	}
	
	public void waitPhase() {
		phase = "Wait";
		selectedSatellite = null;
		printToSelectText(defaultSelectText);
		mainButton.remove();
		noButtons();
	}
	
	
	public void printToSelectText(String s) {
		selectText.setText(s);
	}

	private void noButtons() {
		button1.remove();
		button2.remove();
		button3.remove();
		button4.remove();
	}
	
	private void stationButtons(Boolean b1, Boolean b2) {
		// Station buttons in main phase:
		//button 1
		button1.setText("Upgrade");
		if (b1) button1.enable();
		else button1.disable();
		button2.setText("Fix");
		if (b2)	button2.enable();
		else button2.disable();
	}
	
	private void planetButtons(Boolean b1, Boolean b2) {
		// Planet buttons in main phase:
		button1.setText("Upgrade");
		if (b1) button1.enable();
		else button1.disable();
		button2.setText("Buy");
		if (b2)	button2.enable();
		else button2.disable();
	}
	
	public void selectSatellite(Satellite sat) {
		// depending on what is selected(and the phase), the buttons change
		
		System.out.println("selectedSatellite  " + control.getStatus());
		selectedSatellite = sat;
		noButtons();
		switch (phase) {
		case "Claim": {
			// planet or station
			mainButton.disable();
			if (sat.getType().equals("S")) {
				// station, print something
				if (sat.owner == control.getOpponent()) {
					printToSelectText("This station is owned by " + control.getOpponent().getName() + ". 100% off limits."); }
				else {  // not owned
					printToSelectText("You do not own this space station. But you could, for the price of a click of a button!" + ((Station) sat).info()); 
					// enable button
					mainButton.enable();
					}
				return;	
			}
			else if (! sat.getType().equals("O")) {
				// planet, print something
				printToSelectText(((Planet) sat).info());
			}
			break;
		} // end claim 
		case "Wait": {
			// planet or station
			if (sat.getType().equals("S")) { // STATION
				// select text output
				String str = "";
				if (sat.owner != null) {
					str = " owned by " + sat.owner.getName();
				}
				printToSelectText("A level " + sat.level + " space station");

				return;
			}
			else {
				// planet/sun, print something
				printToSelectText(sat.info());
			}
			break;
		}
		case "Main": {
			// planet or station
			if (sat.getType().equals("S")) { // STATION

				if(sat.owner == control.getPlayer()) {
					printToSelectText(((Station) sat).info());
					stationButtons(true, ((Station)sat).isMalfunctioning());
					}
				else if (sat.owner == control.getOpponent()) {
					printToSelectText("This station is owned by " + control.getOpponent().getName() + ". ");
					stationButtons(false, false);
				}
				else {printToSelectText("This space station is unmanned! Would you like to build here?" + ((Station) sat).info()); 
					stationButtons(false, false);
				}
				return;
			}
			else if (! sat.getType().equals("O")) {
				// planet, print something
				if (sat.owner == control.getOpponent()) { // owned by opponent
					printToSelectText("This planet is already owned by " + sat.owner.getName());
					planetButtons(false, false);
				}
				else if (((Planet) sat).planetWithinAoI() == false) { // outside AoI
					printToSelectText(((Planet) sat).info("This planet is too far away to build on."));
					planetButtons(false, false);
				}
				else if (sat.owner == null) { // not owned
					printToSelectText(((Planet) sat).info("Not currently owned! Invest away."));
					planetButtons(false, true);
				}
				else { // owned by current player
					printToSelectText(((Planet) sat).info("You own this planet!"));
					planetButtons(true, false);
				}
				return;
			}
			else { //sun
				printToSelectText(sat.info());
			}
			break;
		}
		default: {
			printToSelectText(sat.info());
		}
		}
		
	}
	
	public void clicked(int command) {
		// Conduct activity based on the button clicked and the phase
		// mainButton = 0, button 1 = 1, button 2 = 2, button 3 = 3, button 4 = 4
		Boolean endTurn = false;
		switch (phase) {
		case "Claim": {
			if (command == MAIN) {
				// mainButton
				if (selectedSatellite != null) {
					if (selectedSatellite.getType().equals("S")) {
						endTurn = ((Station) selectedSatellite).claimStation();
					}
					else { // selected satellite is not a station
						printToSelectText("You can't claim this satellite! You can only claim a station.");
					}
				}
				else { // no selected satellite
					printToSelectText("ahhhhhhh");
				}
			} // end button main
			break;
		} // end case claim station
		case "Main": {
			if (command == MAIN) {
				control.getPlayer().addEventChance(0.25);
				control.setStatus("collectResources");
				printToSelectText("Skipping turn.");
				control.printToPlayerArea();
			} // end button main
			if (command == B1) {
				// upgrade
				if (selectedSatellite != null && (! selectedSatellite.getType().equals("O"))) { // we have a station or planet
					printToSelectText("The satellite has been upgraded");
					endTurn = selectedSatellite.upgradeSatelliteToNextLevel();
				}
				else {
					// no selected satellite
					printToSelectText("Please select a satellite that you would like to upgrade. The upgrade costs vary depending on the satellite.");
				}
			} // end b1
			if (command == B2) {
				if (selectedSatellite != null) {
					if (selectedSatellite.getType().equals("S")) { // STATION --> fix
						Player p = control.getPlayer();
						Station base = p.getBase();
						if (base.isMalfunctioning() && p.getGas() >2 && p.getMineral() >5 && p.getWater() > 3) {
							p.subGas(2); p.subMineral(5); p.subWater(3);
							endTurn = base.fixMalfunction();
						}
						else {
							printToSelectText("You don't have enough materials to fix this station.");
						}
					}
					else if (! selectedSatellite.getType().equals("O")) { // PLANET --> buy
						endTurn = ((Planet) selectedSatellite).buyPlanet();
					}
				}
				else { 
					printToSelectText("Please select a satellite in order .."); // this shouldn't be an issue
				}
			} // end b2
			break;
		} // end case main
		} // end switch
		
		if (endTurn) control.setStatus("endTurn");
	}



	public void buttonInfo(int buttonNum) {
		// A disabled button has been clicked
		switch (phase) {
		case "Claim": {
			// only mainButton will be available
			printToSelectText("Click this button after you have selected a station in order to claim it as your own.");
			break;
		}
		case "Main": {
			if (buttonNum == MAIN) {
				printToSelectText("Pass the turn. Do nothing."); // This should never be called
			}
			else if (buttonNum == B1) { // always upgrade
				printToSelectText("Upgrade a station or a planet to produce more resources or other benefits.");
			}
			else if (buttonNum == B2) { // either: fix or buy
				if (selectedSatellite.getType().equals("S")) {
					// fix:
					printToSelectText("If this station is damaged, this button will use the necessary resources to fix the problem.");
				}
				else {
					// buy:
					printToSelectText("Extend your reach! Buying/claiming a planet will increase the number of resources it produces. But you can only buy planets that you have not bought already and that are within range of your station.");
				}
			}
			else if (buttonNum == B3) {
				
			}
			else { // B4
				
			}
			break;
		}
		default: {
			
		}
		}
	}
	
}
