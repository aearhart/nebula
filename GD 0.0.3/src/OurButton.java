import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Button extends JComponent implements MouseListener{
	int x;
	int y;
	int width;
	int height;
	String str;
	ClientController control; 
	
	Color border = Color.BLUE;
	Color highlight = Color.CYAN;
	Color fill = Color.LIGHT_GRAY;
	
	public Button(ClientController clientControl, int locX, int locY, String s) {
		x = locX; y = locY;
		str = s;
		width = 100; height = 50;
		control = clientControl;
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fill);
		g.fillRect(x, y, width, height);
		g.setColor(border);
		g.drawRect(x, y, width, height);
		g.drawString(str, x+20, y+20);
		
	}

	public void setText(String s) {
		str = s;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (control.chatEnabled) {
			// turn chat off
			str = "Enable chat";
			control.getChatbox().turnOff("Chat disabled. Go to settings to enable.");
		}
		else { // turn chat on
			str = "Disable chat";
			control.getChatbox().turnOn();
		}
	}

	public void switchBorderColors() {
		Color temp = fill;
		fill = highlight;
		highlight = fill;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		switchBorderColors();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		switchBorderColors();
		repaint(); 
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
