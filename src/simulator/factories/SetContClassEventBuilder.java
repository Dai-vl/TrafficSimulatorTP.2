package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray ws = data.getJSONArray("info");
		List<Pair<String, Integer>> w = new ArrayList<>();
		JSONObject aux;
		for (int i = 0; i < ws.length(); i++) {
			aux = ws.getJSONObject(i);
			w.add(new Pair<String, Integer>(aux.getString("vehicle"), aux.getInt("class")));
		}

		return new NewSetContClassEvent(time, w);
	}

}