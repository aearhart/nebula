import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class OurButton extends JComponent implements MouseListener{
	private int width = 125;
	private int height = 50;
	private String str = "";
	private SelectPanel2 sPanel; 
	
	private int buttonNum;

	private int imageNum = 0;
	
	//                                     active inactive highlighted, invisible
	private BufferedImage[] buttonImages = {null,   null,     null,       null};
	
	/*private BufferedImage activeButton;
	private BufferedImage inactiveButton;
	private BufferedImage highlightedButton;
	private BufferedImage invisibleButton;*/
	
	private Boolean isVisible = true;
	private Boolean isActive = true;
	
	public OurButton(SelectPanel2 panel, int num, int w, int h) {
		getImages();
		
		buttonNum = num;
		
		width = w;
		height = h;
		sPanel = panel;
		addMouseListener(this);
	}

	public OurButton(SelectPanel2 panel, int num) {
		getImages();
		
		buttonNum = num;

		sPanel = panel;
		addMouseListener(this);
	}
	
	private void getImages() {
		try {                
			buttonImages[0] = ImageIO.read(getClass().getResource("activeButton.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		try {                
			buttonImages[1] = ImageIO.read(getClass().getResource("inactiveButton.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		try {                
			buttonImages[2] = ImageIO.read(getClass().getResource("highlightedButton.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		try {                
			buttonImages[3] = ImageIO.read(getClass().getResource("invisibleButton.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		g.drawImage(buttonImages[imageNum], 0, 0, width, height, null);
		
		if(isVisible) {
			g.setColor(Color.BLACK);
			g.drawString(str, 20, 20);
		}
		
	}

	public void setText(String s) {
		str = s;
	}

	public void highlight() {
		if(isActive) {
			imageNum = 2;
			repaint();
		}
	}
	
	public void unhighlight() {
		if(isActive) {
			imageNum = 0;
			repaint();
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(str + " Button Clicked");
		//sPanel.clicked(buttonNum);
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
		//System.out.println(str + " Button Pressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(str + " Button Released");
		
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

	public void enable() {
		isActive = true;
		isVisible = true;
		imageNum = 0;
		repaint();
	}
	
	public void disable() {
		isActive = false;
		isVisible = true;
		imageNum = 1;
		repaint();
		
	}

	public void remove() {
		isActive = false;
		isVisible = false;
		imageNum = 3;
		repaint();
		
	}
	
}
