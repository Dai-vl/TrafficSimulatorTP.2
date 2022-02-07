package simulator.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	private List<Road> incomingRoad;
	private Map<Junction,Road> outgoingRoad; //cruce al que va, carreterapor la que va
	private List<List<Vehicle>> queue;
	private Map<Road,List<Vehicle>> roadQueue;
	private int currGreen;
	private int lastSwitchingTime;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {	
		super(id);
		
		if(lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0) {
			throw new IllegalArgumentException("Constructor Junction no v�lido"); 
		}
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		lastSwitchingTime = 0;
	}

	void addIncommingRoad(Road r) throws IllegalArgumentException{
		if(!r.getDest().equals(this))
			throw new IllegalArgumentException("Not valid Road: in addIncommingRoad");
		
		incomingRoad.add(r);
		List<Vehicle> auxList =  new LinkedList<>(r.getVehicles()); //TODO ?
		//auxList = r.getVehicles();
		queue.add(auxList);
		roadQueue.put(r, auxList);
	}
	
	void addOutgoingRoad(Road r) throws IllegalArgumentException{
		if(!r.getSrc().equals(this) || outgoingRoad.containsKey(r.getDest()))
			throw new IllegalArgumentException("Not valid Road: in addOutgoingRoad");		
		
		outgoingRoad.put(r.getDest(), r);		
	}
	
	void enter(Vehicle v) {
		try {
			v.getRoad().enter(v);
		} catch(IllegalArgumentException ie) {
			System.out.println(ie.getMessage() + " Junction: enter \n");
		}
	}
	
	public Road roadTo(Junction j) {
		return outgoingRoad.get(j);
	}
	
	void advance(int time) {
		List<Vehicle> movingVehicles = new ArrayList<>();
		for(int i = 0; i < queue.size(); i++) {
			dqStrategy.dequeue(queue.get(i));
			//TODO
		}
			

		
		int nextRoad = lsStrategy.chooseNextGreen(incomingRoad, queue, currGreen, lastSwitchingTime, time);
		if(nextRoad != currGreen) {
			currGreen = nextRoad;
			lastSwitchingTime = time;
		}
	}

	public JSONObject report() {
		return null;
	}

}