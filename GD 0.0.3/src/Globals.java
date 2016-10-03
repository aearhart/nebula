import java.awt.List;
import java.util.ArrayList;
 
public class Globals {
	public static String delim = "@@";
	
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
		 * gas, water, mineral > 10
		 * numPlanets > 2
		 * 
		 */
		
		return (p.gas > 10 && p.water > 10 && p.mineral > 10);// && p.numPlanets > 2);
	}
}
