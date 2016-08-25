import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Planet extends Satellite implements MouseListener {

	private Integer numOfResources;
	private Player owner = null;
	private Controller control;
	private int costWater = 0;
	private int costMetal = 0;
	private int costGas = 0;
	private int level = 0;
	private String t = "";
	private Color ownerColor;
	
	public Planet(Controller ctrl, Integer locX, Integer locY, Integer sz) {
		super(ctrl, locX, locY, sz);
		// TODO Auto-generated constructor stub
		numOfResources = 1;
		control = ctrl;
		addMouseListener(this);
	}

	public Planet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz);
		control = ctrl;
		numOfResources = numResources;
		addMouseListener(this); 
	}

	public void setOwnerColor(Color col) {
		ownerColor = col;
	}
	
	public void defineType(String str) {
		t = str;
	}
	
	public String getType() {
		return t;
	}
	
	public Integer getResources(){
		return numOfResources;
	}
	
	public void setOwner(Player p) {
		owner = p;
	}
	
	public Player getOwner() {
		return owner;	
	}
	
	public String info() {
		String str;
		if (owner == null) 
			str = getName() + " not currently owned.";
		else
			str = getName() + " currently owned by " + owner.getName();
		str += "Level " + level + "\n" + "Resources: " + numOfResources + "\n costs: " + costWater + " water, " + costMetal + " metal, " + costGas + " gas.";
		return str;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawOval(0, 0, s, s);
		if (owner != null) {
			g.setColor(ownerColor);
			g.fillOval((s/2)-5, (s/2)-5, 10, 10);
		}
	}
	
	public Boolean planetWithinAoI() {
		/* check to see if planet is within the current player's AoI */
		Station[] stations = control.getCurrPlayer().getStations();
		for (int i = 0; i < control.getCurrPlayer().getNumStations(); i++) {
			if (control.withinDistance(stations[i], (Satellite)(this))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (control.getStatus()) {
		case "Claiming": {
			control.printToHoverArea(info());
			}
		case "Test": {
			control.printToHoverArea(info());
			}
		case "Upgrade": {
			// within AoI
			if (planetWithinAoI()) {
				// not owned
				if (this.owner == null && control.getCurrPlayer().getGas() >= costGas && control.getCurrPlayer().getMineral() >= costMetal && control.getCurrPlayer().getWater() >= costWater) {
					control.getCurrPlayer().subGas(costGas);
					control.getCurrPlayer().subMineral(costMetal);
					control.getCurrPlayer().subWater(costWater);
					level++;
					owner = control.getCurrPlayer();
					control.getCurrPlayer().addPlanet();
					ownerColor = control.getCurrPlayer().getColor();
					repaint();
					// double cost
					costGas += costGas;
					costMetal += costMetal;
					costWater += costWater;
					numOfResources += 3;
					control.setStatus("collectResources");
					control.printToInstructionArea(this.getName() + " is now level " + level + ", gives out " + numOfResources + ".");
					control.update();
				}
				/*else if (this.owner != control.getCurrPlayer()) {
					control.printToHoverArea("This planet is already owned by + " owner.getName());
				}*/
				else if (this.owner == control.getCurrPlayer() && control.getCurrPlayer().getGas() >= costGas && control.getCurrPlayer().getMineral() >= costMetal && control.getCurrPlayer().getWater() >= costWater) {
					// owned, upgrading.
					control.getCurrPlayer().subGas(costGas);
					control.getCurrPlayer().subMineral(costMetal);
					control.getCurrPlayer().subWater(costWater);
					level++;
					owner = control.getCurrPlayer();
					// double cost
					costGas += costGas;
					costMetal += costMetal;
					costWater += costWater;
					numOfResources += 3;
					control.setStatus("collectResources");
					control.printToInstructionArea(this.getName() + " is now level " + level + ", gives out " + numOfResources + ".");
					control.update();
				}
				else {control.printToInstructionArea("Insufficient funds or priviledges.");}

				return;
			}
		}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		switchColors();
		repaint();
		switch (control.getStatus()) {
		case "Claiming": {
			control.printToHoverArea(info());
			return;
			}
		case "Test": {
			control.printToHoverArea(info());
			return;
			}
		case "Upgrade": {
			if(planetWithinAoI() == false) {
				control.printToHoverArea("This planet is too far away to build on.");
			}
			else if (owner != null && owner != control.getCurrPlayer()) {
				control.printToHoverArea("This planet is already owned by " + owner.getName());
			}
			else {
				control.printToHoverArea(info());
			}
			return;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		switchColors();
		repaint();
		switch (control.getStatus()) {
		case "Claiming": {
			control.printToHoverArea("Hover over a satellite for more info.");
			return;
			}
		case "Test": {
			control.printToHoverArea("Hover over a satellite for more info.");
			return;
			}
		case "Upgrade": {
			control.printToHoverArea("Hover over a satellite for more info.");
			return;
			}
		}
	}

}
