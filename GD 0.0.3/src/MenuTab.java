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
		Font f = new Font("Consolas", Font.PLAIN, 20);
		
		GridBagConstraints c = new GridBagConstraints();
		
		// Settings LABEL
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 0; c.gridy = 0; c.ipadx = 5; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		
		settingsLabel = new JLabel("Settings:");
		settingsLabel.setFont(f);
		settingsLabel.setForeground(Color.GREEN);
		settingsLabel.setBounds(0, 0, 100, 20);
		
		this.add(settingsLabel, c);
		
		// Button MUSIC
		c.gridx = 2; c.gridy = 1; c.ipadx = 5; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		
		musicButton = new JButton("Start music");
		musicButton.setMnemonic('m');
		musicButton.setActionCommand("Music");
		musicButton.setFont(f);
		musicButton.addActionListener(this);
		this.add(musicButton, c);
		
		// volume control SLIDER
		c.gridx = 3; c.gridy = 1; c.ipadx = 5; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		
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

				settingsLabel.setText("Volume: " + st.currentVolume);
			}
		});
		
		this.add(volumeControl, c);

		// Button QUIT
		c.gridx = 2; c.gridy = 2; c.ipadx = 5; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.insets = new Insets(5, 5, 5, 5);
		
		quitButton = new JButton("Quit Game");
		quitButton.setActionCommand("Quit");
		quitButton.setMnemonic('q');
		quitButton.addActionListener(this);
		quitButton.setFont(f);
		this.add(quitButton);
		

		// Button Pause (will have to implement other things / for online playing will not want this option
		c.gridx = 2; c.gridy = 3; c.ipadx = 5; c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		
		pauseButton = new JButton("Pause");
		pauseButton.setMnemonic('h');
		pauseButton.setActionCommand("Pause");
		pauseButton.addActionListener(this);
		
		this.add(pauseButton);
		

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
