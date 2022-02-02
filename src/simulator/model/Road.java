package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	private int length;
	private Junction srcJunc;
	private Junction destJunc;
	private int maxSpeed;
	private int actLimSpeed;
	private int contLimit;
	private Weather weather;
	private int conTotal; //Carretera
	private List<Vehicle> vehicles; //ordenada por location del vehiculo (descendente)

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if(maxSpeed <= 0 || contLimit < 0 || length <= 0 || srcJunc != null || destJunc != null || weather != null)
			throw new IllegalArgumentException("Constructor Road no v�lido"); 
		this.srcJunc = srcJunc; 
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.length = length;
		this.weather = weather;
		this.contLimit = contLimit;	
		vehicles = new ArrayList<>();
	}

	void enter(Vehicle v) {
		if(v.getSpeed() == 0 && v.getLocation() == 0) vehicles.add(v);
		else throw new IllegalArgumentException("Invalid Vehicle"); //la he cambiado a esta
	}
	
	void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w != null) weather = w;
		else throw new NullPointerException("Invalid weather");
	}
	
	void addContamination(int c) {
		if (c >= 0) conTotal += c;
		else throw new IllegalArgumentException("c must be positive");
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v: vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		//TODO Recuerda ordenar la lista de veh�culos por su localizaci�n al final del m�todo
	}

	public JSONObject report() {
		JSONObject jo1 = new JSONObject();

		jo1.put("id", this.getId());
		jo1.put("speedlimit", actLimSpeed);
		jo1.put("weather", weather);
		jo1.put("co2", conTotal);
		JSONArray ja = new JSONArray();
		for(Vehicle v: vehicles) {
			ja.put(v.getId());
		}
		jo1.put("vehicles", ja);
		
		return jo1;
	}

	public int getLength() {
		return length;
	}
	
	public Junction getDest() {
		return destJunc;
	}
	
	public Junction getSrc() {
		return srcJunc;
	}
	
	public Weather getWeather() {
		return weather;
	}
	
	public int getContLimit() {
		return contLimit;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getTotalCO2() {
		return conTotal;
	}
	
	public void setTotalCO2(int c) throws IllegalArgumentException{ //TODO PUBLIC?
		if( c < 0)
			throw new IllegalArgumentException("Negative total contamination in Road");
		conTotal = c;
	}
	
	public int getSpeedLimit() {
		return actLimSpeed;
	}
	
	public void setSpeedLimit(int l) {//TODO public?
		actLimSpeed = l;
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
}
