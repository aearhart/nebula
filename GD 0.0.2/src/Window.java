import javax.swing.*;
import java.awt.*;
import java.awt.*;

public class Window extends JFrame {
	
	private Map map;
	private ClientController control;
	
	public Window(ClientController clientController) {
		super("Game Demo 0.0.2");
		this.setLayout(new FlowLayout());
		control = clientController;
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
