import java.awt.Color;

public class Player {
	private String name;
	private Station[] stations = new Station[10];
	private int numStations = 0;
	private int planets = 0;
	private int gas = 0;
	private int water = 0;
	private int mineral = 0;
	private ClientController control;
	private Color col = Color.BLUE;
	private String num = "1";
	
	public Player(ClientController clientController, String n, String no){
		num = no;
		this.name = n;
		this.control = clientController;
		
	}
	
	public String getNum() {
		return num;
	}
	
	public String info() {
		String str = name;
		str += ":\nResources: w  g  m\n";
		str += "                    " + water + "  " + gas + "  " + mineral;
		str += "\nStations owned: " + numStations;
		str += "\nPlanets owned: " + planets;
		return str;
	}
	
	private String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s).replace(' ', '0');
	}
	
	private String padLeft(int s, int n) {
	    return String.format("%1$" + n + "s", Integer.toString(s)).replace(' ', '0');
	}
	
	public String playerState() {
		// P_=s__p__g___w___m___>s__,s__,n______...
		String s = "P" + num;
		s += "s" + padLeft(numStations, 2);
		s += "p" + padLeft(planets, 2);
		s += "g" + padLeft(gas, 3);
		s += "w" + padLeft(water, 3);
		s += "m" + padLeft(mineral, 3);
		s += ">";
		for (int sat = 0; sat < numStations; sat++) {
			s += "s" + padLeft(stations[sat].getName(), 2) + ",";
		}
		s += "n" + name;
		return s;
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
