import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller control;
	
	public Map(Controller ctrl){
		control = ctrl;
		this.setLayout(null);
		this.setPreferredSize(new Dimension (1000,1000));
		
		/*
		Satellite s01 = new Satellite(450, 400); // sun 
		Satellite s02 = new Satellite(1, 10, 40, 30); // water
		Satellite s03 = new Satellite(2, 40, 600, 70); // gas
		Satellite s04 = new Satellite(3, 700, 58, 47); // metal
		
		this.add(s01);
		this.add(s02);
		this.add(s03);
		this.add(s04);
		
		s01.setBounds(s01.getLocX(), s01.getLocY(), s01.getSz(), s01.getSz());
		s02.setBounds(s02.getLocX(), s02.getLocY(), s02.getSz(), s02.getSz());
		s03.setBounds(s03.getLocX(), s03.getLocY(), s03.getSz(), s03.getSz());
		s04.setBounds(s04.getLocX(), s04.getLocY(), s04.getSz(), s04.getSz());
		*/
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(control.AoIc);
		g.drawOval(control.AoIx, control.AoIy, control.AoIs, control.AoIs);
		
		g.setColor(Color.BLACK);
		for(int i = 0; i < 11; i++)
			for(int j = 0; j < 11; j++)
				g.drawRect(i*100, j*100, 100, 100);
		//g.drawOval(800, 10, 10, 10);
		//g.drawOval(700, 50, 200, 3);
		
	}

}
