import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class OurButton extends JComponent implements MouseListener{
	int width = 125;
	int height = 75;
	String str;
	SelectPanel2 sPanel; 
	
	Color fill;
	Color border = Color.BLUE;
	Color highlight = Color.CYAN;
	Color base = Color.LIGHT_GRAY;
	
	public OurButton(SelectPanel2 panel, int w, int h, String s) {
		fill = base;
		
		width = w;
		height = h;
		str = s;
		sPanel = panel;
		addMouseListener(this);
	}

	public OurButton(SelectPanel2 panel, String s) {
		fill = base;
		
		str = s;
		sPanel = panel;
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fill);
		g.fillRect(0, 0, width, height);
		g.setColor(border);
		g.drawRect(0, 0, width, height);
		g.drawString(str, 20, 20);
		
	}

	public void setText(String s) {
		str = s;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(str + " Button Clicked");
	}

	public void highlight() {
		fill = highlight;
		repaint();
	}
	
	public void unhighlight() {
		fill = base;
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		highlight();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		unhighlight();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(str + " Button Pressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(str + " Button Released");
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
