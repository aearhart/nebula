import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class OurButton extends JComponent implements MouseListener {
	private int width = 125;
	private int height = 50;
	private String str = "";
	private SelectPanel sPanel; 
	
	private int buttonNum;

	private int imageNum = 0;
	private KeyStroke key; 
	
	//                                     active inactive highlighted, invisible
	private BufferedImage[] buttonImages = {null,   null,     null,       null};
	
	/*private BufferedImage activeButton;
	private BufferedImage inactiveButton;
	private BufferedImage highlightedButton;
	private BufferedImage invisibleButton;*/
	
	private Boolean isVisible = true;
	private Boolean isActive = true;
	
	public OurButton(SelectPanel panel, int num, KeyStroke keyStroke, int w, int h) {
		getImages();
		
		buttonNum = num;
		key = keyStroke;

		
		width = w;
		height = h;
		sPanel = panel;
		addMouseListener(this);
	}

	public OurButton(SelectPanel panel, int num, KeyStroke k) {
		getImages();
		
		buttonNum = num;
		key = k;
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
	
	public void action() {
		// The button activates: it completes its command
		if (! (sPanel == null)) {
			if (isActive) // enabled
				sPanel.clicked(buttonNum);
			else if (isVisible) { // disabled
				sPanel.buttonInfo(buttonNum); // display button info
			}
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		action();
	}

	
	@Override
	public void mouseEntered(MouseEvent e) {

		highlight();
	}

	@Override
	public void mouseExited(MouseEvent e) {

		unhighlight();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(str + " Button Pressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println(str + " Button Released");
		
	}

	public KeyStroke getKey() {
		return key;
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
