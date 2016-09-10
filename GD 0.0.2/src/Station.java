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
	private int costWater = 0;
	private int costMetal = 0;
	private int costGas = 0;
	private int level = 0;
	
	public Station(ClientController clientController, Integer locX, Integer locY, Integer sz, String n) {
		super(clientController, locX, locY, sz);
		// TODO Auto-generated constructor stub
		
		this.defineType("S");
		this.setResource(0);
		this.setName(n);
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
		//control.printToInstructionArea("CLICKED");
		//System.out.println(control.getStatus());
		switch (control.getStatus()) {
		case "Claiming": {
				if(this.owner == null) {
					System.out.println(owned);
					setOwner(control.getPlayer()); 
					repaint();
					control.setStatus("");
					control.printToInstructionArea(control.getStatus());
				}
				else {
					System.out.println("already owned!");
					control.printToInstructionArea("This station is already owned by " + owner.getName());
				}
				return;
			}
		case "Test": {
			if(this.owner == null) 
			control.printToInstructionArea("Before collecting: w---g---m");
			control.printToInstructionArea("                   " + control.getPlayer().getWater() + "   " + control.getPlayer().getGas() + "   " + control.getPlayer().getMineral());
			//control.collectResources();
			control.printToInstructionArea("After  collecting: w---g---m");
			control.printToInstructionArea("                   " + control.getPlayer().getWater() + "   " + control.getPlayer().getGas() + "   " + control.getPlayer().getMineral());
			return;
			}
		case "Upgrade": {
			if (this.owner == control.getPlayer() && control.getPlayer().getGas() >= costGas && control.getPlayer().getMineral() >= costMetal && control.getPlayer().getWater() >= costWater) {
				control.getPlayer().subGas(costGas);
				control.getPlayer().subMineral(costMetal);
				control.getPlayer().subWater(costWater);
				level++;
				// double cost
				costGas += costGas;
				costMetal += costMetal;
				costWater += costWater;
				AreaOfInfluence = (int)((float)(AreaOfInfluence) * 1.5);
				control.setStatus("collectResources");
				control.printToInstructionArea("Upgraded station to level " + level + ".");
				control.update();
			}
			else {control.printToInstructionArea("Insufficient funds or privileges.");}

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
			switchColors();
			control.drawAoI(this);
			repaint();
			return;
			}
		case "Upgrade": {
			if(this.owner == control.getPlayer()) {
				switchColors();
				control.drawAoI(this);
				repaint(); 
				control.printToHoverArea("This level " + level + " " + "station costs : " + costWater + " water, " + costMetal + " metal, and " + costGas + " gas.");
				}
			else {control.printToHoverArea("You do not own this space station."); }
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
			switchColors();
			control.removeAoI();
			repaint(); 
			return;
			}
		case "Upgrade": {
			if(this.owner == control.getPlayer()) {
				switchColors();
				control.removeAoI();
				repaint();
				}
			control.printToHoverArea("Hover over a satellite for more information.");
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
