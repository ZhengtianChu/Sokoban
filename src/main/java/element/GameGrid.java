package element;

import java.awt.*;
import java.util.Iterator;

/** 
 * A class {@code GameGrid} represented the method of grid in
 * game map. The methods about the grid of the map in the game
 * are included in this class.
 *
 * @author Zhengtian CHU - modified
 * @since 1.0
 */
public class GameGrid implements Iterable<GameObject> { 

    int COLUMNS;
    int ROWS;
    private GameObject[][] gameObjects;
    
    /** 
     * Constructs and initialize a map of Sokoban with
     * specified columns and specified rows
     * 
     * @param columns the columns of grids
     * @param rows the rows of grids 
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;
        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     * Translate the point from {@code sourceLocation} for
     * {@code delta}.
     * @param sourceLocation the location of source point
     * @param delta the value of position variation
     * 
     * @return the point after translating.
     * @see java.awt.Point#translate(int, int)
     */
    public static Point TranslatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * Returns the dimension of this dimension with
     * the specified columns and the specified rows
     * 
     * @return a copy of this dimension
     * @see java.awt.Dimension#Dimension()
     */
    public Dimension GetDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * Get the game object after translating from the 
     * source location.
     * @param source the source point
     * @param delta the value of point variation
     * 
     * @return the target game object after translating by
     * 				 {@link GameGrid#GetGameObjectAt(Point)}.
     * @see GameGrid#TranslatePoint(Point, Point)
     */
    GameObject getTargetFromSource(Point source, Point delta) {
        return GetGameObjectAt(TranslatePoint(source, delta));
    }

    /**
     * Get the game object in the specified position
     * @param col the specified column
     * @param row the specified row
     *  
     * @return the {@code GameObject} on the specified position
     * @throws ArrayIndexOutOfBoundsException if the position is outside the map
     * @see  GameEngine#IsDebugActive()
     */
    public GameObject GetGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (GameEngine.IsDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }

        return gameObjects[col][row];
    }

    /**
     * Get the game object in the specified position
     *
     * @param p the specified position
     * 
     * @return the {@code GameObject} in the specified position
     * 				 by {@link GameGrid#GetGameObjectAt(int, int)}.
     * @throws IllegalArgumentException if the point is {@code null}.
     */
    public GameObject GetGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }
        return GetGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * Remove the object of specific position
     * @param position the position of object
     * 
     * @return {@code true} if successfully remove
     * 				 the object; otherwise return {@code false}
     * @see GameGrid#PutGameObjectAt(GameObject, Point)
     */
    public boolean RemoveGameObjectAt(Point position) {return PutGameObjectAt(null, position);}


    /**
     * put the {@code gameObject} to the specified position,
     * If the given position is out of the bounds of the map,
     * will return {@code false} else return {@code true}.
     * @param gameObject the object wants to put 
     * @param x the object's column
     * @param y the object's row
     * 
     * @return {@code true} if the {@code GameObject} is successfully put; 
     * 				 otherwise return {@code false}.
     * @see GameGrid#isPointOutOfBounds(int, int)
     */
     public boolean PutGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }
        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * put the {@code gameObject} to the specified position,
     * If point is {@code null},  will return {@code false} 
     * else return {@code true}.
     * 
     * @param gameObject the game object want to put
     * @param p the specified position
     * @return {@code true} if successfully put the object; 
     * 				  otherwise return {@code false}
     */
    public boolean PutGameObjectAt(GameObject gameObject, Point p) {return p != null && PutGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());}

    /**
     * Judge whether the specified x-coordinate and 
     * 	specified y-coordinate is out of bounds
     * @param x the specified column
     * @param y the specified row
     * 
     * @return {@code true} if the specified x-coordinate and 
     * 					y-coordinate is not out of bounds; otherwise
     * 				  return {@code false}.
     */
    private boolean isPointOutOfBounds(int x, int y) {return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);}

    /**
     * Judge whether the specified point is out of bounds
     * @param p the specified point
     * 
     * @return {@code true} if the point {@code p} is out of bound;
     * 				otherwise return {@code false}
     * @see GameGrid#isPointOutOfBounds(int, int)
     */ 
    @SuppressWarnings("unused")
	private boolean isPointOutOfBounds(Point p) {return isPointOutOfBounds(p.x, p.y);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.GetCharSymbol());
            }

            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public Iterator<GameObject> iterator() {return new GridIterator();}

    /**
     * The class {@code GridIterator} represents the methods
     * about the Iterator for the Grid
     * 
     * @author Zhengtian CHU
     * @see GameGrid#GetGameObjectAt(int, int)
     * @since 1.0
     */
    public class GridIterator implements Iterator<GameObject> {
        int row = 0;
        int column = 0;

        @Override
        public boolean hasNext() {
            return !(row == ROWS && column == COLUMNS);
        }

        @Override
        public GameObject next() {
            if (column >= COLUMNS) {
                column = 0;
                row++;
            }
            return GetGameObjectAt(column++, row);
        } 
    }
}