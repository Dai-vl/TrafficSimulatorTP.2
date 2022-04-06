package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

	private RoadMap roads;
	private List<Event> events;
	private int time;
	private List<TrafficSimObserver> observers;

	public TrafficSimulator() {
		events = new SortedArrayList<Event>();
		roads = new RoadMap();
		time = 0;
		observers = new ArrayList<TrafficSimObserver>();
	}

	public void addEvent(Event e) {
		events.add(e);

		for (TrafficSimObserver t : observers) {
			t.onEventAdded(roads, events, e, time);
		}
	}

	public void advance() {
		time += 1;

		for (TrafficSimObserver t : observers) {
			t.onAdvanceStart(roads, events, time);
		}

		List<Event> toRemove = new SortedArrayList<Event>();

		for (Event e : events) {
			if (e._time == time) {
				e.execute(roads);
				toRemove.add(e);
			}
		}

		events.removeAll(toRemove);

		for (Junction j : roads.getJunctions()) {
			j.advance(time);
		}

		for (Road r : roads.getRoads()) {
			r.advance(time);
		}

		for (TrafficSimObserver t : observers) {
			t.onAdvanceEnd(roads, events, time);
		}
	}

	public void reset() {
		roads.reset();
		events.clear();
		time = 0;
		for (TrafficSimObserver t : observers) {
			t.onReset(roads, events, time);
		}
	}

	public JSONObject report() {
		JSONObject jo1 = new JSONObject();
		jo1.put("time", time);
		jo1.put("state", roads.report());
		return jo1;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		observers.add(o);
		for (TrafficSimObserver t : observers) {
			t.onRegister(roads, events, time);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		observers.remove(o);
	}

	public int getTime() {
		return time;
	}
}
