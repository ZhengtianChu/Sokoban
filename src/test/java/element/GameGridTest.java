package element;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Point;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameGridTest {
	private static GameGrid grid;

	@BeforeClass
	public static void beforeClass() {
		grid = new GameGrid(10, 10);
	}

	@Test
	public void testTranslatePoint() {
		Point point = new Point(1,2); 
		Point delta = new Point(3,5);
		Point transPoint = GameGrid.TranslatePoint(point, delta);
		assertEquals(4, transPoint.x);
		assertEquals(7, transPoint.y);
	}

	@Test
	public void testGetDimension() {
		Dimension dimension = new Dimension(10, 10);
		assertEquals(dimension, grid.GetDimension());
	}

	@Test
	public void testGetTargetFromSource() {
		Point source = new Point(1, 1);
		Point delta = new Point(2, 2);
		grid.PutGameObjectAt(GameObject.WALL, 3, 3);
		assertEquals(GameObject.WALL, grid.getTargetFromSource(source, delta));
	}

	@Test
	public void testGetGameObjectAtIntInt() {
		try {
			grid.GetGameObjectAt(12, 12);
		}catch (ArrayIndexOutOfBoundsException ex) {
			assertTrue(ex.getMessage().contains("The point [12:12] is outside the map."));
		}
		
		grid.PutGameObjectAt(GameObject.CRATE_ON_DIAMOND, 7, 5);
		GameObject obj = grid.GetGameObjectAt(new Point(7,5));
		assertEquals(GameObject.CRATE_ON_DIAMOND, obj);
	}

	@Test
	public void testGetGameObjectAtPoint() {
		try {
			grid.GetGameObjectAt(null);
		}catch(IllegalArgumentException ex) {
			assertTrue(ex.getMessage().contains("Point cannot be null."));
		}		
		
		grid.PutGameObjectAt(GameObject.WALL, 1, 2);
		GameObject obj = grid.GetGameObjectAt(new Point(1,2));
		assertEquals(GameObject.WALL, obj);
	}

	@Test
	public void testRemoveGameObjectAt() {
		Point point = new Point(1, 1);
		grid.PutGameObjectAt(GameObject.FLOOR, point);
		assertTrue(grid.RemoveGameObjectAt(point));
	}

	@Test
	public void testPutGameObjectAtGameObjectIntInt() {
		grid.PutGameObjectAt(GameObject.FLOOR, 8, 8);
		assertEquals(GameObject.FLOOR, grid.GetGameObjectAt(8, 8));

		assertFalse(grid.PutGameObjectAt(GameObject.FLOOR, 12, 8));
	}

	@Test
	public void testPutGameObjectAtGameObjectPoint() {
		Point point = new Point(5, 5);
		grid.PutGameObjectAt(GameObject.CRATE, point);
		assertEquals(GameObject.CRATE, grid.GetGameObjectAt(point));
	}
}
