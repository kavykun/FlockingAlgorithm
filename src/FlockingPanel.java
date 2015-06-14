import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Displays the flocking of the ball objects.
 * 
 * @author Kavy Rattana
 * 
 */
public class FlockingPanel extends JPanel implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.Timer theTimer;
	private int speed = 60;
	private int howMany = 1;
	public int level = 1;
	private Image image;
	private ArrayList<Ball> swarm;
	private ArrayList<Shooter> shooters;
	private ArrayList<Particles> particles;

	private Shooter s;
	private Particles p;
	private Ball b;
	private SoundNew newSound;
	public Point mousePosition;
	public ControlPanel cpanel;
	private FlockingPanel fpanel;

	private Boolean hasDied = false;

	public double shooterX = 0.0;
	public double shooterY = 0.0;
	public double particlesX = 0;;
	public double particlesY = 0;;
	public double velocityY = 0.0;
	public double velocityX = 0.0;
	public double xCoordinate = 0.0;
	public double yCoordinate = 0.0;

	/**
	 * creatures the window for the flocking balls adds the balls to the array.
	 * 
	 */
	public FlockingPanel() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(400, 700));
		setLayout(new BorderLayout());

		image = new ImageIcon("space.jpg").getImage();

		shooterX = 200.0;
		shooterY = 650;

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		swarm = new ArrayList<Ball>();
		shooters = new ArrayList<Shooter>();
		particles = new ArrayList<Particles>();

		Ball k = new Ball(0, 0, null, null, null);

		p = new Particles((int) particlesX, (int) particlesY, particles);
		s = new Shooter((int) shooterX, (int) shooterY, shooters);

		shooters.add(s);

		for (int i = 0; i < howMany; i++) {
			Ball b = null;

			b = new PredatorShip(400, 700, swarm, shooters, particles);

			swarm.add(b);

		}

		theTimer = new javax.swing.Timer(speed, this);

	}

	public void getControlPanel(ControlPanel p) {

		cpanel = p;

		System.out.println("The control panel has been passed");

	}

	/**
	 * sets the delay on the timer.
	 * 
	 * @param n
	 *            .
	 */
	public void setDelayTimer(int n) {

		theTimer.setDelay(n);

	}

	/**
	 * sets the initial delay for the timer.
	 * 
	 * @param n
	 *            .
	 */
	public void setInitialDelay(int n) {

		theTimer.setInitialDelay(n);

	}

	/**
	 * starts the timer.
	 */
	public void startTimer() {

		if (theTimer != null) {

			theTimer.start();
		}

	}

	/**
	 * stops the timer.
	 */
	public void stopTimer() {

		if (theTimer != null) {

			theTimer.stop();
		}
	}

	/**
	 * updates the ball by clearing the array list and then adding new balls.
	 */
	public void updateBalls() {

		swarm.clear();
		shooters.clear();
		particles.clear();

	

		for (int i = 0; i < howMany; i++) {
			Ball b = null;

			b = new PredatorShip(400, 700, swarm, shooters, particles);
			if (i == 3 || i == 5 || i == 7) {
				b = new PredatorShip1(400, 700, swarm, shooters, particles);

			}
			if (i == 6 || i > 9) {
				b = new PredatorShip2(400, 700, swarm, shooters, particles);
			}

			swarm.add(b);

		}
		
		s = new Shooter((int) shooterX, (int) shooterY, shooters);

		shooters.add(s);

		revalidate();
		repaint();

	}

	/**
	 * repaints the ball's position.
	 * 
	 * @param g
	 *            .
	 */
	public void paintComponent(Graphics g) {

		super.paintComponents(g);

		g.clearRect(0, 0, 400, 700);
		g.drawImage(FlockingWindow.background, 0, 0, 400, 700, null);

		for (int i = 0; i < swarm.size(); i++) {

			swarm.get(i).draw(g);

		}

		for (int i = 0; i < shooters.size(); i++) {

			shooters.get(i).draw(g);

		}
		for (int i = 0; i < particles.size(); i++) {

			particles.get(i).draw(g);

		}

	}

	/**
	 * handles the event for updating the ball objects.
	 * 
	 * @param e
	 *            .
	 */
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < swarm.size(); i++) {

			swarm.get(i).update();

		}

		for (int i = 0; i < shooters.size(); i++) {

			shooters.get(i).update();

		}
		for (int i = 0; i < particles.size(); i++) {

			particles.get(i).update();

		}

		howMany = swarm.size();

		cpanel.updateLevelsTextField();
		cpanel.updatePredatorsTextField();
		cpanel.updateXcoordianteTextField();
		cpanel.updateYcoordianteTextField();
		cpanel.revalidate();
		cpanel.repaint();

		checkDone();
		repaint();

	}

	/**
	 * gets the speed of the balls.
	 * 
	 * @return speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * sets the speed of the balls.
	 * 
	 * @param s
	 *            .
	 */
	public void setSpeed(int s) {
		speed = s;
	}

	/**
	 * get how many balls there are.
	 * 
	 * @return howMany.
	 */
	public int getHowMany() {
		return howMany;
	}

	/**
	 * sets how many balls there are.
	 * 
	 * @param hm
	 *            .
	 */
	public void setHowMany(int hm) {
		howMany = hm;
	}

	public void checkDone() {

		if (swarm.size() == 0) {

			level++;
			String msg = "Level " + (level - 1) + " completed";
			String tit = "Victory";
			JOptionPane.showMessageDialog(FlockingWindow.window, msg);
			howMany = level;
			howMany++;
			updateBalls();

		}
	}

	public void space() {

		newSound = new SoundNew();

		particlesX = shooterX;
		particlesY = shooterY;

		p = new Particles((int) particlesX, (int) particlesY, particles);
		particles.add(p);

		//newSound.playSound("0.wav");

	}

	public void ShooterMoveRight() {

		velocityX = 12;
		velocityY = 0;

		shooterX += velocityX;
		shooterY += velocityY;

		particlesY = shooterY;
		particlesX = shooterX;
	}

	public void ShooterMoveLeft() {

		velocityX = -12;
		velocityY = 0;

		shooterX += velocityX;
		shooterY += velocityY;

		particlesY = shooterY;
		particlesX = shooterX;

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

		int code = arg0.getKeyCode();

		if (code == KeyEvent.VK_RIGHT) {

			shooters.clear();
			ShooterMoveRight();

			s = new Shooter((int) shooterX, (int) shooterY, shooters);

			shooters.add(s);

		}
		if (code == KeyEvent.VK_LEFT) {

			shooters.clear();
			ShooterMoveLeft();

			s = new Shooter((int) shooterX, (int) shooterY, shooters);

			shooters.add(s);

		}

		if (code == KeyEvent.VK_SPACE) {

			space();

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		mousePosition = arg0.getPoint();

		// if (particles.size() > 0 || swarm.size() > 0) {

		for (int j = 0; j < swarm.size(); j++) {

			if (getMouseDistance(swarm.get(j), mousePosition) < 50) {

				stopTimer();

				System.out
						.println("The size of the swarm is : " + swarm.size());

				xCoordinate = mousePosition.getX();
				yCoordinate = mousePosition.getY();

				setxCoordinate(xCoordinate);
				setyCoordinate(yCoordinate);

				String point = "Predator: " + swarm.get(j).getClass().getName()
						+ "\n Health: " + swarm.get(j).getHealth();

				JOptionPane.showMessageDialog(FlockingWindow.window, point);

			}

		}

		startTimer();
	}

	// TODO Auto-generated method stub

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the xCoordinate
	 */
	public double getxCoordinate() {

		return xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public double getyCoordinate() {

		return yCoordinate;
	}

	/**
	 * @param xCoordinate
	 *            the xCoordinate to set
	 */
	public void setxCoordinate(double xCoordinate) {

		this.xCoordinate = xCoordinate;
	}

	/**
	 * @param yCoordinate
	 *            the yCoordinate to set
	 */
	public void setyCoordinate(double yCoordinate) {

		this.yCoordinate = yCoordinate;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void decrementHowMany() {

		howMany--;

	}

	public int getMouseDistance(Ball pd, Point p) {

		double dX = p.getX() - pd.position.getX();
		double dY = p.getY() - pd.position.getY();

		return (int) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	public void save() throws IOException {

		JFileChooser chooser = new JFileChooser();

		chooser.setCurrentDirectory(new java.io.File("saves"));
		chooser.setAcceptAllFileFilterUsed(false);

		int show = chooser.showSaveDialog(null);

		try {

			File file = chooser.getSelectedFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

			ArrayList[] allArrays = new ArrayList[5];

			allArrays[0] = swarm;
			allArrays[1] = shooters;
			allArrays[2] = particles;

			objectOut.writeObject(allArrays);

			objectOut.close();

			JOptionPane.showMessageDialog(null, "Saved");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void load() throws IOException, ClassNotFoundException {

		JFileChooser chooser = new JFileChooser();

		chooser.setCurrentDirectory(new java.io.File("saves"));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		int show = chooser.showOpenDialog(this);

		try {

			File file = chooser.getSelectedFile();

			FileInputStream fileIn = new FileInputStream(file);

			ObjectInputStream objectIn = new ObjectInputStream(fileIn);

			ArrayList[] allArrays = (ArrayList[]) objectIn.readObject();

			swarm.clear();
			shooters.clear();
			particles.clear();

			swarm = allArrays[0];
			shooters = allArrays[1];
			particles = allArrays[2];

			repaint();

			JOptionPane.showMessageDialog(null, "Loaded");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
