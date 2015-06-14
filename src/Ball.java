import java.awt.Graphics;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Ball implements many flocking behaviors.
 * 
 * @author Kavy
 * @version 04/1/2013
 */
public class Ball implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Position of the ball. **/
	protected Position position;

	/** The vector of direction of the ball. */
	protected MyVector vector;

	/** The maximum value in the x-coordinate. */
	protected int maxX;
	/** The maximum value in the y-coordinate. */
	protected int maxY;
	/** The width of the ball. **/
	public static final int BALLWIDTH = 10;

	protected ArrayList<Ball> swarm;
	protected ArrayList<Shooter> shooters;
	protected ArrayList<Particles> particles;
	private int health;
	private int shootersX;
	private int shootersY;
	protected Shooter shot;
	private FlockingPanel fpanel;

	Boolean hasDied = false;

	/**
	 * The constructor.
	 * 
	 * @param maxX1
	 *            The starting x.
	 * @param maxY
	 *            The starting y.
	 * @param list
	 *            The array list of ball objects.
	 */
	public Ball(final int maxX1, final int maxY1, ArrayList<Ball> list,
			ArrayList<Shooter> sh, ArrayList<Particles> pa) {

		swarm = list;
		shooters = sh;
		particles = pa;
		health = 1;
		maxX = maxX1;
		maxY = maxY1;
		int x = (int) (maxX * Math.random());
		int y = (int) (0);
		position = new Position(x, y);

		// random numbers between -10 and 10
		int changeX = 0;
		int changeY = (int) 10;
		vector = new MyVector(changeX, changeY);

	}

	/**
	 * Implements rule 2 for the flocks.
	 * 
	 * @return v.
	 */
	public MyVector moveTowardsShooter() {
		double dx = 0.0;
		double dy = 0.0;
		MyVector v = new MyVector(dx, dy);

		if (shooters.size() > 0) {
			shootersX = (int) shooters.get(0).position.getX();
			shootersY = (int) shooters.get(0).position.getY();

			if (shootersX > position.getX()) {

				dx = dx + 1;
			}
			if (shootersX < position.getX()) {

				dx = dx - 1;
			}

		}
		v.set(dx, dy);

		return v;

	}

	/**
	 * Implements rules 3 of the flock.
	 * 
	 * @return v.
	 */
	public MyVector matchVelocity() {

		MyVector sumV = new MyVector(0, 0);
		int count = 0;

		for (Ball b : swarm) {

			sumV.add(b.vector);
			count++;

		}

		double dx = sumV.getChangeX() / count;
		double dy = sumV.getChangeY() / count;
		MyVector v = new MyVector(dx, dy);

		v.subtract(vector.getChangeX(), vector.getChangeY());
		v.set(v.getChangeX() / 8, v.getChangeY() / 8);

		return v;

	}

	/**
	 * update changes the position of the ball; it does bounds checking.
	 */
	public void update() {

		double x = position.getX();
		double y = position.getY();
		double changeX = vector.getChangeX();
		double changeY = vector.getChangeY();

		MyVector rule1 = moveTowardsShooter();
		changeX += rule1.getChangeX();
		changeY += rule1.getChangeY();
		//
		// MyVector rule2 = keepDistance();
		// changeX += rule2.getChangeX();
		// changeY += rule2.getChangeY();
		//
		// MyVector rule3 = matchVelocity();
		// changeX += rule3.getChangeX();
		// changeY += rule3.getChangeY();

		if (x + changeX > maxX) {
			changeX = -1 * changeX;
		} else if (x + changeX < 0) {
			changeX = -1 * changeX;
		}
		if (y + changeY > maxY) {
			changeY = -1 * changeY;
		} else if (y + changeY < 0) {
			changeY = -1 * changeY;
		}

		x += changeX;
		y += changeY;

		fpanel = new FlockingPanel();

		if (particles.size() > 0 || swarm.size() > 0) {

			for (int i = 0; i < particles.size(); i++) {

				for (int j = 0; j < swarm.size(); j++) {

					if (swarm.get(j).getDistance(particles.get(i)) < 17) {

						swarm.get(j).wasHit();

						System.out.println("The health of the swarm is: "
								+ swarm.get(j).getHealth());

						if (swarm.get(j).getHealth() == 0) {

							swarm.remove(j);

						}

						System.out.println("The predator has been hit!");

						fpanel.decrementHowMany();

					}

				}
			}
		}

		if (shooters.size() > 0) {

			for (int j = 0; j < swarm.size(); j++) {

				if (shooters.size() > 0) {

					if (swarm.get(j).getShooterDistance(shooters.get(0)) < 15) {

						shooters.remove(shooters.get(0));

						shooterHasDied();

						fpanel.updateBalls();

						System.out.println("The shooter has been killed");

					}

				}
			}

		}

		position.setPosition(x, y);
		vector.set(changeX, changeY);

	}

	/**
	 * Used when the shooter died.
	 */

	public void shooterHasDied() {

		if (hasDied = true) {

			String title = "Game Over!";

			JOptionPane.showMessageDialog(FlockingWindow.window, title);

		} else

			hasDied = false;

	}

	/**
	 * Returns the health.
	 * 
	 * @return
	 */

	public int getHealth() {
		return health;
	}

	/**
	 * Reduces the health of the ball.
	 */
	public void wasHit() {

		health--;
	}

	/**
	 * Draw the ball.
	 * 
	 * @param g
	 *            The drawing pane's graphic object.
	 */
	public void draw(Graphics g) {

		int x = (int) position.getX();
		int y = (int) position.getY();
		g.setColor(Color.red);
		g.fillOval(x - 5, y - 5, BALLWIDTH, BALLWIDTH);
	}

	/**
	 * Gets the distance between a ball and particle.
	 * 
	 * @param particles
	 * @return
	 */
	public int getDistance(Particles particles) {
		double dX = particles.position.getX() - position.getX();
		double dY = particles.position.getY() - position.getY();

		return (int) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	/**
	 * Gets the distance between a ball and shooter.
	 * 
	 * @param shoot
	 * @return
	 */

	public int getShooterDistance(Shooter shoot) {
		double dX = shoot.position.getX() - position.getX();
		double dY = shoot.position.getY() - position.getY();

		return (int) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	public void getFlockingPanel(FlockingPanel p) {

		fpanel = p;

	}

}
