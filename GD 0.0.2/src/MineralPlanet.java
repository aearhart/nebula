import java.awt.Color;

	public class MineralPlanet extends Planet {

		public MineralPlanet(ClientController ctrl, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
			super(ctrl, locX, locY, sz, numResources);
			this.setName(n);
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
			this.setType("M");
		}

}

