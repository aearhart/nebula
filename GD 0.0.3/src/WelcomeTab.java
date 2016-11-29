/*   WelcomeTab.java
 *     11/13/2016
 *          The title screen
 * 				1. Title
 * 				2. Player name
 * 				3. Join a game by connecting to the server
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class WelcomeTab extends JPanel implements ActionListener{
	private ClientController control;
	public String name = "WelcomeTab";
	

	protected JTextField textField;
	private JLabel infoLabel;
	private JLabel title;
	private BufferedImage titleImage = null;

	
	private String fieldText = "What is your name?";
	private String infoText = "Welcome to GD 0.0.3!";


	private Player p;
	private OurButton connectButton;
	
	private Boolean finished = false;

	int ENTER = 0;
	
	public WelcomeTab(ClientController clientController) {
		super(new GridBagLayout());
		control = clientController;
		p = control.getPlayer();
		//this.setPreferredSize(new Dimension(Globals.mapSize, Globals.mapSize));
		
		setBackground(Color.BLACK);
		
		GridBagConstraints c= new GridBagConstraints();
		
		try {                
			titleImage = ImageIO.read(getClass().getResource("menuBackground.jpg"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		
		
		// Title
		if (titleImage != null) {
			title = new JLabel();
			title.setIcon(new ImageIcon(titleImage, "Nebula 9000"));
//			title.setLocation(0, 0);
			title.setBounds(0, 0, Globals.mapSize, Globals.mapSize / 3);
			title.setPreferredSize(new Dimension(Globals.mapSize, Globals.mapSize/3));
	        title.setHorizontalAlignment(JLabel.CENTER);
		}
		else {
			title = new JLabel("Nebula9000");
			title.setFont(Globals.f);
			title.setLocation(0, 0);
			title.setOpaque(true);
			title.setForeground(Globals.textColor);
			title.setBackground(new Color(6, 159, 70, 120));
//			title.setBounds(0, 0, Globals.mapSize, Globals.mapSize / 5);
		}
//		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0; c.gridy = 0;
		c.gridwidth = 10; c.gridheight = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0; c.weighty = 1.0;
//		c.ipadx = Globals.mapSize; c.ipady = Globals.mapSize/3;
		add(title, c);
		
		// Text field
		textField = new JTextField(20);
		textField.addActionListener(this);
		textField.setText(fieldText);
		textField.setFont(Globals.f);
		textField.setPreferredSize(new Dimension(Globals.mapSize/2, 50));
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0; c.gridy = 1;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 1.0; c.weighty = 0.0;
		add(textField, c);
		

		// Connect to server button
		//TODO: create Enter button for WelcomeTab visuals
		connectButton = new OurButton(this, ENTER, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		connectButton.setPreferredSize(new Dimension(connectButton.getWidth() + 1, connectButton.getHeight() + 1));
		connectButton.setText("Join Game");
				
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1; c.gridy = 1;
		c.gridwidth = 1; c.gridheight = 1;
//		c.ipadx = 10; c.ipady = 10;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.0; c.weighty = 0.0;
		this.add(connectButton, c);

		/*
		// info label
		infoLabel = new JLabel();
		infoLabel.setFont(Globals.f);
		infoLabel.setText(infoText);
		 
		c.anchor = GridBagConstraints.NORTHEAST;
//		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; c.gridy = 3;
		c.weightx = 1.0; c.weighty = 1.0;
		c.gridwidth = 4; c.gridheight = 1;
		add(infoLabel, c);*/
		
				
		// KEYBINDINGS
		connectButton.getInputMap(Globals.IFW).put(connectButton.getKey(), "keyENTER");
		Action keyENTER = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed ENTER");
				connectButton.action();
			}};
		connectButton.getActionMap().put("keyENTER", keyENTER);
	}
	
	public Boolean illegal(String t) {
		if (t.contains("@@")) return true;
		return false;
	}
	
	public Boolean notFinished() {
		return (! finished);
	}
	
	public void update() {
		this.setVisible(true);
	}
	
	public String getName() {
		return name;
	}

	public void clicked(int command) {
		if (command == ENTER) {
			if (textField.getText().equals(fieldText) || textField.getText().equals("")) {
				// player did not choose a name, give random one
				Random rand = new Random();
				String[] names = {"Nincompoop", "Anonymous", "Me who shall not be named", "[insert name here]", "Lazy the Nameless", "Captain Somebody"};
				int r = rand.nextInt(names.length);
				textField.setText(names[r]);
			}
			// set player name
			p.setName(textField.getText());
			control.getWindow().rename(p.getName());
			finished = true;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("event");
		
		String text = textField.getText();
		if (illegal(text)) {
			infoLabel.setText(infoText + "That's an illegal name. A name can't have two @@ symbols.");
			textField.setText(fieldText);
		}
		else if (text.equals(fieldText)){
		}
		else {
//			infoLabel.setText(infoText + "\nYour name:\n" + text + "\n");
		}
		textField.selectAll();
		
		textField.setCaretPosition(textField.getDocument().getLength());
		
	}
	
}
