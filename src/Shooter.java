import java.awt.Graphics;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Shooter implements Serializable {
	/**
	 * The shooter class for the game shooter. 
	 * @author Kavy
	 * 
	 * 
	 */
	private ArrayList<Shooter> shooter;
	private MyVector vector;
	double shooterX = 0.0;
	double shooterY = 0.0;
	double particlesX = 0;;
	double particlesY = 0;;
	double velocityY = 0.0;
	double velocityX = 0.0;
	Position position;

	private Image img;

	
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor.
	 * @param maxX1
	 * @param maxY1
	 * @param list
	 */
	public Shooter(final int maxX1, final int maxY1, ArrayList<Shooter> list) {

		shooter = list;

		shooterX = maxX1;
		shooterY = maxY1;

		position = new Position(shooterX, shooterY);

		int changeX = (int) position.getX();
		int changeY = (int) position.getY();
		vector = new MyVector(changeX, changeY);

		createImage();

	}
	/**
	 * Used to generate an image for the shooter.
	 */
	public void createImage() {

		try {
			img = ImageIO.read(new File("Shooter.png"));
		} catch (IOException e) {
		}

	}

	/**
	 * update changes the position of the ball; it does bounds checking.
	 */
	public void update() {

		double x = position.getX();
		double y = position.getY();

		double changeX = vector.getChangeX();
		double changeY = vector.getChangeY();

		position.setPosition(x, y);
		vector.set(changeX, changeY);

	}
	/**
	 * The draw method for the shooter.
	 * @param g
	 */
	public void draw(Graphics g) {

		int x = (int) position.getX();
		int y = (int) position.getY();

		g.drawImage(img, x, y, 25, 80, null);

	}

}
