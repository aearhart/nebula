import javax.swing.*;
import java.awt.*;
import java.awt.*;

public class Window extends JFrame {
	
	private Map map;
	
	public Window() {
		super("Game Demo 0.0.1");
		this.setSize(1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
		m = new Map();
		this.add(m);
		this.setVisible(true);
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
