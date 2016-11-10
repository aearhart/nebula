import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JComponent;
 
public class Globals {
	public static String delim = "@@";
	public static int winSize = 1000;
	public static double baseEventChance = 1.0;
	public static Font f = new Font("Consolas", Font.PLAIN, 22);
	public static Color textColor = new Color(99, 234, 237, 255);
	public static final int IFW  = JComponent.WHEN_IN_FOCUSED_WINDOW;
	
	public static Color backgroundColor = new Color(31, 106, 119, 127);
	public static void setWinSize() {
		// set winSize according to size of monitor
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		if (width > height) winSize = (int) height;
		else winSize = (int) width;
		System.out.println(".......................WINSIZE = " + winSize);
	}
	
	public static String addDelims(ArrayList<String> s) {
		if (s.size() == 0)
			return "";
		String result = s.get(0);
		for (int i = 1; i < s.size(); i++) {
			result += delim + s.get(i);
		}
		return result;
		
	}
	
	public static Boolean playerWin(Player p) {
		// return whether or not player p has own
		
		/* win conditions:
		 * 
		 * gas, water, mineral > 40
		 * numPlanets > 2
		 * 
		 */
		
		return (p.gas >= 40 && p.water >= 40 && p.mineral >= 40 && p.numPlanets >= 1);
	}
}
