package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> incomingRoad;
	private Map<Junction, Road> outgoingRoad; // cruce al que va, carretera por la que va
	private List<List<Vehicle>> queue;
	private Map<Road, List<Vehicle>> roadQueue;
	private int currGreen, lastSwitchingTime, xCoor, yCoor;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;

	public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		if (lsStrategy == null)
			throw new IllegalArgumentException("Constructor Junction no valido: lsStrategy es null");
		if (dqStrategy == null)
			throw new IllegalArgumentException("Constructor Junction no valido: dqStratey es null");
		if (xCoor < 0)
			throw new IllegalArgumentException("Constructor Junction no valido: coordenada x negativa");
		if (yCoor < 0)
			throw new IllegalArgumentException("Constructor Junction no valido: coordenada y negativa");

		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		lastSwitchingTime = 0;
		incomingRoad = new ArrayList<>();
		outgoingRoad = new HashMap<>();
		queue = new ArrayList<>();
		roadQueue = new HashMap<>();
		currGreen = -1; // TODO lo he inicializado asi para probar
	}

	public void addIncommingRoad(Road r) throws IllegalArgumentException {
		if (!r.getDest().equals(this))
			throw new IllegalArgumentException("Not valid Road: in addIncommingRoad");

		incomingRoad.add(r);
		List<Vehicle> auxList = new LinkedList<>();
		queue.add(auxList);
		roadQueue.put(r, auxList);
	}

	public void addOutgoingRoad(Road r) throws IllegalArgumentException {
		if (!r.getSrc().equals(this) || outgoingRoad.containsKey(r.getDest()))
			throw new IllegalArgumentException("Not valid Road: in addOutgoingRoad");

		outgoingRoad.put(r.getDest(), r);
	}

	void enter(Vehicle v) {
		v.getRoad().enter(v);
	}

	public Road roadTo(Junction j) {
		return outgoingRoad.get(j);
	}

	void advance(int time) {
		List<Vehicle> movingVehicles = new ArrayList<>();
		for (int i = 0; i < queue.size(); i++) {
			movingVehicles = dqStrategy.dequeue(queue.get(i));

			for (int j = 0; j < movingVehicles.size(); j++) {
				movingVehicles.get(j).moveToNextRoad();
			}

			queue.get(i).removeAll(movingVehicles); // TODO revisar
			roadQueue.replace(incomingRoad.get(i), queue.get(i));
		}

		int nextRoad = lsStrategy.chooseNextGreen(incomingRoad, queue, currGreen, lastSwitchingTime, time);
		if (nextRoad != currGreen) {
			currGreen = nextRoad;
			lastSwitchingTime = time;
		}
	}

	public JSONObject report() {
		JSONObject jo1 = new JSONObject();
		JSONArray ja = new JSONArray();

		jo1.put("id", this.getId());
		if (currGreen != -1)
			jo1.put("green", incomingRoad.get(currGreen));
		else
			jo1.put("green", "none");

		for (int j = 0; j < queue.size(); j++) { // TODO revisar
			JSONObject jo2 = new JSONObject();
			JSONObject jo3 = new JSONObject();
			JSONArray ja2 = new JSONArray(queue.get(j));

			jo2.put("road", incomingRoad.get(j)); // se supone que tienen q coincidir?
			jo2.put("vehicles", ja2);
			ja.put(jo2);
			// jo3.put("vehicles", ja2);
			// ja.put(jo3);

			/*
			 * for (int i = 0; i < queue.get(i).size(); i++) { ja2.put(queue.get(i)); }
			 */

		}

		jo1.put("queues", ja);
		return jo1;
	}

}