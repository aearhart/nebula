
public class Player {
	private String name;
	private Base[] bases;
	private int gas;
	private int water;
	private int mineral;
	
	public Player(String n){
		this.name = n;
	}
	
	public void setBases(Base[] bs) {
		this.bases = bs;
	}
	
	public Base[] getBases() {
		return bases;
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

	public void setWater(int water) {
		this.water = water;
	}

	public int getMineral() {
		return mineral;
	}

	public void setMineral(int mineral) {
		this.mineral = mineral;
	}
	
	public int getGas(){
		return gas;
	}

	public void setGas(int gas) {
		this.gas = gas;
	}
	
	
}
