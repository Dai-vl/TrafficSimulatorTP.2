package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs == null)
			throw new IllegalArgumentException("ERROR en constructor SetContClassEvent: cs es null");
		this.cs = cs;
	}

	void execute(RoadMap map) {
		for (Pair<String, Integer> p : cs) {
			try {
				map.getVehicle(p.getFirst()).setContClass(p.getSecond());
			} catch (IllegalArgumentException ie) {
				throw new IllegalArgumentException(ie.getMessage() + " SetContClassEvent: No Existe vehiculo \n");
			}
		}
	}

	@Override
	public String toString() {
		return "Change CO2 class: " + cs.get(0).getFirst().toString() + ' ' + cs.get(0).getSecond().toString();
	}

}
