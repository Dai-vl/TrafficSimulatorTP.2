package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int _timeSlot;
	private final static int posIni = 0;

	public MostCrowdedStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}

	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.isEmpty())
			return -1;
		if (currGreen == -1)
			return largestQueue(qs, posIni);
		if (currTime - lastSwitchingTime < _timeSlot)
			return currGreen;
		return largestQueue(qs, currGreen + 1);
	}

	private int largestQueue(List<List<Vehicle>> qs, int iniPos) {
		int max = qs.get(0).size();
		int look;
		for (int i = 1; i < qs.size(); i++) {
			look = (iniPos + i) % qs.size();
			if (qs.get(look).size() < max)
				max = qs.get(look).size();
		}
		return max;
	}

}