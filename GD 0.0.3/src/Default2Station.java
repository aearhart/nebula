
public class Default2Station extends Station{

	public Default2Station(Integer locX, Integer locY, Integer sz, String n) {
		super(locX, locY, sz, n);

		// to create planets
		gasAccess = 1;
		mineralAccess = 3;
		waterAccess = 2;
		gasSector = 4;
		mineralSector = 6;
		waterSector = 5;
		
		// to affect station
		gasGenerated = 5;
		mineralGenerated = 5;
		waterGenerated = 5;
		upgradeCost = 1;
		
		// to affect player
		gasStart = 8;
		mineralStart = 8;
		waterStart = 8;
		
		
		// old stuff
		costGas = 15;
		costMineral = 15;
		costWater = 15;
		
		setup();
		createPlanets();
		

	}

	public Default2Station(ClientController clientController, Integer locX, Integer locY, Integer sz, String n) {
		super(clientController, locX, locY, sz, n);


		// to create planets
		gasAccess = 1;
		mineralAccess = 3;
		waterAccess = 2;
		gasSector = 4;
		mineralSector = 6;
		waterSector = 5;
		
		// to affect station
		gasGenerated = 5;
		mineralGenerated = 5;
		waterGenerated = 5;
		upgradeCost = 1;
		
		// to affect player
		gasStart = 8;
		mineralStart = 8;
		waterStart = 8;
		
		
		// old stuff
		costGas = 15;
		costMineral = 15;
		costWater = 15;
		
		setup();
		createPlanets();
		

	}

}
