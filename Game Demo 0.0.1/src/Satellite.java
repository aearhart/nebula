import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Satellite extends JComponent implements MouseListener{

	public Satellite() {

		addMouseListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {

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
	
	public Dimension getPreferredSize() {
		return new Dimension(100, 100);
	}

}
