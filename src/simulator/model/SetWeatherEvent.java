package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if (ws == null)
			throw new IllegalArgumentException("ERROR en SetWeatherEvent constructor: ws es null");
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Weather> p : ws) {
			try {
				map.getRoad(p.getFirst()).setWeather(p.getSecond());
			} catch (IllegalArgumentException ie) {
				throw new IllegalArgumentException(ie.getMessage() + " SetWeatherEvent: No Existe Carretera \n");
			}
		}

	}

	@Override
	public String toString() {
		return "Change weather road: " + ws.get(0).getFirst().toString() + ' ' + ws.get(0).getSecond().toString();
		// TODO pasar el array entero (en set cont class tb)
	}

}
