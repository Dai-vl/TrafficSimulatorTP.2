package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

	private RoadMap roads;
	private List<Event> events;
	private int time;

	public TrafficSimulator() {
		events = new SortedArrayList<Event>();
		roads = new RoadMap();
		time = 0;
	}

	public void addEvent(Event e) {
		events.add(e);
		onEventAdded();
	}

	private void onEventAdded() {
		// TODO Auto-generated method stub

	}

	public void advance() {
		time += 1;

		onAdvanceStart();

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

		onAdvanceEnd();
	}

	private void onAdvanceEnd() {

	}

	private void onAdvanceStart() {

	}

	public void reset() {
		roads.reset();
		events.clear();
		time = 0;
		onReset();
	}

	private void onReset() {
		// TODO Auto-generated method stub

	}

	public JSONObject report() {
		JSONObject jo1 = new JSONObject();
		jo1.put("time", time);
		jo1.put("state", roads.report());
		return jo1;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		onRegister();
	}

	private void onRegister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeObserver(TrafficSimObserver o) {

	}

	void onError() {

	}
}
