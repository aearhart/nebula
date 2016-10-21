import javax.swing.*;
import java.awt.*;
import java.awt.*;

public class Window extends JFrame {
	
//	private Map map;
	private ClientController control;
	
	JTabbedPane tabbedPane = new JTabbedPane();
	private String[] tabNames = {"Menu", "Map"};
	private Color[] tabColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
	
	
	public Window(ClientController clientController, String playerName) {
		super("Welcome to Game Demo 0.0.3");
		//this.setLayout(new FlowLayout());
		control = clientController;
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		
		//Map m = new Map();
		//this.add(m);
		
		this.setLocationRelativeTo(null);
		this.add(tabbedPane, BorderLayout.CENTER);
		this.pack();

		this.setVisible(true);
	}
	
	public void addTab(JPanel tab, String tabName) {
		tabbedPane.addTab(tabName, tab);
		this.pack();
		update();
	}
	
	public void addTabs(JPanel[] tabs) {
		for (int i = 0; i < tabNames.length; i++)
			tabbedPane.addTab(tabNames[i], tabs[i]);
		this.add(tabbedPane, BorderLayout.CENTER);
		
		this.pack();
		update();
	}
	
	public void removeAtab(int tabPosition) {
		tabbedPane.remove(tabPosition);
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
	
	public void rename(String str) {
		this.setTitle("Game Demo 0.0.3 for " + str);
	}
	
	public void update() {
		this.setVisible(true);
	}

}
