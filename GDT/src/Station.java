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
	private int costWater = 0;
	private int costMetal = 0;
	private int costGas = 0;
	private int level = 0;
	
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
		control.printToInstructionArea(control.getStatus());
		
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
		System.out.println(control.getStatus());
		switch (control.getStatus()) {
		case "Claiming": {
				if(this.owner == null) 
					setOwner(control.getCurrPlayer()); 
				else 
					control.printToInstructionArea("This station is already owned by " + owner.getName());
				return;
			}
		case "Test": {
			if(this.owner == null) 
			control.printToInstructionArea("Before collecting: w---g---m");
			control.printToInstructionArea("                   " + control.getCurrPlayer().getWater() + "   " + control.getCurrPlayer().getGas() + "   " + control.getCurrPlayer().getMineral());
			control.collectResources();
			control.printToInstructionArea("After  collecting: w---g---m");
			control.printToInstructionArea("                   " + control.getCurrPlayer().getWater() + "   " + control.getCurrPlayer().getGas() + "   " + control.getCurrPlayer().getMineral());
			return;
			}
		case "UpgradeStations": {
			if(this.owner == control.getCurrPlayer()) {
				control.setStatus("SelectStation");
				control.printToInstructionArea("Click again to upgrade the station's AoI by 100.");
				control.printToHoverArea("This level " + level + " " + "station costs : " + costWater + " water, " + costMetal + " metal, and " + costGas + " gas.");
			}
			return;
		}
		case "SelectStation": {
			if (this.owner == control.getCurrPlayer() && control.getCurrPlayer().getGas() >= costGas && control.getCurrPlayer().getMineral() >= costMetal && control.getCurrPlayer().getWater() >= costWater) {
				control.getCurrPlayer().subGas(costGas);
				control.getCurrPlayer().subMineral(costMetal);
				control.getCurrPlayer().subWater(costWater);
				level++;
				// double cost
				costGas += costGas;
				costMetal += costMetal;
				costWater += costWater;
				AreaOfInfluence = AreaOfInfluence * 2;
				control.setStatus("Test");
				control.printToInstructionArea("none");
			}
			else {control.printToInstructionArea("Insufficient funds or priviledges.");}
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
		case "UpgradeStations": {
			if(this.owner == control.getCurrPlayer()) {
				switchColors();
				control.drawAoI(this);
				repaint(); 
				}
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
		case "UpgradeStations": {
			if(this.owner == control.getCurrPlayer()) {
				switchColors();
				control.removeAoI();
				repaint();
			}
			return;
		}
		case "SelectStations": {
			if(this.owner == control.getCurrPlayer()) {
				switchColors();
				control.removeAoI();
				repaint();
			}
			return;
		}}
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
