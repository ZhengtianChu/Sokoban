package display;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import element.*;
import interact.model.GamePageModel;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * This class stores some core methods to run the game.
 * @author Zhengtian CHU
 * @version 2.0
 * @since 1.0
 */
public class GameCore {
    public static GameLogger logger;
    private static String m_MapSetName;

	public static String GetMapSetName() {return m_MapSetName;}

	public static void SetMapSetName(String mapSetName) {GameCore.m_MapSetName = mapSetName;}
	
    /**
     * This method will be called when the game starts and need to be
     * initialized. This method will manufacture a {@link GameEngine} from
     * {@link EngineFactoryPattern} and try to create a music player. And then
     * it will judge whether the current level has been finished(using when
     * loading game not from default file). Later, it will goes to
     * {@code reloadGrid()} method.
     *
     * @param input the stream of game map file
     * @see GameEngine
     * @see element.MusicPlayer
     * @see GamePageModel
     * @see #ReloadGrid()
     */
    public static void InitializeGame(InputStream input) {
    	GameEngine.SetInputStream(input);

        GamePageModel.SetGameEngine((GameEngine) EngineFactoryPattern.GenerateEngine());
        GameEngine engine = GamePageModel.GetGameEngine();
        try {
			GamePageModel.GetPlayer().CreatePlayer();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
        while(GameEngine.GetCurrentLevel().IsComplete()) {
        	GameEngine.SetCurrentLevel(engine.GetNextLevel());
        }
        GameEngine.m_KeeperPositionList = new ArrayList<>();
        GameEngine.m_ConcurrencyList = new ArrayList<>();
        GameEngine.m_CratePositionList = new ArrayList<>();
        GamePageModel.NumberProperty().set(String.valueOf(GameEngine.GetMovesCount()));
        ReloadGrid();
    }

    /**
     * This method set an event filter to handle the operation from keyBoard by
     * calling {@code handleKey()}.
     *
     * @see GamePageModel
     * @see GameEngine
     * @see #ReloadGrid()
     */
    public static void SetEventFilter() {
        GamePageModel.GetPrimaryStage().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
				GamePageModel.GetGameEngine().HandleKey(event.getCode());
				GamePageModel.GetNumberProperty().set(String.valueOf(GameEngine.GetMovesCount()));
			} catch (Exception e) {
				e.printStackTrace();
			}
            ReloadGrid();
        });}

    /**
     * This function is called when player wants to load the existing map file
     * in their local. The method will create a {@link FileChooser} to choose the
     * file, and then saving file in {@link GamePageModel} and initialize the game.
     *
     * @throws FileNotFoundException if the file could be found
     * @see GameEngine
     * @see GamePageModel
     * @see FileChooser
     * @see #InitializeGame(InputStream)
     */
    public static void LoadGameFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        GamePageModel.SetSaveFile(fileChooser.showOpenDialog(GamePageModel.GetPrimaryStage()));

        if (GamePageModel.GetSaveFile() != null) {
            if (GameEngine.IsDebugActive()) {
                GameEngine.m_Logger.info("Loading save file: " + GamePageModel.GetSaveFile().getName());
            }

            InitializeGame(new FileInputStream(GamePageModel.GetSaveFile()));
        }}
      
	 /**
     * Load the levels in the game file to the {@code levels} list. Using {@link BufferedReader#readLine()}
     * to read line by line in the {@code reader}. Using {@code rawLevel} to
     * store the lines in level. Using {@code levels} to store the parsed levels.
     * 
     * @param input the stream of game map file
     * @return the list of levels
     * 
     * @see java.util.ArrayList
     * @see	java.io.BufferedReader
     * @see	java.io.InputStreamReader#InputStreamReader(InputStream)
     * @see GameLogger
     * @throws NullPointerException 	if cannot open the request file.
     */
    public static List<Level> LoadGameFile(InputStream input) {
        List<Level> levels = new ArrayList<>(6);
        int levelIndex = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    if (rawLevel.size() != 0) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                }
                if (line.contains("MapSetName")) {
                    SetMapSetName(line.replace("MapSetName: ", ""));
                    continue;
                }
                if (line.contains("TotalMovesCount")){
                    GameEngine.SetMovesCount(Integer.parseInt(line.replace("TotalMovesCount:","")));
                    continue;
                }
                if (line.contains("LastMovesCount")){
                    GameEngine.SetLastMovesCount(Integer.parseInt(line.replace("LastMovesCount:","")));
                    continue;
                }
                if (line.contains("Min")){
                    GamePageModel.SetMins(Integer.parseInt(line.replace("Mins:","")));
                    continue;
                }
                if (line.contains("Sec")){
                    GamePageModel.SetSecs(Integer.parseInt(line.replace("Secs:","")));
                    continue;
                }
                if (line.contains("TotalTime")){
                    GameEngine.SetTotalTime(Integer.parseInt(line.replace("TotalTime:","")));
                    continue;
                }
                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }
                    levelName = line.replace("LevelName: ", "");
                    continue;
                }
                line = line.trim();
                line = line.toUpperCase();

                if (line.matches(".*W.*W.*")) {
                    rawLevel.add(line);
                }
            }
        } catch (IOException e) {
            logger.severe("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            logger.severe("Cannot open the requested file: " + e);
        }

        return levels;
    }

    /**
     * This method will get the level of the game and add the object to the game
     * grid.
     * If the game is completed, the method will calculate the whole time the
     * player used and show then in a ScoreBoard.
     *
     * @see Level
     * @see element.Level.LevelIterator
     * @see #AddObjectToGrid(GameObject, Point)
     * @see GameEngine
     * @see GamePageModel
     */
    public static void ReloadGrid() {
	    if (GameEngine.GetGameComplete()) {
	        FileHandler.WriteFile(GameEngine.GetUsername(), GameEngine.GetTotalTime(), GameEngine.GetMovesCount(), -1);
	        FileHandler.ReadFile(-1);
	        ScoreBoardAdapter.CreateFinalBoard();
	        return;
	    }
	
	    Level currentLevel = GameEngine.GetCurrentLevel();
	    Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();
	    GamePageModel.GetGameGrid().getChildren().clear();
	
	    while (levelGridIterator.hasNext()) {
	        AddObjectToGrid(levelGridIterator.next(), levelGridIterator.GetCurrentPosition());
	    }
	    GamePageModel.GetGameGrid().autosize();
	    GamePageModel.GetPrimaryStage().sizeToScene();
	}

    /**
     * This method add the graphic objects from class {@link GraphicObject}
     * to the grid of game.
     * @param gameObject the game object need to be added to map
     * @param location the location of objects
     *
     * @see GameObject
     * @see GraphicObject
     * @see element.GameGrid
     * @see GamePageModel#GetGameGrid()
     */
	public static void AddObjectToGrid(GameObject gameObject, Point location) {
        GraphicObject graphicObject = new GraphicObject(gameObject);
        GamePageModel.GetGameGrid().add(graphicObject, location.y, location.x);
    }
}
