package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	
	private List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws == null)	throw new IllegalArgumentException("ERROR!");
		this.ws = ws;
		}

	@Override
	void execute(RoadMap map) {
		for( int i = 0; i < ws.size(); i++) {
			try {
			map.getRoad(ws.get(i).getFirst()).setWeather(ws.get(i).getSecond());
			}
			catch(IllegalArgumentException ie) {
				System.out.println(ie.getMessage() + " SetWeatherEvent: No Existe Carretera \n");
			}
		}
		
	}


}
