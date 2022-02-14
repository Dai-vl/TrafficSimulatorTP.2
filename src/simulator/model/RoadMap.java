package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
	
	private List<Junction> junctions;
	private List<Road> roads;
	private List<Vehicle> vehicles;
	private Map<String,Junction> juncMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
	RoadMap(){
		junctions = new ArrayList<>();
		roads = new ArrayList<>();
		vehicles = new ArrayList<>();
		juncMap = new HashMap<>();
		roadMap = new HashMap<>();
		vehicleMap = new HashMap<>();
	}
	
	void addJunction(Junction j) {
			if(juncMap.containsKey(j._id))
				throw new IllegalArgumentException("Not valid junction: in addJunction (ID)");
		junctions.add(j);
		//TODO modificar mapa
	}
	
	void addRoad(Road r) {
			if(roadMap.containsKey(r._id)) 
				throw new IllegalArgumentException("Not valid junction: in addRoad (ID)");
			//TODO comprobar cruces conectan carreteras existen en el mapa
		roads.add(r);
		//TODO modificar mapa
	}
	
	void addVehicle(Vehicle v) {
			if(vehicleMap.containsKey(v._id)) 
				throw new IllegalArgumentException("Not valid junction: in addVehicle (ID)");
			//TODO existen carreteras que conecten los cruces consecutivos de su itinerario
		vehicles.add(v);
		//TODO modificar mapa
	}
	
	public Junction getJunction(String id) {
		return juncMap.get(id);
	}
	
	public Road getRoad(String id) {
		return roadMap.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		return vehicleMap.get(id);
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(junctions);
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(roads);
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
	
	void reset() { // me ha dicho el que usemos el clear
		junctions.clear();
		vehicles.clear();
		roads.clear();
		juncMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
}
