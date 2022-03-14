package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import extra.jtable.EventsTableModel;
import extra.jtable.JunctionsTableModel;
import extra.jtable.RoadsTableModel;
import extra.jtable.VehiclesTableModel;
import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private Controller control;

	public MainWindow(Controller c) {
		super("Traffic Simulator");
		control = c;
		initGUI();

	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		mainPanel.add(new ControlPanel(control), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(control), BorderLayout.PAGE_END);

		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);

		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);

		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(control)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);

		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(control)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);

		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(control)), "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);

		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(control)), "Roads");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);

		// TODO add other tables
		// ...
		// maps

		JPanel mapView = createViewPanel(new MapComponent(control), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);

		// TODO add a map for MapByRoadComponent
		// ...

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		// TODO add a framed border to p with title
		p.add(new JScrollPane(c));
		return p;
	}
}
