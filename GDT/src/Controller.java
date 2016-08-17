
public class Controller {

	public static void main(String[] args) {
		
		
		Window window = new Window();
		Map map = new Map();
		window.add(map);
		window.update();

		// ??Planet(x, y, s, numResources, name)
		Satellite s01 = new WaterPlanet(450, 400, 30, 3, "0");  
		Satellite s02 = new MetalPlanet(800, 104, 40, 5, "1"); 
		Satellite s03 = new GasPlanet(645, 40, 26, 5, "2"); 
		Satellite s04 = new WaterPlanet(766, 388, 58, 2, "3"); 
		
		Satellite[] all = {s01, s02, s03, s04};
		
		for (int i = 0; i < all.length; i++) {
			Satellite s = all[i];
			map.add(s);
			s.setBounds(s.getLocX(), s.getLocY(), s.getSz(), s.getSz());
		}

	}

}
