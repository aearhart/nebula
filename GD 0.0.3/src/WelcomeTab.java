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
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
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
	
	private String infoText = "Welcome to GD 0.0.3!";
	private String fieldText = "What is your name? (hit enter to select)";

	private Player p;
	private OurButton connectButton;
	

	int ENTER = 0;
	
	public WelcomeTab(ClientController clientController) {
		super(new GridBagLayout());
		control = clientController;
		p = control.getPlayer();
		this.setPreferredSize(new Dimension(Globals.winSize, Globals.winSize));
		
		GridBagConstraints c= new GridBagConstraints();
		
		// Title
		title = new JLabel("Nebula9000");
		title.setFont(Globals.f);
		title.setLocation(0, 0);
		title.setOpaque(true);
		title.setForeground(Globals.textColor);
		title.setBackground(new Color(6, 159, 70, 120));
		title.setBounds(0, 0, Globals.winSize, Globals.winSize / 3);
		
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 0;
		c.gridwidth = 10; c.gridheight = 1;
		c.fill = GridBagConstraints.REMAINDER;
		c.weightx = 1.0; c.weighty = 1.0;
		add(title, c);
		
		// Text field
		textField = new JTextField(20);
		textField.addActionListener(this);
		textField.setText(fieldText);
		textField.setFont(Globals.f);
		
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 3; c.gridy = 1;
		c.gridwidth = 7; c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0; c.weighty = 0.5;
		add(textField, c);
		

		// Connect to server button
		//TODO: create Enter button for WelcomeTab visuals
		//connectButton = new OurButton(this, ENTER, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		connectButton = new OurButton(this, ENTER, KeyStroke.getKeyStroke(' '));
		connectButton.setPreferredSize(new Dimension(connectButton.getWidth() + 1, connectButton.getHeight() + 1));
		connectButton.setText("Join Game");
				
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 4; c.gridy = 2;
		c.gridwidth = 2; c.gridheight = 1;
		c.ipadx = 10; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 1.0; c.weighty = 0.5;
		this.add(connectButton, c);

		// info label
		infoLabel = new JLabel();
		infoLabel.setFont(Globals.f);
		infoLabel.setText(infoText);
		 
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 3; c.gridy = 3;
		c.weightx = 1.0; c.weighty = 1.0;
		c.gridwidth = 4; c.gridheight = 1;
		add(infoLabel, c);
		
				
		// KEYBINDINGS
		connectButton.getInputMap(Globals.IFW).put(connectButton.getKey(), "keyENTER");
		Action keyENTER = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed ENTER");
				connectButton.action();
			}};
		connectButton.getActionMap().put("keyENTER", keyENTER);
		
		textField.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "inputENTER");
		Action inputENTER = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("key typed input field");
				selectAll();
		}};
		textField.getActionMap().put("inputENTER", inputENTER);
	}
	
	public Boolean illegal(String t) {
		if (t.contains("@@")) return true;
		return false;
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
				int r = rand.nextInt(5);
				String[] names = {"Nincompoop", "Anonymous", "Me who shall not be named", "[insert name here]", "Lazy the Nameless", "Captain Somebody"};
				textField.setText(names[r]);
			}
			// set player name
			p.setName(textField.getText());
			control.getWindow().rename(p.getName());
			control.beginGame();
		}
	}
	
	public void selectAll() {
		System.out.println("hey");
		textField.selectAll();
		textField.setCaretPosition(textField.getDocument().getLength());
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
			infoLabel.setText(infoText + "\nYour name:\n" + text + "\n");
		}
		textField.selectAll();
		
		textField.setCaretPosition(textField.getDocument().getLength());
		
	}
	
}
