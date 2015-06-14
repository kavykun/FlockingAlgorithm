import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.Serializable;

public class PredatorShip2 extends Ball implements Serializable {
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
	 * The constructor.
	 * @param x
	 * @param y
	 * @param list
	 * @param sh
	 * @param pa
	 */
	public PredatorShip2(int x, int y, ArrayList<Ball> list,
			ArrayList<Shooter> sh, ArrayList<Particles> pa) {
		super(x, y, list, sh, pa);
		shipx = x;
		shipy = y;
		health = 3;

		try {
			File file = new File("predator3.png");
			img = ImageIO.read(file);
		} catch (Exception e) {
		}
	}
	/**
	 * Returns the health of the predator ship.
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * Subracts 1 from the predators ship's health.
	 * 
	 */
	public void wasHit() {
		health--;
	}
	/**
	 * The draw method for the predator ship.
	 */
	public void draw(Graphics g) {

		int x = (int) position.getX();
		int y = (int) position.getY();

		g.drawImage(img, x, y, 50, 75, null);
	}

}