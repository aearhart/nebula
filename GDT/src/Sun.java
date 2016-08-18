import java.awt.Color;
import java.awt.Graphics;

public class Sun extends Satellite {

	public Sun(Controller ctrl) {
		super(ctrl, 450, 450, 100);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.YELLOW);
		g.fillOval(0, 0, s, s);	
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, s, s);
	}
}
