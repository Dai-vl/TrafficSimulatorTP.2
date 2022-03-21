package simulator.view;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private Controller control;

	ControlPanel(Controller c) {
		control = c;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new GridLayout(1, 2));

		JPanel left = new JPanel();
		left.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton fileChooser = new JButton();

		ImageIcon iconOpen = new ImageIcon("resources/icons/open.png");
		fileChooser.setIcon(iconOpen);
		fileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int seleccion = fc.showOpenDialog(ControlPanel.this);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					control.reset();
					InputStream in;
					try {
						in = new FileInputStream(fc.getSelectedFile());
						control.loadEvents(in);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else if (seleccion == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(ControlPanel.this, "Ha habido un error", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		JButton contVehicle = new JButton();
		ImageIcon iconCO2 = new ImageIcon("resources/icons/co2class.png");
		contVehicle.setIcon(iconCO2);
		contVehicle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2ClassDialog co2 = new ChangeCO2ClassDialog(
						(Frame) SwingUtilities.getWindowAncestor(ControlPanel.this));
			}

		});

		left.add(fileChooser);
		left.add(contVehicle);

		JPanel right = new JPanel();
		right.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.add(left);
		this.add(right);

	}

}
