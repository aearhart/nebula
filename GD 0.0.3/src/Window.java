import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class Window extends JFrame implements KeyListener{
	
//	private Map map;
	private ClientController control;
	
	JTabbedPane tabbedPane = new JTabbedPane();
	private String[] tabNames = {"Map"};
	private Color[] tabColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
	
	// selected indexes
	private static final int MENU = 0;
	private static final int MAP = 1;
	
	private static final char MENUkey = '-';
	private static final char MAPkey = '=';
	
	public Window(ClientController clientController, String playerName) {
		super("Welcome to Game Demo 0.0.3");
		//this.setLayout(new FlowLayout());
		control = clientController;
		
		
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setBackground(Color.BLACK);
		
		//Map m = new Map();
		//this.add(m);
		Font f = new Font("Consolas", Font.PLAIN, 20);
		
		this.setLocationRelativeTo(null);
		tabbedPane.setFont(f);
		this.add(tabbedPane, BorderLayout.CENTER);
		this.pack();

		this.setVisible(true);
		setFocusable(true);
	}
	
	public void addTab(JPanel tab, String tabName) {
		tabbedPane.addTab(tabName, tab);
		//this.add(tabbedPane, BorderLayout.CENTER);
		this.pack();
		update();
	}
	
	public void addTabs(JPanel[] tabs) {
		for (int i = 0; i < tabNames.length; i++)
			tabbedPane.addTab(tabNames[i], tabs[i]);
		//this.add(tabbedPane, BorderLayout.CENTER);
		
		this.pack();
		update();
	}
	
	public void removeAtab(int tabPosition) {
		tabbedPane.remove(tabPosition);
		this.pack();
		update();
	}
	
	public int getActiveTab() {
		return tabbedPane.getSelectedIndex();
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

	public void setMapFront() {
		tabbedPane.setSelectedIndex(MAP);
	}
	
	public void setMenuFront() {
		tabbedPane.setSelectedIndex(MENU);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// change selected tabbedpane
		//TODO: changing selected tab: cycle or specific key? ALSO this isn't working at all
		System.out.println("KEY TYPED!!!");
		if (! control.getStatus().equals("Welcome")) {
			System.out.println("changing tabs");
			if (e.getKeyChar() == MENUkey)
				setMenuFront();
			else if (e.getKeyChar() == MAPkey)
				setMapFront();
		}
	}
}
