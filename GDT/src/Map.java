import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller control;
	
	public Map(Controller ctrl){
		control = ctrl;
		this.setLayout(null);
		this.setPreferredSize(new Dimension (1000,1000));
		
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
