
public class Controller {

	public static void main(String[] args) {
		
		
		Window window = new Window();
		Map map = new Map();
		window.add(map);
		window.update();

		
		Satellite s01 = new Satellite(450, 400); // sun 
		Satellite s02 = new Satellite(1, 10, 40, 30); // water
		Satellite s03 = new Satellite(2, 40, 600, 70); // gas
		Satellite s04 = new Satellite(3, 700, 58, 47); // metal
		
		Satellite[] all = {s01, s02, s03, s04};
		
		for (int i = 0; i < all.length; i++) {
			Satellite s = all[i];
			map.add(s);
			s.setBounds(s.getLocX(), s.getLocY(), s.getSz(), s.getSz());
		}
		
		
		// whatevs
	}

}
