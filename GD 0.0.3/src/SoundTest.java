import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

public class SoundTest {
	File f = new File("src/testMusic1.wav");
	Clip clip;
	AudioInputStream ais;
	Boolean playing = false;
	FloatControl soundControl;
	public Float currentVolume = 0f; // max: 6.0206f;
	
	public SoundTest() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		// TODO Auto-generated constructor stub

	clip = AudioSystem.getClip();
	ais = AudioSystem.getAudioInputStream(f);
	clip.open(ais);
	soundControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	setVolume(currentVolume);
	//soundControl.setValue(-15.0f); sets the volume
	//clip.loop(Clip.LOOP_CONTINUOUSLY);
	//SwingUtilities.invokeLater(new Runnable() {public void run() { System.out.println("close to exit");}});
}
	public void stopPlaying() {
		clip.stop();
		playing = false;
		currentVolume = 0f;
	}
	
	public void startPlaying() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		playing = true;
	}
	
	public void setVolume(float vol) {
		soundControl.setValue(vol);
		currentVolume = vol;
	}
}
