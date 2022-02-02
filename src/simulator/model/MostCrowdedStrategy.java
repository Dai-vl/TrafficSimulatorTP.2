package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{
	
	private int _timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}
	
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if(roads.isEmpty())
			return -1;
		if(currGreen == -1)
			return -2; //TODO
		if(currTime - lastSwitchingTime < _timeSlot)
			return currGreen;
		
		return 0; //TODO wtf es eso
	}

}
