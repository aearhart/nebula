import java.awt.Color;

public class DefaultStation extends Station {

	public DefaultStation(Integer locX, Integer locY, Integer sz, String n) {
		super(locX, locY, sz, n);

		// to create planets
		gasAccess = 3;
		mineralAccess = 3;
		waterAccess = 3;
		gasSector = 5;
		mineralSector = 5;
		waterSector = 5;
		
		// to affect station
		gasGenerated = 5;
		mineralGenerated = 5;
		waterGenerated = 5;
		upgradeCost = 5;
		
		// to affect player
		gasStart = 5;
		mineralStart = 5;
		waterStart = 5;
		
		
		// old stuff
		costGas = 15;
		costMineral = 15;
		costWater = 15;
		
		setup();
		createPlanets();
		

	}

	public DefaultStation(ClientController clientController, Integer locX, Integer locY, Integer sz, String n) {
		super(clientController, locX, locY, sz, n);

		// to create planets
		gasAccess = 3;
		mineralAccess = 3;
		waterAccess = 3;
		gasSector = 5;
		mineralSector = 5;
		waterSector = 5;
		
		// to affect station
		gasGenerated = 3;
		mineralGenerated = 3;
		waterGenerated = 3;
		upgradeCost = 1.5;
		
		// to affect player
		gasStart = 5;
		mineralStart = 5;
		waterStart = 5;
		
		
		// old stuff
		costGas = 15;
		costMineral = 15;
		costWater = 15;
		
		setup();
		createPlanets();
	}

}
