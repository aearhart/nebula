import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {
	public String name;

	public int numStations = 0;
	public ArrayList<String> stationList = new ArrayList<String>();
	public List<Station> stations = new ArrayList<Station>();
	
	public int numPlanets = 0;
	public int gas = 0;
	public int water = 0;
	public int mineral = 0;
	public ClientController control;
	public Color col = Color.BLUE;
	public int R = 0;
	public int G = 0;
	public int B = 0;
	public String num = "P1";
	
	
	public Player() {
		
	}
	
	public Player(ClientController clientController, String n){
		// making a new player
		num = n;
		this.name = "Player" + n; // name currently can't have spaces
		this.control = clientController;
		if (num.equals("P1")) {
			R = 230;
			G = 140;
			B = 255;
			col = new Color(R, G, B);
		}
		else {
			R = 255;
			G = 215;
			B = 140;
			col = new Color(R, G, B);
		}
		
	}

	public String printState() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("player");
		aList.add(num);
		aList.add(name);
		aList.add(Integer.toString(R));
		aList.add(Integer.toString(G));
		aList.add(Integer.toString(B));
		aList.add(Integer.toString(numStations));
		aList.add(Globals.addDelims(stationList));
		aList.add(Integer.toString(numPlanets));
		aList.add(Integer.toString(gas));
		aList.add(Integer.toString(water));
		aList.add(Integer.toString(mineral));
		
		return Globals.addDelims(aList);
	}
	
	public void update(String str) {
		num = str.substring(0, 2);
		R = Integer.parseInt(str.substring(5, 8));
		G = Integer.parseInt(str.substring(9, 12));
		B = Integer.parseInt(str.substring(13, 16));
		col = new Color(R, G, B);
		numStations = Integer.parseInt(str.substring(17, 19));
		numPlanets = Integer.parseInt(str.substring(20, 22));
		gas = Integer.parseInt(str.substring(23, 26));
		water = Integer.parseInt(str.substring(27, 30));
		mineral = Integer.parseInt(str.substring(31, 34));
		int pos = 35;
		while (str.charAt(pos) != 'n') {
			if (! stationList.contains(str.substring(pos, pos+3))) {
				stationList.add(str.substring(pos, pos+3));
				stations.add(control.getStation(str.substring(pos, pos+3)));
			}
			pos +=4;
		}
		name = str.substring(pos + 1);
		System.out.println("updated player " + printState());
	}
	
	public String getNum() {
		return num;
	}
	
	public String info() {
		String str = name;
		str += ":\nResources: w  g  m\n";
		str += "                    " + water + "  " + gas + "  " + mineral;
		str += "\nStations owned: " + numStations;
		str += "\nPlanets owned: " + numPlanets;
		return str;
	}
	

	
	public void addStationToList(Satellite satellite) {
		stationList.add("s" + padLeft(satellite.getName(), 2));
	}
	
	public void setColor(Color c) {
		col = c;
	}
	
	public Color getColor() {
		return col;
	}
	
	public int getPlanet() {
		return numPlanets;
	}
	
	public void addPlanet() {
		numPlanets++;
	}

	public int getNumStations(){
		return numStations;
	}
	
	public void addStation(Satellite satellite) {
		if (! stationList.contains("s" + satellite.getName())) {
			stations.add((Station) satellite);
			addStationToList(satellite);
			numStations++;
		}
	}
	
	public List<Station> getStations() {
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
