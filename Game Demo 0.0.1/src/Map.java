import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
	private static final long serialVersionUID = 1L;

	public Map(){
		this.setLayout(null);
		Satellite s01 = new Satellite(0, 0);
		Satellite s02 = new Satellite(30, 30);
		Satellite s03 = new Satellite(800, 450);
		this.add(s01);
		this.add(s02);
		this.add(s03);
		
		s01.setBounds(s01.getX(), s01.getY(), 100, 100);
		s02.setBounds(s02.getX(), s02.getY(),100,100);
		s03.setBounds(s03.getX(), s03.getY(), 100, 100);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawOval(800, 10, 10, 10);
		//g.drawOval(700, 50, 200, 3);
	}

}
