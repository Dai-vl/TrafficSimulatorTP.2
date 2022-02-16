package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{
	
	private List<Pair<String,Integer>> cs;

	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if(cs == null) throw new IllegalArgumentException("ERROR!");
		this.cs = cs;
		}


	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for( int i = 0; i < cs.size(); i++) {
			try {
			map.getVehicle(cs.get(i).getFirst()).setContaminationClass(cs.get(i).getSecond());
			}
			catch(IllegalArgumentException ie) {
				System.out.println(ie.getMessage() + " NewSetContClassEvent: No Existe vehiculo \n");
			}
		}
	}

}
