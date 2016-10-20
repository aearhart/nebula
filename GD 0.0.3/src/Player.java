import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Player {
	public String name;

	public int numStations = 0;
	public ArrayList<String> stationList = new ArrayList<String>();
	public List<Station> stations = new ArrayList<Station>();
	
	public int numPlanets = 0;
	public int gas = 0;
	public int water = 0;
	public int mineral = 0;
	public ClientController control = null;
	public ServerController server = null;
	public Color col = Color.BLUE;
	public int R = 0;
	public int G = 0;
	public int B = 0;
	public String num = "P1";
	
	public double eventChance = Globals.baseEventChance;
	
	
	public Player(ServerController svr) { // server
		server = svr;
	}
	
	public Player(ClientController c) {
		control = c;
	}
	
	public Player(ClientController clientController, String n){
		// making a new player
		num = n;
		name = "Player" + n; // name currently can't have @@
		control = clientController;

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
	
	public int update(String[] ary, int i) {
		// player @@ num @@ name @@ R @@ G @@ B @@ numStations @@ stationList @@ numPlanets @@ gas @@ water @@ mineral
		// i         +1     +2     +3   +4    +5     +6             +7             +8          +9     +10     +12
		if (! ary[i++].equals("player"))
			return -1;
		num = ary[i++];
		name = ary[i++];
		R = Integer.parseInt(ary[i++]);
		G = Integer.parseInt(ary[i++]);
		B = Integer.parseInt(ary[i++]);
		col = new Color(R, G, B);
		numStations = Integer.parseInt(ary[i++]);
		for (int j = 0; j < numStations; j++) {
			updateStations(ary[i++]);
		}
		if (numStations == 0) // skip empty section
			i++;
		

		numPlanets = Integer.parseInt(ary[i++]);
		//i++; // skipping this step because updating the planets does this already. TODO I think I'd rather this handle it than planet but this will do for now.
		
		
		gas = Integer.parseInt(ary[i++]);
		water = Integer.parseInt(ary[i++]);
		mineral = Integer.parseInt(ary[i++]);
		
		return i;
	}
	
	
	public void updateStations(String sat) {
		if (! stationList.contains(sat)) {
			stationList.add(sat);
			if (control == null) {
				stations.add(server.getStation(sat));
				return;
			}
			stations.add(control.getStation(sat));
		}
	}
	
	public String getNum() {
		return num;
	}
	
	public String info() {
		String str = name;
		str += ":\nResources: w  g  m\n";
		str += "          " + water + "   " + gas + "   " + mineral;
		str += "\nStations owned: " + numStations;
		str += "\nPlanets owned: " + numPlanets;
		return str;
	}
	

	
	public void addStationToList(Satellite satellite) {
		stationList.add("s" + satellite.getNum());
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
		
		System.out.println("Player " + name + " added a planet. They now have " + numPlanets + " planets.");
	}

	public int getNumStations(){
		return numStations;
	}
	
	public void addStation(Satellite satellite) {
		if (! stationList.contains("s" + satellite.getNum())) {
			stations.add((Station) satellite);
			addStationToList(satellite);
			numStations++;
		}
	}
	
	public Station getBase() {
		if (stations == null) return null;
		return stations.get(0);
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
	
	public void setNum(String n) {
		num = n;
		if (num.equals("P1")) { // eventually could potentially choose colors
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
	
	public void addEventChance(double d) {
		eventChance += d;
	}
}
