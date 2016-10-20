import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuTab extends JPanel implements ActionListener{
	private ClientController control;
	public String name = "MenuTab";
	public SoundTest s;
	public JButton b;
	public JButton b1;
	public JTextField tf;
	
	public MenuTab(ClientController clientController) {
		control = clientController;
	}
	
	public void createTab(SoundTest st) {
		s = st;
		tf = new JTextField("This is the menu", 100);
		b = new JButton("Quit Game");
		b.setActionCommand("Quit");
		b.setMnemonic('q');
		b.addActionListener(this);
		b1 = new JButton("Start music");
		b1.setMnemonic('m');
		b1.setActionCommand("Music");
		b1.addActionListener(this);
		this.add(tf);
		this.add(b);
		this.add(b1);
	}
	
	public void addComponents(JTextField f1, JButton b1) {
		this.add(f1);
		this.add(b1);
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
	}
}
