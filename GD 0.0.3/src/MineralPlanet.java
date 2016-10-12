import java.awt.Color;

	public class MineralPlanet extends Planet {

		public MineralPlanet(Integer locX, Integer locY, String sz, String n) {
			super(locX, locY, sz, "M");
			//mineralResource = numResources;
			this.setNum(n);
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
		}

		
		public MineralPlanet(ClientController ctrl, Integer locX, Integer locY, String sz, String n) {
			super(ctrl, locX, locY, sz, "M");
			this.setNum(n);
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
		}

}

