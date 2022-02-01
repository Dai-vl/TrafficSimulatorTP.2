package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus state;
	private Road road;
	private int location; // comienzo carretera 0
	private int contClass; // entre 0 y 10
	private int contTotal; 
	private int disTotal;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		this.maxSpeed = maxSpeed; // comprobar si es positivo
		this.contClass = contClass; // comprobar si esta entre 0 y 10
		this.itinerary = itinerary; // comprobar si es minimo 2
		Collections.unmodifiableList(new ArrayList<>(itinerary)); // copia

	}
	
	int min(int x, int y) {
		if(x < y) return x;
		else return y;
	}
	
	void setSpeed(int s) { 
		this.actSpeed = min(s, maxSpeed);
	}
	
	void setContaminationClass(int c) {
		if(c >= 0 && c <= 10) this.contClass = c;
		else throw new IllegalArgumentException("ERROR!");
	}
	
	@Override
	void advance(int time) {

		if(state == VehicleStatus.TRAVELING) {
			location = min(location + actSpeed, road.getLength());
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
