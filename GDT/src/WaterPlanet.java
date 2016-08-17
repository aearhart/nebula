import java.awt.Graphics;

public class WaterPlanet extends Planet {

	public WaterPlanet(Integer locX, Integer locY) {
		super(locX, locY);
		// TODO Auto-generated constructor stub
	}

	public WaterPlanet(Integer ty, Integer locX, Integer locY, Integer sz) {
		super(ty, locX, locY, sz);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawOval(0, 0, s, s);
		g.drawOval(0, 0, s-1, s-1);
		g.drawOval(1, 1, s-1, s-1);
	}
}
