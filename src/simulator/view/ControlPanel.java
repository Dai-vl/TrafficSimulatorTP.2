package simulator.view;

import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private Controller control;

	ControlPanel(Controller c) {
		control = c;
	}

}
