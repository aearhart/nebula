import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuTab extends JPanel implements ActionListener{
	private ClientController control;
	public String name = "MenuTab";
	public SoundTest music;

	public JButton quitButton;
	public JButton musicButton;
	public JButton pauseButton;
	public JLabel settingsLabel;
	public JLabel welcomeLabel;
	public JSlider volumeControl;
	
	//https://pixabay.com/en/nebula-space-stars-galaxy-668783/
	protected BufferedImage image = null;
	
	public MenuTab(ClientController clientController) {
		control = clientController;
		try {
			image = ImageIO.read(getClass().getResource("menuBackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(Globals.winSize, Globals.winSize));
		this.setLayout(new GridBagLayout());
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image,  0,  0, getWidth(), getHeight(), this);
	}
	public void createTab(SoundTest st) {
		music = st;

		
		GridBagConstraints c = new GridBagConstraints();
		Color testBackground = new Color(200, 50, 150, 50);
		// Welcome LABEL
		welcomeLabel = new JLabel("\n\nWelcome to Game Demo 0.0.3!");
		welcomeLabel.setFont(Globals.f);
		welcomeLabel.setForeground(Globals.textColor);
		welcomeLabel.setBackground(testBackground); // for testing
		welcomeLabel.setOpaque(true); // for testing
		
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.gridx = 0; c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0; c.weightx = 1.0;
		c.gridheight = 1; c.gridwidth = 2;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(welcomeLabel, c);
		
		// info panel
		InfoPanel p = new InfoPanel(control);
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 2; c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.0; c.weighty = 1.0;
		c.gridheight = 10; c.gridwidth = 2;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(p, c);
		
		// 2nd panel
		InfoPanel2 p2 = new InfoPanel2(control);
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 4; c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.0; c.weighty = 1.0;
		c.gridheight = 10; c.gridwidth = 2;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(p2, c);
		
		// Settings LABEL

		testBackground = new Color(200, 150, 50, 50);
		settingsLabel = new JLabel("Settings:");
		settingsLabel.setFont(Globals.f);
		settingsLabel.setForeground(Globals.textColor);
		settingsLabel.setBackground(testBackground);
		settingsLabel.setOpaque(true);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridheight = 1; c.gridwidth = 2;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(settingsLabel, c);
		
		// Button MUSIC
		musicButton = new JButton("Start music");
		musicButton.setMnemonic('m');
		musicButton.setActionCommand("Music");
		musicButton.setFont(Globals.f);
		musicButton.addActionListener(this);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridheight = 1; c.gridwidth = 1;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(musicButton, c);
		
		
		// volume control SLIDER
		volumeControl = new JSlider(JSlider.HORIZONTAL, -60, 6, 0);
		volumeControl.setForeground(new Color(1, 117, 170, 255));
		volumeControl.setBackground(new Color(109, 208,255, 40));
		volumeControl.setOpaque(false);
		volumeControl.setSize(100, 50);
		volumeControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				if (volumeControl.getValue() == -60) {
					st.stopPlaying();
				}
				else if (! st.playing) {
					st.startPlaying();
					st.setVolume(volumeControl.getValue());
				}
				else {
					st.setVolume(volumeControl.getValue());	
				}

			}
		});
		
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 1; c.gridy = 4;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridheight = 1; c.gridwidth = 1;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(volumeControl, c);

		/* placement is NOT working >:(
		// Button Pause (will have to implement other things / for online playing will not want this option
		pauseButton = new JButton("Pause");
		pauseButton.setFont(Globals.f);
		pauseButton.setMnemonic('h');
		pauseButton.setActionCommand("Pause");
		pauseButton.addActionListener(this);
				
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0; c.gridy = 8;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridheight = 1; c.gridwidth = 1;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(pauseButton);
				
		// Button QUIT
		quitButton = new JButton("Quit Game");
		quitButton.setActionCommand("Quit");
		quitButton.setMnemonic('q');
		quitButton.addActionListener(this);
		quitButton.setFont(Globals.f);
		
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 1; c.gridy = 8;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridheight = 1; c.gridwidth = 1;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(quitButton);
		*/

		

	}
	
	public void update() {
		this.setVisible(true);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("Music".equals(e.getActionCommand())) {
			if (music.playing) {
				// stop playing
				musicButton.setText("Play music");
				music.stopPlaying();
			}
			else {
				//start playing
				musicButton.setText("Mute music");
				music.startPlaying();
			}
		}
		else if ("Quit".equals(e.getActionCommand())) {
			control.close();
		}
		else if ("Pause".equals(e.getActionCommand())) {
			Map m = control.getMap();
			if (m.hovering) {
				m.clear();
			}
			else
				m.hover("Hovering text");
		}
	}
}
