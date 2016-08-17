import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	public WaterPlanet() {
		super(400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
	}

	public WaterPlanet(Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
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
