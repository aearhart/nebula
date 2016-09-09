import java.awt.Color;

	public class MineralPlanet extends Planet {

		public MineralPlanet(ClientController ctrl) {
			super(ctrl, 400, 400, 100);
			// TODO Auto-generated constructor stub
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
			this.defineType("mineral");
		}

		public MineralPlanet(ClientController ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
			super(ctrl, locX, locY, sz, numResources);
			// TODO Auto-generated constructor stub
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
			this.defineType("mineral");
		}

		public MineralPlanet(ClientController ctrl, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
			super(ctrl, locX, locY, sz, numResources);
			this.setName("Mineral Planet " + n);
			this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
			this.defineType("mineral");
		}

}

