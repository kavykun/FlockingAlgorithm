import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Creates the control panel for the flocking window.
 * 
 * @author kr5721
 * @version 03/25/2013
 * 
 */
public class ControlPanel extends JPanel implements ActionListener,
		ChangeListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton start;
	private JButton stop;
	private JButton exit;
	private JButton save;
	private JButton load;
	private JTextField creaturesTextField;
	private JTextField levelsTextField;
	private JTextField xCoordinateText;
	private JTextField yCoordinateText;
	private String creaturesText;
	private JPanel top;
	private JPanel center;
	private JPanel center1;
	private JPanel bottom;
	private JSlider speed;
	public FlockingPanel fpanel;
	boolean frozen = false;
	private int delay = 0;

	int levels;
	double xCoordinate;
	double yCoordinate;
	int predators;

	/**
	 * creates the buttons for the control panel.
	 * 
	 * @param fp
	 *            .
	 */
	public ControlPanel(FlockingPanel fp) {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(200, 400));
		setLayout(new GridLayout(4, 1));

		fpanel = fp;

		levels = fpanel.getLevel();
		xCoordinate = fpanel.getxCoordinate();
		yCoordinate = fpanel.getyCoordinate();

		start = new JButton("Start");
		stop = new JButton("Stop");
		save = new JButton("Save");
		load = new JButton("Load");
		exit = new JButton("Exit");

		creaturesTextField = new JTextField(8);
		levelsTextField = new JTextField(8);
		xCoordinateText = new JTextField(8);
		yCoordinateText = new JTextField(8);
		speed = new JSlider();

		top = new JPanel();
		center = new JPanel();
		center1 = new JPanel();
		bottom = new JPanel();

		top.setLayout(new GridLayout(2, 2));
		center.setLayout(new GridLayout(2, 4));
		center1.setLayout(new BorderLayout());
		bottom.setLayout(new BorderLayout());

		top.add(start);
		start.addActionListener(this);
		top.add(stop);
		stop.addActionListener(this);
		top.add(save);
		save.addActionListener(this);
		top.add(load);
		load.addActionListener(this);

		top.setBackground(Color.LIGHT_GRAY);
		center.setBackground(Color.LIGHT_GRAY);
		center1.setBackground(Color.LIGHT_GRAY);
		bottom.setBackground(Color.LIGHT_GRAY);

		center.add(creaturesTextField, BorderLayout.NORTH);
		creaturesTextField.setBorder(BorderFactory
				.createTitledBorder("Enemy Ships:"));
		creaturesTextField.setText(predators + "");
		creaturesTextField.setHorizontalAlignment(JLabel.CENTER);
		creaturesTextField.addActionListener(new CreaturesListener());
		creaturesTextField.setEditable(false);

		center.add(levelsTextField, BorderLayout.SOUTH);
		levelsTextField.setBorder(BorderFactory.createTitledBorder("Level:"));
		levelsTextField.setText(levels + "");
		levelsTextField.setHorizontalAlignment(JLabel.CENTER);
		levelsTextField.setEditable(false);

		center.add(xCoordinateText, BorderLayout.SOUTH);
		xCoordinateText.setBorder(BorderFactory
				.createTitledBorder("X-Coordinate:"));
		xCoordinateText.setText(fpanel.getxCoordinate() + "");
		xCoordinateText.setHorizontalAlignment(JLabel.CENTER);
		xCoordinateText.setEditable(false);

		center.add(yCoordinateText, BorderLayout.SOUTH);
		yCoordinateText.setBorder(BorderFactory
				.createTitledBorder("Y-Coordinate:"));
		yCoordinateText.setText(fpanel.getyCoordinate() + "");
		yCoordinateText.setHorizontalAlignment(JLabel.CENTER);
		yCoordinateText.setEditable(false);

		center1.add(speed, BorderLayout.NORTH);
		speed.setBorder(BorderFactory.createTitledBorder("Speed"));

		speed.addChangeListener(this);

		bottom.add(exit, BorderLayout.SOUTH);
		exit.addActionListener(this);

		add(top);
		add(center);
		add(center1);
		add(bottom);

	}

	/**
	 * handles the action for the buttons.
	 * 
	 * @param e
	 *            .
	 */
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		System.out.println(command);

		if (command.equals("Start")) {

			fpanel.startTimer();

		} else if (command.equals("Stop")) {

			fpanel.stopTimer();

		} else if (command.equals("Exit")) {

			System.exit(0);
		} else if (command.equals("Save")) {

			try {
				fpanel.save();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (command.equals("Load")) {

		}

		fpanel.repaint();
		fpanel.revalidate();

	}

	/**
	 * Handles the listener for the creatures text field. .
	 */
	public class CreaturesListener implements ActionListener {
		/**
		 * handles the event for creatures text field.
		 * 
		 * @param e
		 *            .
		 */
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();

			fpanel.setHowMany(Integer.parseInt(command));

			fpanel.removeAll();
			fpanel.updateBalls();
			fpanel.repaint();
			fpanel.revalidate();

		}
	}

	/**
	 * handles the slider in the control panel.
	 * 
	 * @param e
	 *            .
	 */
	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();
		int creatureSpeed = fpanel.getSpeed();

		if (creatureSpeed == 0) {

			frozen = true;

		}

		if (source.getValueIsAdjusting()) {
			creatureSpeed = (int) source.getValue();
			if (creatureSpeed == 0) {
				if (frozen)
					fpanel.stopTimer();
			} else {
				delay = 500 / creatureSpeed;
				fpanel.setDelayTimer(delay);
				fpanel.setInitialDelay(delay * 10);
				if (frozen)
					fpanel.startTimer();
			}
		}

	}

	/**
	 * gets the creature text.
	 * 
	 * @return creaturestext.
	 */
	public String getCreaturesText() {

		return creaturesText;
	}

	/**
	 * sets the creatures text.
	 * 
	 * @param ct
	 *            .
	 */
	public void setCreaturesText(String ct) {

		creaturesText = ct;
	}

	public void updateXcoordianteTextField() {

		xCoordinateText.setText(fpanel.getxCoordinate() + "");

	}

	public void updateYcoordianteTextField() {

		yCoordinateText.setText(fpanel.getyCoordinate() + "");

	}

	public void updateLevelsTextField() {

		levelsTextField.setText(fpanel.getLevel() + "");

	}

	public void updatePredatorsTextField() {

		predators = fpanel.getHowMany();

		creaturesTextField.setText(predators + "");

	}
}
