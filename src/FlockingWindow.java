import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 * Creatures the windows for the panels.
 * 
 * @author Kavy Rattana
 * 
 */
public class FlockingWindow extends JFrame implements Serializable {

	public static FlockingWindow window;
	public FlockingPanel flockPanel;
	public ControlPanel controls;
	public Ball b;

	public static Image predator1 = new ImageIcon("predatorsh.png").getImage();
	public static Image predator2 = new ImageIcon("pred2.png").getImage();
	public static Image predator3 = new ImageIcon("predator3.png").getImage();
	public static Image shooter = new ImageIcon("Shooter.png").getImage();
	public static Image background = new ImageIcon("space.jpg").getImage();
	//public static Clip shoot = = new CLip
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor adds a big panel for displaying the flock and a smaller panel
	 * for controls.
	 * 
	 */
	public FlockingWindow() {
		
		setTitle("Space Battle");
		flockPanel = new FlockingPanel();
		controls = new ControlPanel(flockPanel);
		flockPanel.getControlPanel(controls);

		setLayout(new BorderLayout());
		add(flockPanel, BorderLayout.CENTER);
		add(controls, BorderLayout.EAST);
		setUndecorated(false);

		flockPanel.repaint();
		flockPanel.revalidate();

		getContentPane();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		String message = "READY FOR BATTLE!?";
		String title = "Space Battle";

		flockPanel.hide();

		flockPanel.setBackground(Color.blue);

		JOptionPane.showMessageDialog(getParent(), message, title,
				getDefaultCloseOperation());

		flockPanel.show();
		flockPanel.enable();

		flockPanel.requestFocus();

		flockPanel.startTimer();

	}

	/**
	 * creatues the window for the flocking program.
	 * 
	 * @param args
	 *            .
	 */
	public static void main(String[] args) {

		window = new FlockingWindow();

	}

}
