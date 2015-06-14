import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.Serializable;

public class PredatorShip extends Ball implements Serializable {
	/**
	 *  * A Predator Ship that extents the ball class
	 * 
     * @author Kyle Willcox
 	 * @version 04/17/2013
	 */
	private static final long serialVersionUID = 1L;
	private int health;
	private Image img;
	private int shipx;
	private int shipy;
	/**
	 * The Constructor.
	 * @param x
	 * @param y
	 * @param list
	 * @param sh
	 * @param pa
	 */
	public PredatorShip(int x, int y, ArrayList<Ball> list,
			ArrayList<Shooter> sh, ArrayList<Particles> pa) {
		super(x, y, list, sh, pa);
		shipx = x;
		shipy = y;
		health = 1;

		try {
			File file = new File("predatorsh.png");
			img = ImageIO.read(file);
		} catch (Exception e) {
		}
	}
	/**
	 * Returns the health of a the predator ship.
	 * 
	 * 
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * Subtracts one from the health of a predator ship.
	 */
	public void wasHit() {
		health--;
	}
	/**
	 * The Draw method for a predator ship.
	 * @param g The graphics.
	 * 
	 */
	public void draw(Graphics g) {

		int x = (int) position.getX();
		int y = (int) position.getY();

		g.drawImage(img, x, y, 50, 75, null);
	}

}
