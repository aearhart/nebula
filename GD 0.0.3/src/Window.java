import javax.swing.*;
import java.awt.*;
import java.awt.*;

public class Window extends JFrame {
	
//	private Map map;
	private ClientController control;
	
	JTabbedPane tabbedPane = new JTabbedPane();
	private String[] tabNames = {"Menu", "Map"};
	 
	public Window(ClientController clientController, String playerName) {
		super("Game Demo 0.0.3 for " + playerName);
		//this.setLayout(new FlowLayout());
		control = clientController;
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
		
		//Map m = new Map();
		//this.add(m);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}
	
	public void addTabs(JPanel[] tabs) {
		for (int i = 0; i < tabNames.length; i++)
			tabbedPane.addTab(tabNames[i], tabs[i]);
	
		this.add(tabbedPane, BorderLayout.CENTER);
		this.pack();
		update();
	}
	/*
	public void add(Map m) {
		map = m;
		this.add(map);
		this.setVisible(true);
	}
	*/
	
	public void update() {
		this.setVisible(true);
	}

}
