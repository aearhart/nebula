

import javax.swing.JPanel;

public class MapTab extends JPanel{
	private ClientController control;
	public String name = "MapTab";
	
	public MapTab(ClientController clientController) {
		control = clientController;
	}
	
	public void addComponents(InfoPanel p1, Map m, InfoPanel2 p2) {
		this.add(p1);
		this.add(m);
		this.add(p2);
	}

	public void update() {
		this.setVisible(true);
	}
	
	public String getName() {
		return name;
	}
}
