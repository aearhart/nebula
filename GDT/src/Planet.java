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
	}
	
	public Boolean planetWithinAoI() {
		System.out.println("planet within aoi?");
		Station[] stations = control.getCurrPlayer().getStations();
		for (int i = 0; i < control.getCurrPlayer().getNumStations(); i++) {
			if (control.withinDistance(stations[i], (Satellite)(this))) {
				System.out.println("yes");
				return true;
			}
		}
		System.out.println("no");
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
					// double cost
					costGas += costGas;
					costMetal += costMetal;
					costWater += costWater;
					numOfResources += 3;
					//control.setStatus("Test");
					control.printToInstructionArea(this.getName() + " is now level " + level + ", gives out " + numOfResources + ".");
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
					//control.setStatus("Test");
					control.printToInstructionArea(this.getName() + " is now level " + level + ", gives out " + numOfResources + ".");
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
		System.out.println("mouseEntered");
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
				System.out.println("too far");
			}
			else if (owner != null && owner != control.getCurrPlayer()) {
				System.out.println("already owned");
				control.printToHoverArea("This planet is already owned by " + owner.getName());
			}
			else {
				System.out.println("over here");
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
		System.out.println("mouseExited");
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
