package simulator.model;

import org.json.JSONObject;

public class Road extends SimulatedObject{
	
	private int length;

	Road(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLength() {
		return length;
	}
}
