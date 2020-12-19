package element;

import javafx.scene.input.KeyCode;
import display.FileHandler;
import display.GameCore;
import interact.model.GamePageModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import display.ScoreBoardAdapter;

/**
 * The {@code GameEngine} class contains the methods that can operate
 * the game, like the engine in car, it is the engine of this game.
 * 
 * @author Zhengtian CHU - modified
 * @since   1.0
 * @version 2.0
 */
public class GameEngine extends Engine{

    public static final String GAME_NAME = "SOKOBAN";
    public static GameLogger m_Logger;
    public List<Level> levels;
    private int upMovesCount = 0;
    private int downMovesCount = 0;
    private Point currentKeeperPosition;
    private boolean isWall = false;
    private boolean onPortalUp = false;
    private boolean onPortalDown = false;
    public static List<Point> m_KeeperPositionList = new ArrayList<>();
    public static List<Point> m_CratePositionList = new ArrayList<>();
    public static List<Integer> m_ConcurrencyList = new ArrayList<>();
    private static boolean m_Debug = false;
    private static Level m_CurrentLevel;
    private static boolean m_GameComplete = false;
	private static int m_MovesCount = 0;
    private static int m_Direction;
    private static int m_LastMovesCount = 0;
    private static String m_Username = "Unknown";
    private static int m_TotalTime;
    private static int m_LevelTime;
    private static int m_CurrentMovesCount;
    private static InputStream m_InputStream;

	public static void SetInputStream(InputStream inputStream) {GameEngine.m_InputStream = inputStream;}

    public static int GetMovesCount() {return m_MovesCount;}
    
    public static void SetMovesCount(int count) {m_MovesCount = count;}

	public static int GetLastMovesCount() {return m_LastMovesCount;}
	
	public static void SetLastMovesCount(int movesCount) {m_LastMovesCount = movesCount;}

    public static Level GetCurrentLevel() {return m_CurrentLevel;}

    public static void SetCurrentLevel(Level level) {GameEngine.m_CurrentLevel = level;}
    
	public static int GetDirection() {return m_Direction;}

    public void SetDirection(int direction) {GameEngine.m_Direction = direction;}

	public static int GetTotalTime() {return m_TotalTime;}

	public static void SetTotalTime(int time){m_TotalTime = time;}

    public static int GetLevelTime() {return m_LevelTime;}

	public static int GetCurrentMovesCount() {return m_CurrentMovesCount;}

	public static String GetUsername() {return m_Username;}

	public static void SetUsername(String username) {GameEngine.m_Username = username;}
	
    public static boolean GetGameComplete() {return m_GameComplete;}

