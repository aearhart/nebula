import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.Control;

public class Station extends Satellite implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Planet[] planetsInAOI;
	private int AreaOfInfluence = 100;
	private Player owner;
	
	public Station(Controller ctrl, Integer locX, Integer locY, Integer sz) {
		super(ctrl, locX, locY, sz);
		// TODO Auto-generated constructor stub
		
		
		
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	public int getAoI() {
		return AreaOfInfluence;
	}
	
	public Planet[] getPInAOI(){
		return planetsInAOI;
	}
	
	public void setPInAOI(Planet[] planets) {
		planetsInAOI = planets;
	}
	
	
	private void setOwner(Player p) {
		owner = p;
		owner.addStation(this);
		this.setColors(p.getColor(), this.getBorderCol(), this.getSelectCol());;
		repaint();
		control.setStatus("Done Claiming");
		control.print(control.getStatus());
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//TODO ADD Image?
		
		
		
		g.setColor(fillCol);
		g.fillRect(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawRect(0, 0, s, s);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		control.print("CLICKED");
		switch (control.getStatus()) {
		case "Claiming": {
				if(this.owner == null) 
					setOwner(control.getCurrPlayer()); 
				else 
					control.print("This station is already owned by " + owner.getName());
				return;
			}
		case "Test": {
			if(this.owner == null) 
			control.print("Before collecting: w---g---m");
			control.print("                   " + control.getCurrPlayer().getWater() + "   " + control.getCurrPlayer().getGas() + "   " + control.getCurrPlayer().getMineral());
			control.collectResources();
			control.print("After  collecting: w---g---m");
			control.print("                   " + control.getCurrPlayer().getWater() + "   " + control.getCurrPlayer().getGas() + "   " + control.getCurrPlayer().getMineral());
			return;
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch (control.getStatus()) {
		case "Claiming": {
				if(this.owner == null) {
					switchColors();
					control.drawAoI(this);
					repaint(); }
				return;
				
			}
		case "Test": {
				if (this.owner == null) {
					switchColors();
					repaint(); }
				return;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch (control.getStatus()) {
		case "Claiming": {
				if(this.owner == null) {
					switchColors();
					control.removeAoI();
					repaint(); }
				return;
			}
		case "Test": {
				if (this.owner == null) {
					switchColors();
					repaint(); }
				return;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
