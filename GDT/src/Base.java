import java.awt.Graphics;

public class Base extends Satellite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Planet[] planetsInAOI;
	private int AOI;
	private Player controller;
	
	public Base(Integer locX, Integer locY, Integer sz) {
		super(locX, locY, sz);
		// TODO Auto-generated constructor stub
	}
	
	public Planet[] getPInAOI(){
		return planetsInAOI;
	}
	
	public void setPInAOI(Planet[] planets) {
		planetsInAOI = planets;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawOval(0, 0, s, s);
	}

}
