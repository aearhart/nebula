import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Planet extends Satellite implements MouseListener {

	private int costWater = 3;
	private int costMetal = 3;
	private int costGas = 3;
	private int level = 0;

	
	public Planet(ClientController ctrl, Integer locX, Integer locY, Integer sz) {
		super(ctrl, locX, locY, sz);
		// TODO Auto-generated constructor stub
		this.setResource(1);
		control = ctrl;
		addMouseListener(this);
	}

	public Planet(ClientController clientController, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(clientController, locX, locY, sz);
		control = clientController;
		this.setResource(numResources);
		addMouseListener(this); 
	}

	public void setOwnerColor(Color col) {
		ownerColor = col;
	}
	
	public String getType() {
		return t;
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
		str += "Level " + level + "\n" + "Resources: " + this.getResources() + "\n costs: " + costWater + " water, " + costMetal + " metal, " + costGas + " gas.";
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
		for (Station sat: control.getPlayer().getStations()) {
			if (control.withinDistance(sat, (Satellite)(this))) {
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
				if (this.owner == null && control.getPlayer().getGas() >= costGas && control.getPlayer().getMineral() >= costMetal && control.getPlayer().getWater() >= costWater) {
					control.getPlayer().subGas(costGas);
					control.getPlayer().subMineral(costMetal);
					control.getPlayer().subWater(costWater);
					level++;
					setOwner(control.getPlayer());
					//owner = control.getPlayer();
					//control.getPlayer().addPlanet();
					//ownerColor = control.getPlayer().getColor();
					repaint();
					// double cost
					costGas += costGas;
					costMetal += costMetal;
					costWater += costWater;
					this.addResources(3);;
					control.setStatus("collectResources");
					control.printToInstructionArea(this.getName() + " is now level " + level + ", gives out " + this.getResources() + ".");
					control.update();
				}
				/*else if (this.owner != control.getPlayer()) {
					control.printToHoverArea("This planet is already owned by + " owner.getName());
				}*/
				else if (this.owner == control.getPlayer() && control.getPlayer().getGas() >= costGas && control.getPlayer().getMineral() >= costMetal && control.getPlayer().getWater() >= costWater) {
					// owned, upgrading.
					control.getPlayer().subGas(costGas);
					control.getPlayer().subMineral(costMetal);
					control.getPlayer().subWater(costWater);
					level++;
					//owner = control.getPlayer();
					// double cost
					costGas += costGas;
					costMetal += costMetal;
					costWater += costWater;
					this.addResources(3);
					control.setStatus("collectResources");
					control.printToInstructionArea(this.getName() + " is now level " + level + ", gives out " + this.getResources() + ".");
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
			else if (owner != null && owner != control.getPlayer()) {
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
