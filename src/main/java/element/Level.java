package element;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static element.GameGrid.TranslatePoint;

/**
 * The class {@code Level} contains some methods about the 
 * level in this game.
 * 
 * @author Zhengtian CHU - modified
 * @since 1.0
 */
public final class Level implements Iterable<GameObject> {
 
    private final String name;
    private final GameGrid objectsGrid; 
    private final GameGrid diamondsGrid;

    private final int index; 
    private int numberOfDiamonds = 0;
    private Point keeperPosition;
    private Point portalUpPosition;
    private Point portalDownPosition;

    public String GetName() {return name;}

    public int GetIndex() {return index;}

    public Point GetKeeperPosition() {return keeperPosition;}

    public void SetKeeperPosition(Point keeper) {this.keeperPosition = keeper;}

    public Point GetPortalDownPosition() { return portalDownPosition; }

    public Point GetPortalUpPosition() { return portalUpPosition; }

    /**
     * Constructs the map for specified string list{@code raw_level} which contains 
     * the symbol of game objects, save the objects in two GameGrids. The diamonds
     * will be stored in {@code diamondsGrid} and others will be stored in {@code objectGrid}.
     * 
     * @param levelName the name of level
     * @param levelIndex the index of level
     * @param raw_level the string list of game map
     * 
     * @see GameEngine#IsDebugActive()
     * @see GameGrid#GameGrid(int, int)
     * @see GameGrid#PutGameObjectAt(GameObject, int, int)
     * @see GameObject#FromChar(char)
     * 
     */
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        if (GameEngine.IsDebugActive()) {System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);}
        
        name = levelName;
        index = levelIndex;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        objectsGrid = new GameGrid(rows, columns);
        diamondsGrid = new GameGrid(rows, columns);

        for (int row = 0; row < raw_level.size(); row++) {

            // Loop over the string one char at a time because it should be the fastest way:
            // http://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
            for (int col = 0; col < raw_level.get(row).length(); col++) {
                // The game object is null when the we're adding a floor or a diamond
                GameObject curTile = GameObject.FromChar(raw_level.get(row).charAt(col));

                if (curTile == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.PutGameObjectAt(curTile, row, col);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.KEEPER) {
                    keeperPosition = new Point(row, col);
                } else if (curTile == GameObject.PORTALUP){
                    portalUpPosition = new Point(row, col);
                } else if (curTile == GameObject.PORTALDOWN){
                    portalDownPosition = new Point(row, col);
                }
                objectsGrid.PutGameObjectAt(curTile, row, col);
                curTile = null;
            }
        }
    }

    /**
     * Judge whether this level is completed, use {@code cratedDiamondsCount}
     * to store the completed diamonds, if the value of it is equals or larger than
     * the {@code numberOfDiamonds}, then the level is completed.
     * 
     * @return	{@code true} if the level is completed;  otherwise return {@code false}.
     * @see	 GameGrid#GetGameObjectAt(int, int)
     *  
     */
    public boolean IsComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                if (objectsGrid.GetGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.GetGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }
        return cratedDiamondsCount >= numberOfDiamonds;
    }


    /**
     * This method is called when user wants to save the game grid. It puts the object
     * in {@code level} to an empty grid.
     *
     * @param level the input level
     * @return a game grid with objects in the level
     * @see LevelIterator
     * @see GameGrid
     */
    public static GameGrid SaveGrid(Level level) {
    	int rows = level.objectsGrid.ROWS;
    	int columns = level.objectsGrid.COLUMNS;
    	GameGrid grid = new GameGrid(columns, rows);
    	Level.LevelIterator gridIterator = (LevelIterator) level.iterator();
    	for (int i = 0; i < rows; i++) {
    		for(int j = 0; j < columns; j ++) {
    			grid.PutGameObjectAt(gridIterator.next(), j, i);
    		}
    	}
    	return grid;
    }

    /**
     * Get the object after moving into the target position by
     * {@link GameGrid#getTargetFromSource(Point, Point)}.
     * 
     * @param source the start position
     * @param delta the position variation
     * @return  the target object after moving.
     * @see GameGrid#getTargetFromSource(Point, Point)
     */
    GameObject GetTargetObject(Point source, Point delta) {return objectsGrid.getTargetFromSource(source, delta);}

    /**
     * Get the object of specific point
     * 
     * @param p the point of the object
     * @return the object on the {@code p}
     */
    public GameObject GetObjectAt(Point p) {
        return objectsGrid.GetGameObjectAt(p);
    }

    /**
     * Put the specific object at specified position
     * @param obj specified object
     * @param p specified position
     */
    public void PutObjectAt(GameObject obj, Point p){
        objectsGrid.PutGameObjectAt(obj, p);
    }

    /**
     * Get the destination position from source position by using 
     * {@link GameGrid#TranslatePoint(Point, Point)},
     * and then move the specified object by using
     * {@link Level#MoveGameObjectTo(GameObject, Point, Point)}.
     * 
     * @param object		 a specified position
     * @param source		 the source position
     * @param delta		the position variation
     * 
     * @see Level#MoveGameObjectTo(GameObject, Point, Point)
     */
    public void MoveGameObjectBy(GameObject object, Point source, Point delta) {MoveGameObjectTo(object, source, TranslatePoint(source, delta));}
 
    /**
     *  Put the object at the {@code destination} to the {@code source} position
     *  and put the specified {@code object} to the  {@code destination} by
     *  using {@link GameGrid#PutGameObjectAt(GameObject, Point)}.
     *  
     * @param object		a specified object
     * @param source		 the source position
     * @param destination 	 the destination position
     */
    public void MoveGameObjectTo(GameObject object, Point source, Point destination) {
        if (objectsGrid.GetGameObjectAt(destination) != GameObject.PORTALDOWN &&
                objectsGrid.GetGameObjectAt(destination) != GameObject.PORTALUP){
            objectsGrid.PutGameObjectAt(GetObjectAt(destination), source);
        } else{
            objectsGrid.PutGameObjectAt(GameObject.FLOOR, source);
        }
        objectsGrid.PutGameObjectAt(object, destination);
    }

    @Override
    public String toString() {return objectsGrid.toString();}

    @Override
    public Iterator<GameObject> iterator() {return new LevelIterator();}



    /** 
     * The class {@code LevelIterator} represents a class for iterating the level.
     * 
     * @author Zhengtian CHU
     */
    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        @Override
        public boolean hasNext() {return !(row == objectsGrid.ROWS - 1 && column == objectsGrid.COLUMNS);}

        @Override
        public GameObject next() {
            if (column >= objectsGrid.COLUMNS) {
                column = 0;
                row++;
            }

            GameObject object = objectsGrid.GetGameObjectAt(column, row);
            GameObject diamond = diamondsGrid.GetGameObjectAt(column, row);
            GameObject retObj = object;

            column++;

            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                } else {
                    retObj = object;
                }
            }

            return retObj;
        }

        /**
         * Get the current position in the grid
         *
         * @return the current position
         */
        public Point GetCurrentPosition() {return new Point(column, row);}
    }
}