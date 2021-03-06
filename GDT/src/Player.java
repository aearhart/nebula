import java.awt.Color;

public class Player {
	private String name;
	private Station[] stations = new Station[10];
	private int numStations = 0;
	private int planets = 0;
	private int gas = 0;
	private int water = 0;
	private int mineral = 0;
	private Controller control;
	private Color col = Color.BLUE;
	
	public Player(Controller ctrl, String n){
		this.name = n;
		this.control = ctrl;
		
	}
	
	public String info() {
		String str = name;
		str += ":\nResources: w  g  m\n";
		str += "                    " + water + "  " + gas + "  " + mineral;
		str += "\nStations owned: " + numStations;
		str += "\nPlanets owned: " + planets;
		return str;
	}
	
	public void setColor(Color c) {
		col = c;
	}
	
	public Color getColor() {
		return col;
	}
	
	public void addPlanet() {
		planets++;
	}
	
	public int getNumStations(){
		return numStations;
	}
	
	public void addStation(Station st) {
		stations[numStations++] = st;
	}
	
	public Station[] getStations() {
		return stations;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWater() { 
		return water;
	}

	public void addWater(int water) {
		this.water += water;
	}
	
	public void subWater(int water) { // do we want this to return -1 if water <= 0?
		this.water -= water;
	}

	public int getMineral() {
		return mineral;
	}

	public void addMineral(int mineral) {
		this.mineral += mineral;
	}
	
	public void subMineral(int mineral) {
		this.mineral -= mineral;
	}
	
	public int getGas(){
		return gas;
	}

	public void addGas(int gas) {
		this.gas += gas;
	}
	
	public void subGas(int gas) {
		this.gas -= gas;
	}
	
}
