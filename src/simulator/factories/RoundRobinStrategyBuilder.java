package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");	
	}

	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int time = 1;
		RoundRobinStrategy rrs = null;
		if(data != null) {
			time = data.getInt("timeslot");
			rrs = new RoundRobinStrategy(time);
		}
		return rrs;
	}

}
