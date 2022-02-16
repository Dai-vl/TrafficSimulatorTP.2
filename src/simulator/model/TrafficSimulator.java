package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	private RoadMap roads;
	private List<Event> events;
	private int time;
	
	public TrafficSimulator() {
		events = new SortedArrayList<Event>();
		roads = new RoadMap();
		time = 0;
	}
	
	public void addEvent(Event e) {
		events.add(e); //TODO al ser sortedArrayList ns si esta ya ordenada al a�adirse, el comparador del tiempo esta en la clase event
	}

	public void advance() {
		time += 1;
		
		for(Event e: events) {
			if(e._time == time) {
				e.execute(roads);
				events.remove(e);
			}
		}
		
		for(Junction j: roads.getJunctions()) {
			j.advance(time);
		}
		
		for(Road r: roads.getRoads()) {
			r.advance(time);
		}
	}
	
	public void reset() {
		roads.reset();
		events.clear();
		time = 0;
	}
	
	public JSONObject report() {
		JSONObject jo1 = new JSONObject();	
		jo1.put("time", time);
		jo1.put("state", roads.report());
		return jo1;
	}
}