    /**
     * Constructor of {@code GameEngine}. It will create a {@link GameLogger}
     * and load the file of {@code inputStream}.
     *
     * @see GameLogger#GameLogger()
     * @see #GetNextLevel()
     * @see GameCore
     * @throws	NoSuchElementException if cannot load the game save file
     * @since 1.0
     */
    public GameEngine() {
    	InputStream input = m_InputStream;
        try {
            m_Logger = new GameLogger();
            levels = GameCore.LoadGameFile(input);
            m_CurrentLevel = GetNextLevel();
        } catch (NoSuchElementException e) {
            m_Logger.warning("Cannot load the default save file: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Judge whether the debug is active, if debug is not active,
     * then return {@code false}, otherwise {@code true}
     *
     * @return the value of {@code debug}
     */
    public static boolean IsDebugActive() {return m_Debug;}

    /**
     * Receive the event from keyboard and make the move. Set the specific
     * directions of keeper. Different target object will cause different effects.
     *
     * @param code the event sent by keyboard
     * 
     * @see javafx.scene.input.KeyCode
     * @see java.awt.Point#Point(int, int)
     * @see #Move(Point)
     * @see #IsDebugActive()
     * @see #SetDirection(int)
     */
    public void HandleKey(KeyCode code) {
        int[] directionList = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        switch (code) {
            case UP:
                Move(new Point(-1, 0));
                if (isWall) {
                	SetDirection(directionList[6]);
                	isWall = false;
                	break;
                }
                upMovesCount++;
                if (upMovesCount % 2 == 0) {
                	SetDirection(directionList[0]);
                } else
                {
                	SetDirection(directionList[4]);
                }          
                break;

            case RIGHT:
                Move(new Point(0, 1));
                if (isWall) {
                	SetDirection(directionList[7]);
                	isWall = false;
                	break;
                }
                SetDirection(directionList[1]);
                break;

            case DOWN:
                Move(new Point(1, 0));
                if (isWall) {
                	SetDirection(directionList[8]);
                	isWall = false;
                	break;
                }
                downMovesCount++;
                if (downMovesCount % 2 == 0) {
                	SetDirection(directionList[2]);
                } else
                {
                	SetDirection(directionList[5]);
                }
                break;

            case LEFT:
                Move(new Point(0, -1));
                if (isWall) {
                	SetDirection(directionList[9]);
                	isWall = false;
                	break;
                }
                SetDirection(directionList[3]);
                break;
            default:
                SetDirection(directionList[10]);
                break;
        }
        if (IsDebugActive()) {System.out.println(code);}
    }

    /**
     * Move the keeper for {@code delta} 2D vector. If the game
     * is completed, the program will jump out of the method, 
     * use {@link #IsGameComplete()} to judge whether the game is completed.
     * If the target object is wall, keeper will not move; If the target is crate,
     * if the next object at the same direction is floor, both keeper and crate
     * will move, otherwise, both will not move; If the target is floor,
     * keeper will move. Use {@code keeperMoved} to judge whether the
     * keeper moved.
     * This method also stores the position of keeper and crate in each
     * moves to list, that will help in {@link #undo()}.
     *
     * @param delta the point variation
     * @see Level#GetKeeperPosition()
     * @see Level#GetObjectAt(Point)
     * @see GameGrid#TranslatePoint(Point, Point)
     * @see GameEngine#IsDebugActive()
     * @see GameEngine#IsGameComplete()
     * @see Level#MoveGameObjectBy(GameObject, Point, Point)
     * @see FileHandler
     * @throws AssertionError if exists
     */
    public void Move(Point delta) {
        if (IsGameComplete()) {return;}
        Point keeperPosition = m_CurrentLevel.GetKeeperPosition();
        Point temp = new Point(keeperPosition.x, keeperPosition.y);
        GameObject keeper = m_CurrentLevel.GetObjectAt(keeperPosition);
        Point targetObjectPoint = GameGrid.TranslatePoint(keeperPosition, delta);
        GameObject keeperTarget = m_CurrentLevel.GetObjectAt(targetObjectPoint);
        if (GameEngine.IsDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(m_CurrentLevel.toString());
            System.out.println("Keeper pos: " + keeperPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
        }
        boolean keeperMoved = false;
        switch (keeperTarget) {
            case WALL:
            	isWall = true;
                break;
            case CRATE:
                GameObject crateTarget = m_CurrentLevel.GetTargetObject(targetObjectPoint, delta);
                if (crateTarget != GameObject.FLOOR) {break;}
                m_ConcurrencyList.add(1);
                m_KeeperPositionList.add(temp);
                m_CurrentLevel.MoveGameObjectBy(keeperTarget, targetObjectPoint, delta);
                m_CurrentLevel.MoveGameObjectBy(keeper, keeperPosition, delta);

                if (onPortalUp){ m_CurrentLevel.PutObjectAt(GameObject.PORTALUP, temp);}
                onPortalUp = false;
                if (onPortalDown){ m_CurrentLevel.PutObjectAt(GameObject.PORTALDOWN, temp); }
                onPortalDown = false;

                currentKeeperPosition = keeperPosition;
                Point currentCratePosition = GameGrid.TranslatePoint(targetObjectPoint, delta);
                m_CratePositionList.add(currentCratePosition);
                keeperMoved = true;
                break;
            case FLOOR:
            	m_ConcurrencyList.add(0);
                m_KeeperPositionList.add(temp);
                m_CurrentLevel.MoveGameObjectBy(keeper, keeperPosition, delta);

                if (onPortalUp){ m_CurrentLevel.PutObjectAt(GameObject.PORTALUP, temp); }
                onPortalUp = false;
                if (onPortalDown){ m_CurrentLevel.PutObjectAt(GameObject.PORTALDOWN, temp); }
                onPortalDown = false;

                currentKeeperPosition = keeperPosition;
                keeperMoved = true;
                break;
            case STAR:
                m_ConcurrencyList.add(0);
                m_KeeperPositionList.add(temp);
                Random rnd = new Random();
                int secs = GamePageModel.GetSecs();
                GamePageModel.SetSecs(rnd.nextInt(secs));
                m_CurrentLevel.PutObjectAt(GameObject.FLOOR,targetObjectPoint);
                m_CurrentLevel.MoveGameObjectBy(keeper, keeperPosition, delta);

                keeperMoved = true;
                break;
            case PORTALUP:
                onPortalDown = true;
                m_ConcurrencyList.add(0);
                m_KeeperPositionList.add(temp);
                Point pos = m_CurrentLevel.GetPortalDownPosition();
                m_CurrentLevel.MoveGameObjectTo(keeper, keeperPosition, pos);
                m_CurrentLevel.PutObjectAt(GameObject.FLOOR, temp);
                m_CurrentLevel.SetKeeperPosition(pos);
                currentKeeperPosition = keeperPosition;
                keeperMoved = true;
                break;

            case PORTALDOWN:
                onPortalUp = true;
                m_ConcurrencyList.add(0);
                m_KeeperPositionList.add(temp);
                Point position = m_CurrentLevel.GetPortalUpPosition();
                m_CurrentLevel.MoveGameObjectTo(keeper, keeperPosition, position);
                m_CurrentLevel.PutObjectAt(GameObject.FLOOR, temp);
                m_CurrentLevel.SetKeeperPosition(position);
                currentKeeperPosition = keeperPosition;
                keeperMoved = true;
                break;

            case BOMB:
                undo(); undo(); undo(); undo(); undo();
                break;

            default:
                m_Logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }
        if (keeperMoved) {
            keeperPosition.translate((int) delta.getX(), (int) delta.getY());
            m_MovesCount++;
            if (m_CurrentLevel.IsComplete()) {
                if (IsDebugActive()) {System.out.println("Level complete!");}
                m_CurrentMovesCount = GetMovesCount() - GetLastMovesCount();
                SetLastMovesCount(GameEngine.GetMovesCount());
                m_LevelTime = GamePageModel.GetMins() * 60 + GamePageModel.GetSecs();
                SetTotalTime(GetTotalTime() + m_LevelTime);
                if (FileHandler.JudgeRanked(GetCurrentLevel().GetIndex())){
                    GameDialog.ShowNameDialog();
                    FileHandler.WriteFile(GetUsername(), m_LevelTime, m_CurrentMovesCount, GetCurrentLevel().GetIndex());
                }
                FileHandler.ReadFile(GetCurrentLevel().GetIndex());
                ScoreBoardAdapter.CreateNormalBoard();
                FileHandler.scorelist = new ArrayList<>();
                m_CurrentLevel = GetNextLevel();
            }
        }
    }

    public static void ShowScores() {
        FileHandler.ReadFile(GetCurrentLevel().GetIndex());
        ScoreBoardAdapter.CreateNormalBoard();
    }

    /**
     * This method is called when the player wants to undo his last move. It will
     * decrease the {@code movesCount} by 1 and move the keeper to the last position
     * in {@code keeperPositionList}. And if the crate also been moved last step, 
     * it will be moved to the current position of the keeper
     * 
     * @see Level
     * @see Point
     * @see GameObject
     * @see #GetMovesCount()
     * @see GameCore
     */
    public void undo() {
    	int movesCount = GetMovesCount();
        if (m_KeeperPositionList.size() == 0) {
    		System.out.print("Nothing to do!");
    	} else
    	{
    		Level currentLevel = GetCurrentLevel();
        	Point currentPosition = currentKeeperPosition;
            Point formerPosition = m_KeeperPositionList.get(m_KeeperPositionList.size() - 1);
            m_KeeperPositionList.remove(m_KeeperPositionList.size()-1); 
            GameObject keeper = currentLevel.GetObjectAt(currentPosition);
            currentLevel.MoveGameObjectTo(keeper, currentPosition, formerPosition);
            currentLevel.SetKeeperPosition(formerPosition);
            currentKeeperPosition = formerPosition;
   
            if (m_CratePositionList.size() > 0 && m_ConcurrencyList.get(m_ConcurrencyList.size()-1) == 1) {
            	Point currentCratePosition = m_CratePositionList.get(m_CratePositionList.size()-1);
                GameObject crate = currentLevel.GetObjectAt(currentCratePosition);
                currentLevel.MoveGameObjectTo(crate, currentCratePosition, currentPosition);
                m_CratePositionList.remove(m_CratePositionList.size()-1);
            }
            
            m_ConcurrencyList.remove(m_ConcurrencyList.size()-1);
            GameCore.ReloadGrid();
        	SetMovesCount(movesCount-1);
        	GamePageModel.NumberProperty().set(String.valueOf(GetMovesCount()));
    	}	
    }

    /**
     * This method is called when the player wants to reset the current level,
     * it will help restore the map to the initial look of this level.
     * 
     * @see Level
     * @see GamePageModel#NumberProperty()
     * @see GameCore#ReloadGrid()
     * @see #SetCurrentLevel(Level)
     */
    public void resetLevel() {
    	Level currentLevel = GetCurrentLevel();
    	int index = currentLevel.GetIndex();
        m_InputStream = getClass().getClassLoader().getResourceAsStream("display/SampleGame.skb");
        GameEngine engine = (GameEngine) EngineFactory.Manufacture("GameEngine");
        assert engine != null;
        SetCurrentLevel(engine.levels.get(index-1));
    	SetMovesCount(GetLastMovesCount());
    	GamePageModel.NumberProperty().set(String.valueOf(GetMovesCount()));
    	GameCore.ReloadGrid();
    }

    /**
     * Judge whether the game has been completed, if the game is completed,
     * return{@code true}, otherwise, return{@code false}.
     *
     * @return the value of {@code gameComplete}
     */
    public boolean IsGameComplete() {return m_GameComplete;}

    /**
     * Get the next level of the game. If current level doesn't exist, then
     * get the first level; If the current level is not the last level, then
     * get the next level; If the current level is the last level, then change
     * the value of {@code gameComplete} to {@code true} and return {@code null}.
     * 
     * @return the first level if current level is {@code null}; the next level if
     * 				  the current level is not the last level; otherwise return {@code null}
     *
     * @see Level#GetIndex()
     */
    public Level GetNextLevel() {
        if (m_CurrentLevel == null) {return levels.get(0);}
        int currentLevelIndex = m_CurrentLevel.GetIndex();
        if (currentLevelIndex < levels.size()) {
            return levels.get(currentLevelIndex);
        }
        
        m_GameComplete = true;
        return null;
    }

    /**
     * Toggle the status of {@code debug}.
     */
    public void ToggleDebug() {m_Debug = !m_Debug;}

}