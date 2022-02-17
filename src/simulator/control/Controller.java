package simulator.control;

import java.io.IOException;
import java.io.InputStream; //TODO es esta biblioteca?
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {

	private TrafficSimulator ts;

	private Factory<Event> ef;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if (sim.equals(null))
			throw new IllegalArgumentException("Traffic Simulator con valor null en controller");
		if (eventsFactory.equals(null))
			throw new IllegalArgumentException("Traffic Simulator con valor null en controller");
		ts = sim;
		ef = eventsFactory;
	}

	public void reset() {
		ts.reset();
	}

	public void loadEvents(InputStream in) throws IOException {
		JSONObject jo = new JSONObject(new JSONTokener(in));

		if (!jo.has("events"))
			throw new IllegalArgumentException("Input Stream incorrecto");

		JSONArray events = jo.getJSONArray("events");

		for (int i = 0; i < events.length(); i++) {
			JSONObject e = events.getJSONObject(i);
			ts.addEvent(ef.createInstance(e)); // TODO por ejemplo
		}

		// TODO cerrar input?
		in.close();
	}

	public void run(int n, OutputStream out) {
		// TODO revisar
		if (out == null) {
			out = new OutputStream() {
				public void write(int b) throws IOException {
				}
			};
		}

		PrintStream print = new PrintStream(out);
		print.println("{");
		print.println("\" states\" : [");
		JSONObject currentState = new JSONObject();

		for (int i = 0; i < n; i++) {
			ts.advance();
			currentState = ts.report();
			print.println(currentState);
			if (i < n - 1)
				print.print(","); // para que no imprima la ultima coma

		}

		print.println("]");
		print.println("}");

	}

}
