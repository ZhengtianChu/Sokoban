package element;

import javax.sound.sampled.LineUnavailableException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * I choose a lazy singleton pattern for the {@code MusicPlayer} class
 * that is because in one game, there is only one Music player can play
 * the background music
 *
 * @author Zhengtian CHU
 * @since 2.0
 *
 */
public class MusicPlayer {
	private static volatile MusicPlayer instance = null;
	
	private MusicPlayer() {System.out.println("Generate a player!");}
	
	public static synchronized MusicPlayer getInstance() {
		if (instance == null) {
			instance  = new MusicPlayer();
		} else {
			System.out.println("You can only generate one player");
		}
		return instance;
	}
	
    private MediaPlayer player;

	public void SetPlayer(MediaPlayer player) {
		this.player = player;
	}

	/**
     * Create a music player
     * 
     * @throws LineUnavailableException cannot find the music file
     */
    public void CreatePlayer() throws LineUnavailableException {
       	String filepath = GameEngine.class.getResource("puzzle_theme.wav").toString();
    	Media music = new Media(filepath);
    	player = new MediaPlayer(music);
    	player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
    }
    
    /**
     * Play the background music of the game.
     */
    public void PlayMusic() {player.play();}

    /**
     * Let the background music of the game stopped.
     */
    public void StopMusic() { player.stop();}

    /**
	 * Judge whether the music is played.
	 * 
	 * @return if the music is played, return {@code true},
	 * 					otherwise return {@code false}
	 */
	public boolean IsPlayingMusic() {return player.getStatus() == MediaPlayer.Status.PLAYING;}
}
