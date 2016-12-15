import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PlayerArea extends JPanel {

	private Player player;
	private ClientController control;
	
	private BufferedImage gasIcon;
	private BufferedImage mineralIcon;
	private BufferedImage waterIcon;
	
	private JLabel nameField;
	private JLabel gasLabel;
	private JLabel gasText;
	private JLabel mineralLabel;
	private JLabel mineralText;
	private JLabel waterLabel;
	private JLabel waterText;
	private JLabel victoryText;
	private JTextArea otherField;
	private JLabel testField;
	
	private int iconSize = 25;
	
	private Font f = new Font("Consolas", Font.PLAIN, 20);
	
	private int width = 300;
	private int height = 450;
	private int smallerHeight = 400;
	
	public PlayerArea(ClientController ctrl) {
		super(new GridBagLayout());

		if (Globals.winSizeSet) {
			width = Globals.mapSize / 3;
			height = Globals.mapSize / 2 - 50;
			smallerHeight = Globals.mapSize / 2 - 100;
		}
		
		GridBagConstraints c = new GridBagConstraints();
		
		control = ctrl;
		player = control.getPlayer();
		
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(width, height));
		
		try {
			gasIcon = ImageIO.read(getClass().getResource("gas_resource.png"));
			mineralIcon = ImageIO.read(getClass().getResource("mineral_resource.png"));
			waterIcon = ImageIO.read(getClass().getResource("water_resource.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		
		// name
		nameField = new JLabel();
		nameField.setOpaque(false);
//		nameField.setPreferredSize(new Dimension(100, 30));
		nameField.setFont(Globals.f);
		nameField.setForeground(Globals.textColor);
		nameField.setText(player.getName());
		c.gridx = 0; c.gridy = 0;
		c.gridwidth = 6; c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(0,2,0,2);
		c.weightx = 1.0; c.weighty = 0.0;
		this.add(nameField, c);
		
		// resources		
		gasLabel = new JLabel();
		gasLabel.setIcon(new ImageIcon(gasIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH), "Gas"));
		c.gridx = 0; c.gridy = 1;
		c.gridwidth = 1; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		c.weightx = 0.1; c.weighty = 0.0;
		c.ipadx = iconSize; c.ipady = iconSize;
		c.insets = new Insets(0,0,0,0);
		this.add(gasLabel, c);
		
		gasText = new JLabel();
		gasText.setOpaque(false);
		gasText.setFont(f);
		gasText.setForeground(Globals.textColor);
		gasText.setVerticalTextPosition(JLabel.CENTER);
		gasText.setText(player.getGasString());
		gasText.setText("9999");
		c.gridx = 1; c.gridy = 1;
		c.ipadx = 50; c.ipady = 30;
		c.insets = new Insets(0,2,0,2);
		this.add(gasText, c);
				
		mineralLabel = new JLabel();
		mineralLabel.setIcon(new ImageIcon(mineralIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH), "Mineral"));
		c.gridx = 2; c.gridy = 1;
		c.ipadx = iconSize; c.ipady = iconSize;
		c.insets = new Insets(0,0,0,0);
		this.add(mineralLabel, c);

		mineralText = new JLabel();
		mineralText.setOpaque(false);
		mineralText.setFont(f);
		mineralText.setForeground(Globals.textColor);
		mineralText.setVerticalTextPosition(JLabel.BOTTOM);
		mineralText.setText(player.getMineralString());
		mineralText.setText("9999");
		c.gridx = 3; c.gridy = 1;
		c.ipadx = 50; c.ipady = 30;
		c.insets = new Insets(0,2,0,2);
		this.add(mineralText, c);
				
		waterLabel = new JLabel();
		waterLabel.setIcon(new ImageIcon(waterIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH), "Water"));
		c.gridx = 4; c.gridy = 1;
		c.ipadx = iconSize; c.ipady = iconSize;
		c.insets = new Insets(0,0,0,0);
		this.add(waterLabel, c);

		waterText = new JLabel();
		waterText.setOpaque(false);
		waterText.setFont(f);
		waterText.setForeground(Globals.textColor);
		waterText.setVerticalTextPosition(JLabel.TOP);
		waterText.setText(player.getWaterString());
		waterText.setText("9999");
		c.gridx = 5; c.gridy = 1;
		c.ipadx = 50; c.ipady = 30;
		c.insets = new Insets(0,2,0,2);
		this.add(waterText, c);
		
		// victory points
		
		victoryText = new JLabel();
		victoryText.setOpaque(false);
		victoryText.setFont(f);
		victoryText.setForeground(Globals.textColor);
		victoryText.setVerticalTextPosition(JLabel.TOP);
		victoryText.setText("VP = " + player.getVictoryPoints());
		
		c.gridx = 1; c.gridy = 2;
		c.ipadx = 50; c.ipady = 30;
		c.insets = new Insets(0,2,0,2);
		this.add(victoryText, c);
		
		
		// other
		otherField = new JTextArea();
		otherField.setPreferredSize(new Dimension(width, smallerHeight));
		otherField.setFont(Globals.f);
		otherField.setLineWrap(true);
		otherField.setWrapStyleWord(true);
		otherField.setForeground(Globals.textColor);
		otherField.setEditable(false);
		otherField.setOpaque(false);
		JScrollPane otherSP = new JScrollPane(otherField);
		otherSP.setOpaque(false);
		otherSP.getViewport().setOpaque(false);
		otherField.setText("You can find your stats here.");
		
		c.ipady = 0;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0; c.gridy = 3;
		c.gridwidth = 6; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0; c.weighty = 0.9;
		this.add(otherField, c);
		
		// test field
		testField = new JLabel();
		testField.setOpaque(false);
		testField.setFont(f);
		testField.setForeground(Globals.textColor);
		testField.setVerticalTextPosition(JLabel.TOP);
		//testField.setText();
		
		c.gridx = 0; c.gridy = 4;
		c.ipadx = 50; c.ipady = 30;
		c.insets = new Insets(0,2,0,2);
		this.add(testField, c);
	}
	
	public void update() {
		gasText.setText(Integer.toString(player.getGas()));
		mineralText.setText(Integer.toString(player.getMineral()));
		waterText.setText(Integer.toString(player.getWater()));
		victoryText.setText("VP = " + player.getVictoryPoints());
		testField.setText("  pv = " + player.getSpaceship().getNumPlanetsVisited() + "  tp = " + control.numOfVisitablePlanets());
		//otherField.setText(s);
	}
	
	public void update(String s) {
		gasText.setText(Integer.toString(player.getGas()));
		mineralText.setText(Integer.toString(player.getMineral()));
		waterText.setText(Integer.toString(player.getWater()));
		victoryText.setText("VP = " + player.getVictoryPoints());
		testField.setText("  pv = " + player.getSpaceship().getNumPlanetsVisited() + "  tp = " + control.numOfVisitablePlanets());
		otherField.setText(s);
	}

}
