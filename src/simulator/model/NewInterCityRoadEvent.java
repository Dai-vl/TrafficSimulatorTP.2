package simulator.model;

public class NewInterCityRoadEvent extends Event{

	private String id;
	private String srcJun;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather) 
	{ // TODO he cambiado el orden para que coinicda con el de road

			super(time);
			this.id = id;
			this.srcJun = srcJun;
			this.destJunc = destJunc;
			this.length = length;
			this.co2Limit = co2Limit;
			this.maxSpeed = maxSpeed;
			this.weather = weather;
	}
	
	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		try {
			InterCityRoad r = new InterCityRoad(id, map.getJunction(srcJun), map.getJunction(destJunc), maxSpeed, co2Limit, length, weather);
			map.addRoad(r);
		}
		catch(IllegalArgumentException ie) {
			System.out.println(ie.getMessage() + " NewCityRoadEvent: addJunction \n");
		}

	}
}