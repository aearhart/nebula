import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuTab extends JPanel{
	private ClientController control;
	public String name = "MenuTab";
	
	public MenuTab(ClientController clientController) {
		control = clientController;
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
}
