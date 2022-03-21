package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class ChangeCO2ClassDialog extends JDialog {

	ChangeCO2ClassDialog(Frame p) {
		super(p, "Change CO2 Class", true);
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
		SpinnerNumberModel model1 = new SpinnerNumberModel(0.0, 0.0, 10.0, 1.0);
		JSpinner co2Spinner = new JSpinner(model1);
		JSpinner ticksSpinner = new JSpinner();

		eventPanel.add(vehicleLabel);
		eventPanel.add(vehicles);
		eventPanel.add(co2Label);
		eventPanel.add(co2Spinner);
		eventPanel.add(ticksLabel);
		eventPanel.add(ticksSpinner);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton okButton = new JButton("  OK  "); // TODO
		JButton cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2ClassDialog.this.dispose();
			}

		});

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}

		});
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(okButton);

		this.add(info, BorderLayout.PAGE_START);
		this.add(eventPanel, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.PAGE_END);

		setSize(new Dimension(650, 300));
		setResizable(false);
		this.setVisible(true);
	}
}
