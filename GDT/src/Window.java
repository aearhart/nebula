import javax.swing.*;
import java.awt.*;
import java.awt.*;

public class Window extends JFrame {
	
	private Map map;
	private Controller control;
	
	public Window(Controller ctrl) {
		super("Game Demo 0.0.1");
		this.setLayout(new FlowLayout());
		control = ctrl;
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
		//Map m = new Map();
		//this.add(m);
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
