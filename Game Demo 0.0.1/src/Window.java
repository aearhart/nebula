import javax.swing.*;
import java.awt.*;
import java.awt.*;

public class Window extends JFrame {
	
	private Map map;
	
	public Window() {
		
		this.setSize(1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
	}
	
	public void add(Map m) {
		map = m;
		this.add(map);
		this.setVisible(true);
	}
	
	
}
