package simulator.factories;

import simulator.model.NewCityRoadEvent;
import simulator.model.Road;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder{

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	NewCityRoadEvent createTheRoad() {
		return new NewCityRoadEvent(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather );
	}

}
