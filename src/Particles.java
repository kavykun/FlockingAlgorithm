import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.management.timer.Timer;
import javax.swing.JPanel;

public class Particles extends JPanel implements ActionListener, Serializable{

	/**
	 * The particles or bullets for the shooter.
	 * 
	 * @author Kavy
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Particles> particles;
	private MyVector vector;
	protected int particlesX;
	protected int particlesY;
	Position position;
	/**
	 * The Constructor.
	 * @param maxX1
	 * @param maxY1
	 * @param list
	 */
	public Particles(final int maxX1, final int maxY1, ArrayList<Particles> list) {

		particles = list;

		particlesX = maxX1;
		particlesY = maxY1;

		int x = (int) (particlesX + 7);
		int y = (int) (particlesY);
		position = new Position(x, y);

		// random numbers between -10 and 10
		int changeX = (int) 0;
		int changeY = (int) -20;
		vector = new MyVector(changeX, changeY);

	}
	/**
	 * The action performed method for the particle.
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		repaint();

	}

	/**
	 * update changes the position of the ball; it does bounds checking.
	 */
	public void update() {

		double x = position.getX();
		double y = position.getY();
		double changeX = vector.getChangeX();
		double changeY = vector.getChangeY();
		
		for(int i = 0; i <particles.size(); i++){
			
			if(particles.get(i).position.getY() < 0){
				
				particles.remove(i);
				
			}
	
			
		}

	

		//x += changeX;
		y += changeY;

		position.setPosition(x, y);
		vector.set(changeX, changeY);

	}
	/**
	 * The Draw method for particles. 
	 * @param g
	 */
	public void draw(Graphics g) {

		int x = (int) position.getX();
		int y = (int) position.getY();
		g.setColor(Color.red);

		g.fillOval(x, y, 10, 10);

	}

}
