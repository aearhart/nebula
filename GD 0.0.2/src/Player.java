import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {
	public String name;

	public int numStations = 0;
	public List<String> stationList = new ArrayList<String>();
	public List<Station> stations = new ArrayList<Station>();
	
	public int planets = 0;
	public int gas = 0;
	public int water = 0;
	public int mineral = 0;
	public ClientController control;
	public Color col = Color.BLUE;
	public int R = 0;
	public int G = 0;
	public int B = 0;
	public String num = "1";
	
	
	public Player(ClientController clientController, String n, String no){
		// making a new player
		num = no;
		this.name = "Player" + no; // name currently can't have spaces
		this.control = clientController;
		if (num.equals("1")) {
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
	
	public Player(ClientController clientController, String str) {
		// making an already created player
		
		// P_=cR___G___B___s__p__gas___w___mineral___>s__,s__,n______...
		// 0   4   8  12   16  19  22   26   30      34 ....,
		
		control = clientController;
		num = str.substring(1,2);
		R = Integer.parseInt(str.substring(5, 8));
		G = Integer.parseInt(str.substring(9, 12));
		B = Integer.parseInt(str.substring(13, 16));
		col = new Color(R, G, B);
		numStations = Integer.parseInt(str.substring(17, 19));
		planets = Integer.parseInt(str.substring(20, 22));
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
		
	}
	
	public void update(String str) {
		System.out.println("Player before update: " + playerState());
		num = str.substring(1, 2);
		R = Integer.parseInt(str.substring(5, 8));
		G = Integer.parseInt(str.substring(9, 12));
		B = Integer.parseInt(str.substring(13, 16));
		col = new Color(R, G, B);
		numStations = Integer.parseInt(str.substring(17, 19));
		planets = Integer.parseInt(str.substring(20, 22));
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
		System.out.println("after update       " + playerState());
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
	
	public String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s).replace(' ', '0');
	}
	
	public String padLeft(int s, int n) {
	    return String.format("%1$" + n + "s", Integer.toString(s)).replace(' ', '0');
	}
	
	public String playerState() {
		// P_=cR___G___B___s__p__g___w___m___>s__,s__,n______...
		String s = "P" + num;
		s += "=cR" + padLeft(R, 3);
		s += "G" + padLeft(G, 3);
		s += "B" + padLeft(B, 3);
		
		s += "s" + padLeft(numStations, 2);
		s += "p" + padLeft(planets, 2);
		s += "g" + padLeft(gas, 3);
		s += "w" + padLeft(water, 3);
		s += "m" + padLeft(mineral, 3);
		s += ">";
		for (String sat: stationList) {
			s += sat + ",";
		}
		s += "n" + name + " ";
		return s;
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
		return planets;
	}
	
	public void addPlanet() {
		planets++;
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
