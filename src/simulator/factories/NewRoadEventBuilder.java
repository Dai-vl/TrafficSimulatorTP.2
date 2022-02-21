package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {
	// a
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
		co2Limit = data.getInt("co2limit");
		maxSpeed = data.getInt("maxspeed");
		weather = Weather.valueOf(data.getString("weather"));

		return createTheRoad();
	}

	abstract NewRoadEvent createTheRoad();

}
