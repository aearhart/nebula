import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuTab extends JPanel implements ActionListener{
	private ClientController control;
	public String name = "MenuTab";
	public SoundTest s;
	public JButton b;
	public JButton b1;
	public JButton b2;
	public JLabel l;
	
	public MenuTab(ClientController clientController) {
		control = clientController;
	}
	
	public void createTab(SoundTest st) {
		s = st;
		
		b = new JButton("Quit Game");
		b.setActionCommand("Quit");
		b.setMnemonic('q');
		b.addActionListener(this);
		b1 = new JButton("Start music");
		b1.setMnemonic('m');
		b1.setActionCommand("Music");
		b1.addActionListener(this);
		
		b2 = new JButton("Test hover over map");
		b2.setMnemonic('h');
		b2.setActionCommand("Hover");
		b2.addActionListener(this);
		
		l = new JLabel("This is the menu");
		l.setForeground(Color.BLACK);
		//l.setBounds(x, y, width, height);
		
		this.add(l);
		this.add(b);
		this.add(b1);
		this.add(b2);
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
			if (s.playing) {
				// stop playing
				b1.setText("Play music");
				s.stopPlaying();
			}
			else {
				//start playing
				b1.setText("Stop music");
				s.startPlaying();
			}
		}
		else if ("Quit".equals(e.getActionCommand())) {
			control.close();
		}
		else if ("Hover".equals(e.getActionCommand())) {
			Map m = control.getMap();
			if (m.hovering) {
				m.clear();
			}
			else
				m.hover("Hovering text");
		}
	}
}
