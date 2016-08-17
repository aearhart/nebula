import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Planet extends Satellite implements MouseListener {

	private Integer numOfResources;
	
	public Planet(Integer locX, Integer locY) {
		super(locX, locY);
		// TODO Auto-generated constructor stub
		numOfResources = 1;
		addMouseListener(this);
	}

	public Planet(Integer locX, Integer locY, Integer numResources) {
		super(locX, locY);
		numOfResources = numResources;
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
