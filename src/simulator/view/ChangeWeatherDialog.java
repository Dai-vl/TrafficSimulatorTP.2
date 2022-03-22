package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

@SuppressWarnings("serial")
public class ChangeWeatherDialog extends JDialog {

	private Controller control;

	ChangeWeatherDialog(Frame p, Controller control) {
		super(p, "Change CO2 Class", true);
		this.control = control;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		JPanel info = new JPanel();

		JLabel infoLabel = new JLabel(
				"Schedule an event to change the weather of a road after a given number of simulation ticks from now.");

		info.add(infoLabel);

		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JLabel roadLabel = new JLabel("Road:");
		JLabel weatherLabel = new JLabel("Weather:");
		JLabel ticksLabel = new JLabel("Ticks:");

		JComboBox<Road> roads = new JComboBox<Road>(); // TODO
		roads.setPreferredSize(new Dimension(60, 20));
		JComboBox<Weather> weatherSpinner = new JComboBox<Weather>(Weather.values());
		JSpinner ticksSpinner = new JSpinner();
		ticksSpinner.setPreferredSize(new Dimension(60, 20));

		eventPanel.add(roadLabel);
		eventPanel.add(roads);
		eventPanel.add(weatherLabel);
		eventPanel.add(weatherSpinner);
		eventPanel.add(ticksLabel);
		eventPanel.add(ticksSpinner);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton okButton = new JButton("  OK  "); // TODO
		JButton cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeWeatherDialog.this.dispose();
			}

		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Weather>> l = new ArrayList<>();
				l.add(new Pair<String, Weather>(roads.getSelectedItem().toString(),
						(Weather) weatherSpinner.getSelectedItem()));
				// TODO get time para sumarselo a los ticks
				control.addEvent(new SetWeatherEvent((Integer) ticksSpinner.getValue(), l));
				ChangeWeatherDialog.this.dispose();
			}

		});
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(okButton);

		this.add(info, BorderLayout.PAGE_START);
		this.add(eventPanel, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.PAGE_END);

		this.setResizable(false);
		this.pack();
		this.setLocation(MainWindow.ancho / 2 - this.getWidth() / 2, MainWindow.alto / 2 - this.getHeight() / 2);
		this.setVisible(true);

	}
}
