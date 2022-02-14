package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import simulator.model.VehicleStatus;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus status;
	private Road road;
	private int location; // comienzo carretera 0
	private int contClass; // entre 0 y 10
	private int contTotal; // cont total vehiculo
	private int disTotal;
	private int junctionInd; // indice del ultimo cruce en el itinerario que haya usado
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws IllegalArgumentException{
		super(id);
		if(maxSpeed <= 0 || contClass < 0 || contClass > 10 || itinerary.size() < 2)
			throw new IllegalArgumentException("ERROR!");
		this.maxSpeed = maxSpeed; 
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary)); 
		this.disTotal = 0;
		this.status = VehicleStatus.PENDING;
		this.junctionInd = 0;
	}
	
	int min(int x, int y) {
		if(x < y) return x;
		else return y;
	}
	
	void setSpeed(int s)  throws IllegalArgumentException{ 
		if(s < 0)
			throw new IllegalArgumentException("ERROR!");
		this.actSpeed = min(s, maxSpeed);
	}
	
	void setContaminationClass(int c)  throws IllegalArgumentException{
		if(c >= 0 && c <= 10) this.contClass = c;
		else throw new IllegalArgumentException("ERROR!");
	}
	
	void moveToNextRoad()  throws IllegalArgumentException{
		if(!VehicleStatus.PENDING.equals(status) && !VehicleStatus.WAITING.equals(status)) 
			throw new IllegalArgumentException("moveToNextRoad: Vehicle");
		
		if(!VehicleStatus.PENDING.equals(status))
			road.exit(this);
		
		try {
			if(junctionInd < itinerary.size()-1) {
				road = 	itinerary.get(junctionInd).roadTo(itinerary.get(junctionInd + 1));		
				road.enter(this);
				junctionInd++;
				status = VehicleStatus.TRAVELING;
			}
			else 
				status = VehicleStatus.ARRIVED;
		}
		catch(IllegalArgumentException ie) {
			System.out.println(ie.getMessage());
		}
	}
	
	void advance(int time) {
		int locIni = location;
		int adv = 0;
		int c;
		
		if(status.equals(VehicleStatus.TRAVELING)) {
			location = min(location + actSpeed, road.getLength());
			adv = location - locIni; disTotal += adv;
			c = adv * contClass; 
			contTotal += c; road.addContamination(c);
			if(location >= road.getLength()) {
				road.getDest().enter(this); //TODO asi o con itinerary / try?
				status = VehicleStatus.WAITING;
			}
		}
		else {
			this.setSpeed(0); //TODO try
		}
	}

	public JSONObject report() {
		JSONObject jo1 = new JSONObject();

		jo1.put("id", this.getId());
		jo1.put("speed", actSpeed);
		jo1.put("distance", disTotal);
		jo1.put("co2", contTotal);
		jo1.put("class", contClass);
		jo1.put("status", status.toString());		
		if(!status.equals(VehicleStatus.PENDING) || !status.equals(VehicleStatus.ARRIVED)) {
			jo1.put("road", road);
			jo1.put("location", location);
		}
		
		return jo1;
	}
	

	public int getLocation() {
		return location;
	}
	
	public int getSpeed() {
		return actSpeed;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getContClass() {
		return contClass;
	}
	
	public VehicleStatus getStatus() {
		return status;
	}
	
	public int getToatalCO2() {
		return contTotal;
	}
	
	public List<Junction> getItinerary(){
		return itinerary;
	}
	
	public Road getRoad() {
		return road;
	}
}