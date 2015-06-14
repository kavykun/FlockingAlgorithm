import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundNew {
	/**
	 * The sound class for the game.
	 * @author Kavy
	 * 
	 */
	File yourFile;
	AudioInputStream stream;
	AudioFormat format;
	DataLine.Info info;
	Clip clip;
	/**
	 * The method that plays the sound.
	 * @param filename
	 */

	public void playSound(String filename) {

		yourFile = new File(filename);

		try {

			stream = AudioSystem.getAudioInputStream(yourFile);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();

		} catch (Exception e) {

		}

	}

}
