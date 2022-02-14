package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
		//TODO modificar mapa es asi????
		juncMap.put(j._id, j);
	}
	
	void addRoad(Road r) {
			if(roadMap.containsKey(r._id)) 
				throw new IllegalArgumentException("Not valid junction: in addRoad (ID)");
			//TODO comprobar cruces conectan carreteras existen en el mapa
			if(!juncMap.containsValue(r.getSrc())) {
				throw new IllegalArgumentException("Not valid junction: in addRoad (cruce conecta carretera)");
				//TODO comprobar cruce conecta carretera existe en el mapa creo q no es asi 
			}
		roads.add(r);
		//TODO modificar mapa es asi????
		roadMap.put(r._id, r);
	}
	
	void addVehicle(Vehicle v) {
			if(vehicleMap.containsKey(v._id)) 
				throw new IllegalArgumentException("Not valid junction: in addVehicle (ID)");
			//TODO existen carreteras que conecten los cruces consecutivos de su itinerario
			for(Junction q: v.getItinerary()) {
				if(!juncMap.containsKey(q.getId())) {
					throw new IllegalArgumentException("Not valid junction: in addRoad (cruce conecta carreteraa)");
					//TODO sigo pensando q no es asi :(
				}
			}
		vehicles.add(v);
		//TODO modificar mapa es asi????
		vehicleMap.put(v._id, v);
	}
	
	public Junction getJunction(String id) {
		return juncMap.get(id); // la propia funcion devuelve null si no existe
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
	
	public JSONObject report() {
		JSONObject jo1 = new JSONObject();
		JSONArray ja = new JSONArray();
		for(Junction q: junctions) {
			ja.put(q);
		}		
		jo1.put("junctions", ja);
		
		JSONArray je = new JSONArray();
		for(Road q: roads) {
			je.put(q);
		}		
		jo1.put("road", je);
		
		JSONArray ji = new JSONArray();
		for(Vehicle q: vehicles) {
			ji.put(q);
		}
		//TODO hacer con ja je ji o podria optimizarse?
		jo1.put("vehicles", ji);
		
		return jo1;
	}
}
