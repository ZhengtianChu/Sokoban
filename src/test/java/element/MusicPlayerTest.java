package element;

import static org.junit.Assert.*;

import javax.sound.sampled.LineUnavailableException;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayerTest {
	MusicPlayer musicPlayer = MusicPlayer.getInstance();
	static MediaPlayer player;
	static MediaPlayer tempPlayer;
	static String filepath;
	static Media music;
    static JFXPanel fxPanel;
	@BeforeClass
	public static void beforeClass() {
		fxPanel = new JFXPanel();
		filepath = GameEngine.class.getResource("puzzle_theme.wav").toString();
		music = new Media(filepath);
		player = new MediaPlayer(music);
		tempPlayer = new MediaPlayer(music);
	}
	
	@Test
	public void testCreatePlayer() {
		musicPlayer.SetPlayer(null);
		try {
			musicPlayer.CreatePlayer();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		assertEquals(tempPlayer.getMedia(), player.getMedia());
	}

	@Test
	public void testIsPlayingMusic() {
		assertEquals(false, musicPlayer.IsPlayingMusic());
	}

}
