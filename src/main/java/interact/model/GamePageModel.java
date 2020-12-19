package interact.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import display.GameCore;
import element.MusicPlayer;
import element.GameDialog;
import element.GameEngine;
import element.GameGrid;
import element.Level;

public class GamePageModel {
    private static Stage m_PrimaryStage;
    private static GameEngine m_GameEngine;
    private static GridPane m_GameGrid;
	private static File m_SaveFile;
    private static StringProperty m_NumberProperty = new SimpleStringProperty();
    private static int mins = 0;
	private static int secs = 0;
	private int millis = 0;
	private Boolean disable = false;
	
	
	private static final MusicPlayer player = MusicPlayer.getInstance();

	public GamePageModel(Stage stage) {GamePageModel.m_PrimaryStage = stage;}

	public static GameEngine GetGameEngine() {return m_GameEngine;}

    public static void SetGameEngine(GameEngine engine) {GamePageModel.m_GameEngine = engine;}

	public static File GetSaveFile() {return m_SaveFile;}

	public static void SetSaveFile(File file) {m_SaveFile = file;}
	
    public static GridPane GetGameGrid() {return m_GameGrid;}

	public static StringProperty GetNumberProperty() {return m_NumberProperty;}

	public static MusicPlayer GetPlayer() {return player;}

	public static void SetGameGrid(GridPane gameGrid) {m_GameGrid = gameGrid;}
        
    public static Stage GetPrimaryStage() {return m_PrimaryStage;}
    	
	public static int GetMins() {return mins;}

	public static int GetSecs() {return secs;}

    public static void SetSecs(int sec) {secs = sec;}

    public static void SetMins(int min) {mins = min;}


    /**
     * Load game from default file
	 */
    public void LoadDefaultSaveFile() {
        System.out.println("Hi");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("display/SampleGame.skb");
        GameEngine.SetInputStream(inputStream);
        System.out.println(inputStream);
        GameCore.InitializeGame(inputStream);
        System.out.println("Hi");
        GameCore.SetEventFilter();
        System.out.println("Hi");
    }

    /**
     * Save the game in local position
     * @throws IOException if cannot create new file
     * @throws NullPointerException if cancel saving the game
     *
     * @see FileChooser
     * @see Level
     * @see BufferedWriter
     */
    public void SaveGame() throws IOException, NullPointerException {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        m_SaveFile = fileChooser.showSaveDialog(m_PrimaryStage);

        if (!m_SaveFile.exists()) {
        	try {
				m_SaveFile.createNewFile();
			} catch (Exception e) {
        	    System.out.println("Save Failed!");
            }
        }
        List<Level> levels = GamePageModel.GetGameEngine().levels;
        
		FileWriter fw = new FileWriter(m_SaveFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("TotalMovesCount:" + GameEngine.GetMovesCount() + "\n"
                + "LastMovesCount:" + GameEngine.GetLastMovesCount() + '\n'
                + "Mins:" + GetMins() + '\n'
                + "Secs:" +  GetSecs() + '\n'
                + "TotalTime:" + GameEngine.GetTotalTime() + '\n');

        bw.write("MapSetName: Example Game!\n");

        for (Level level : levels) {
            GameGrid grid = Level.SaveGrid(level);
            bw.write("LevelName:" + level.GetName() + '\n' + grid.toString() + '\n');
        }    
        bw.close();
    }

    /**
     * Load the fame file
     *
     * @see GameCore
     */
    public void LoadGame() {
    	if(!disable) {
    	    try {
                GameCore.LoadGameFile();
                disable = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    	}else {
    		System.out.println("Cannot load again!");
        }
    }
   
    /**
     *  Undo the last move for keeper and crate.
     */
    public void Undo() {
    	m_GameEngine.undo();
    }    
    
    public void ResetLevel() {
      	m_GameEngine.resetLevel();
    }

    /**
     * show the game about dialog
     *
     * @see GameDialog
     */
    public void ShowAbout() {
        String title = "About This Game";
        String message = "Created by Zhengtian CHU, Nov 2020.\n";
        Stage dialog = GameDialog.CreateView(title, message, null);
        dialog.show();
    }
        
    public void ToggleMusic() {
    	if (player.IsPlayingMusic()) {
            player.StopMusic();
    	} else {
    		player.PlayMusic();
    	}
    }
    
    public void ToggleDebug() {
        m_GameEngine.ToggleDebug();
        GameCore.ReloadGrid();
    }

	public static StringProperty NumberProperty() {return m_NumberProperty;}

    /**
     * Change the text in status time-consuming
     * @param text the text in view
     */
    public void Change(Text text) {
        int MILLTOSEC = 1000;
        int SECTOMIN = 60;
        int ZERO = 0;
        int TEN = 10;
        int HUNDRED = 100;
    	text.setFont(new Font("MV Boli", 15));    	
    	if (GameEngine.GetMovesCount() - GameEngine.GetLastMovesCount() == 0)
    	{
    		millis = ZERO;
    		secs = ZERO;
    		mins = ZERO;
    	}   
		if(millis == MILLTOSEC) {
			secs++;
			millis = ZERO;
		}
		if(secs == SECTOMIN) {
			mins++;
			secs = ZERO;
		} 
		text.setText("                          TIME : " + (((mins/TEN) == ZERO) ? "0" : "") + mins + ":"
		 + (((secs/TEN) == ZERO) ? "0" : "") + secs + ":"
			+ (((millis/TEN) == ZERO) ? "00" : (((millis/HUNDRED) == ZERO) ? "0" : "")) + millis++);
    }

    public void ShowScores() { GameEngine.ShowScores(); }
}
