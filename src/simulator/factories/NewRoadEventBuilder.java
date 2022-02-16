package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Road;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{
	
	protected int time;
	protected String id, srcJun, destJunc;
	protected int length, co2Limit, maxSpeed;
	protected Weather weather;
	
	public NewRoadEventBuilder(String type) {
		super(type);
	}

	protected Event createTheInstance(JSONObject data) {
		time = data.getInt("time");
		id = data.getString("id");
		srcJun = data.getString("src");
		destJunc = data.getString("dest");
		length = data.getInt("length");
		co2Limit = data.getInt("co2Limit");
		maxSpeed = data.getInt("maxSpeed");
		weather = Weather.valueOf(data.getString("weather"));
		
		return null;
	}
	
	abstract NewRoadEvent createTheRoad(); //TODO crear NewRoadEvent

}
