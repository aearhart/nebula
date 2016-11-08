import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SelectPanel2 extends JPanel implements ActionListener {
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
	
	public SelectPanel2(ClientController clientController) {
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

		pC.gridx = 0; pC.gridy = 0;
		pC.gridwidth = 2; pC.gridheight = 1;
		mainButton = new OurButton(this, 0, 255, 50);
		mainButton.setPreferredSize(new Dimension(mainButton.getWidth() + 1, mainButton.getHeight() + 1));
		panel.add(mainButton, pC);
 
		pC.gridx = 0; pC.gridy = 1;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton button1 = new OurButton(this, 1);
		button1.setPreferredSize(new Dimension(button1.getWidth() + 1, button1.getHeight() + 1));
		panel.add(button1, pC);
		
		pC.gridx = 1; pC.gridy = 1;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton button2 = new OurButton(this, 2);
		button2.setPreferredSize(new Dimension(button2.getWidth() + 1, button2.getHeight() + 1));
		panel.add(button2, pC);

		pC.gridx = 0; pC.gridy = 2;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton button3 = new OurButton(this, 3);
		button3.setPreferredSize(new Dimension(button3.getWidth() + 1, button3.getHeight() + 1));
		panel.add(button3, pC);
		
		pC.gridx = 1; pC.gridy = 2;
		pC.gridwidth = 1; pC.gridheight = 1;
		OurButton button4 = new OurButton(this, 4);
		button4.setPreferredSize(new Dimension(button4.getWidth() + 1, button4.getHeight() + 1));
		panel.add(button4, pC);
		
		c.gridx = 0; c.gridy = 1;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(panel, c);
		
		// Main button
 /*
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 7;
		c.gridwidth = 2; c.gridheight = 1; 
		c.weightx = 1.0; c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		this.add(mainButton, c); 
		
		// Other buttons
		button1 = new OurButton(this, 1, 75, 50);
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.gridx = 0; c.gridy = 8;
		c.gridwidth = 1; c.gridheight = 1; 
		c.weightx = 1.0; c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		this.add(button1, c); 
		
		button2 = new OurButton(this, 2, 75, 50);
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridx = 1; c.gridy = 8;
		c.gridwidth = 1; c.gridheight = 1; 
		c.weightx = 1.0; c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		this.add(button2, c); 
		
		button3 = new OurButton(this, 3, 75, 50);
		button3.remove();
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.gridx = 0; c.gridy = 9;
		c.gridwidth = 1; c.gridheight = 1; 
		c.weightx = 1.0; c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		this.add(button3, c); 
		
		button4 = new OurButton(this, 4, 75, 50);
		button4.disable();
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridx = 1; c.gridy = 9;
		c.gridwidth = 1; c.gridheight = 1; 
		c.weightx = 1.0; c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		this.add(button4, c);
		*/
		
		
		claimPhase();
	}
	
	
	
	public void claimPhase() {
		phase = "Claim";
		selectedSatellite = null;
		printToSelectText(defaultSelectText);
		// mainButton = claim station
		mainButton.setEnabled(false);
		mainButton.setText("Claim Station");
		
		// buttons-- will eventually want to make it disappear
		//noButtons();
	}
	
	public void mainPhase() {
		phase = "Main";
		selectedSatellite = null;
		printToSelectText(defaultSelectText);
		// mainButton = wait turn
		mainButton.setText("Pass");
		mainButton.setEnabled(true);
		
		//noButtons();
	}
	
	public void waitPhase() {
		phase = "Wait";
		selectedSatellite = null;
		printToSelectText(defaultSelectText);
		
	}
	public void printToSelectText(String s) {
		selectText.setText(s);
	}

	private void noButtons() {
		button1.setEnabled(false);
		button1.setText(" ");
		button2.setEnabled(false);
		button2.setText(" ");
		button3.setEnabled(false);
		button3.setText(" ");
		button4.setEnabled(false);
		button4.setText(" ");
	}
	
	private void stationButtons(Boolean b1, Boolean b2) {
		// Station buttons in main phase:
		//button 1
		button1.setText("Upgrade");
		button1.setEnabled(b1);
		button2.setText("Fix");
		button2.setEnabled(b2);
	}
	
	private void planetButtons(Boolean b1, Boolean b2) {
		// Planet buttons in main phase:
		button1.setText("Upgrade");
		button1.setEnabled(b1);
		button2.setText("Buy");
		button2.setEnabled(b2);
	}
	
	public void selectSatellite(Satellite sat) {
		// depending on what is selected(and the phase), the buttons change
		
		System.out.println("selectedSatellite  " + control.getStatus());
		selectedSatellite = sat;

		noButtons();
		switch (phase) {
		case "Claim": {
			// planet or station
			if (sat.getType().equals("S")) {
				// station, print something
				if (sat.owner == control.getOpponent()) {
					printToSelectText("This station is owned by " + control.getOpponent().getName() + ". 100% off limits."); }
				else {  // not owned
					printToSelectText("You do not own this space station. But you could, for the price of a click of a button!" + ((Station) sat).info()); 
					// enable button
					mainButton.setEnabled(true);
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
			else if (! sat.getType().equals("O")) {
				// planet, print something
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
			break;
		}
		default: {
			printToSelectText(sat.info());
		}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Conduct activity based on the button clicked and the phase
		
		Boolean endTurn = false;
		switch (phase) {
		case "Claim": {
			if (e.getActionCommand().equals("main")) {
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
					printToSelectText("You haven't selected anything! Please click on the station you wish to claim.");
				}
			} // end button main
			break;
		} // end case claim station
		case "Main": {
			if (e.getActionCommand().equals("main")) {
				control.getPlayer().addEventChance(0.25);
				control.setStatus("collectResources");
				printToSelectText("Skipping turn.");
				control.printToPlayerArea();
			} // end button main
			if (e.getActionCommand().equals("b1")) {
				// upgrade
				if (selectedSatellite != null && (! selectedSatellite.getType().equals("O"))) { // we have a station or planet
					printToSelectText("The satellite has been upgraded");
					endTurn = selectedSatellite.upgradeSatelliteToNextLevel();
				}
				else {
					// no selected satellite
					printToSelectText("Please select a satellite that you would like to upgrade");
				}
			} // end b1
			if (e.getActionCommand().equals("b2")) {
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
	
}