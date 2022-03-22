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
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.SetContClassEvent;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class ChangeCO2ClassDialog extends JDialog {
	private Controller control;

	ChangeCO2ClassDialog(Frame p, Controller control) {
		super(p, "Change CO2 Class", true);
		this.control = control;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		JPanel info = new JPanel();

		JLabel infoLabel = new JLabel(
				"Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.");

		info.add(infoLabel);

		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JLabel vehicleLabel = new JLabel("Vehicle:");
		JLabel co2Label = new JLabel("CO2 Class:");
		JLabel ticksLabel = new JLabel("Ticks:");

		JComboBox<Vehicle> vehicles = new JComboBox<Vehicle>(); // TODO
		vehicles.setPreferredSize(new Dimension(60, 20));
		JSpinner co2Spinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10.0, 1.0));
		co2Spinner.setPreferredSize(new Dimension(60, 20));
		JSpinner ticksSpinner = new JSpinner();
		ticksSpinner.setPreferredSize(new Dimension(60, 20));

		eventPanel.add(vehicleLabel);
		eventPanel.add(vehicles);
		eventPanel.add(co2Label);
		eventPanel.add(co2Spinner);
		eventPanel.add(ticksLabel);
		eventPanel.add(ticksSpinner);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.setPreferredSize(cancelButton.getPreferredSize());

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2ClassDialog.this.dispose();
			}

		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Integer>> l = new ArrayList<>();
				l.add(new Pair<String, Integer>(vehicles.getSelectedItem().toString(),
						(Integer) co2Spinner.getValue()));
				// TODO get time para sumarselo a los ticks
				control.addEvent(new SetContClassEvent((Integer) ticksSpinner.getValue(), l));
				ChangeCO2ClassDialog.this.dispose();
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
