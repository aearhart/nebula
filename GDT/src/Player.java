
public class Player {
	private String name;
	private Station[] stations;
	private int gas = 0;
	private int water = 0;
	private int mineral = 0;
	
	public Player(String n){
		this.name = n;
	}
	
	public void setStations(Station[] st) {
		this.stations = st;
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
