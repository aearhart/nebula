import java.awt.Color;

	public class MineralPlanet extends Planet {

		public MineralPlanet(Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
			super(locX, locY, sz);
			mineralResource = numResources;
			this.setNum(n);
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
			this.setType("M");
		}

		
		public MineralPlanet(ClientController ctrl, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
			super(ctrl, locX, locY, sz);
			mineralResource = numResources;
			this.setNum(n);
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
			this.setType("M");
		}

}

