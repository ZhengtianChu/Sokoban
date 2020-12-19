package element;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class LevelTest {
	static List<String> stringList = new ArrayList<String>();
	static Level level1;
	@BeforeClass
	public static void beforeClass() {
		stringList.add("WWWWWWWWWWWWWWWWWWWW");
		stringList.add("W    W             W");
		stringList.add("W C  W D           W");
		stringList.add("w    w      WWWWWWWW");
		stringList.add("w    WWWW  WWWWWWWWW");
		stringList.add("w            WWWWWWW");
		stringList.add("w    WWWWW   WWWWWWW");
		stringList.add("w    WWWWWWWWWWWWWWW");
		stringList.add("w    WWWWWWWWWWWWWWW");
		stringList.add("W    WWWWWWWWWWWWWWW");
		stringList.add("W    WWWWWWWWWWWWWWW");
		stringList.add("w           WWWWWWWW");
		stringList.add("w       W WWWWWWWWWW");
		stringList.add("wWWWWWW W WWWWWWWWWW");
		stringList.add("wWWWWWW W WWWWWWWWWW");
		stringList.add("wWWWWWW W WWWWWWWWWW");
		stringList.add("wWWWWWW W WWWWWWWWWW");
		stringList.add("wWWWWWW W WWWWWWWWWW");
		stringList.add("wWWWWWW   SWWWWWWWWW");
		stringList.add("wwwwwwwwWwwwwwwwwwww");
		level1 = new Level("start", 0, stringList);
	}

	@Test
	public void testIsComplete() {
		assertFalse(level1.IsComplete());
	}

	@Test
	public void testGetName() {
		assertEquals("start", level1.GetName());
	}

	@Test
	public void testGetIndex() {
		assertEquals(0, level1.GetIndex());
	}

	@Test
	public void testGetKeeperPosition() {
		Point position = new Point(18,10);
		assertEquals(position, level1.GetKeeperPosition());
	}

	@Test
	public void testSetKeeperPosition() {
		Point position = new Point(18,8);
		level1.SetKeeperPosition(position);
		assertEquals(position, level1.GetKeeperPosition());
	}

	@Test
	public void testGetTargetObject() {
		Point source = new Point(18, 8);
		Point delta = new Point(0, 2);
		GameObject obj = level1.GetTargetObject(source, delta);
		assertEquals(GameObject.KEEPER, obj);
	}

	@Test
	public void testGetObjectAt() {
		assertEquals(GameObject.CRATE, level1.GetObjectAt(new Point(2, 2)));
	}

	@Test
	public void testMoveGameObjectBy() {
		Point source = new Point(18, 9);
		Point delta = new Point(0, 1);
		level1.MoveGameObjectBy(GameObject.FLOOR, source, delta);
		assertEquals(GameObject.KEEPER, level1.GetObjectAt(source));
	}

	@Test
	public void testMoveGameObjectTo() {
		Point source = new Point(18, 10);
		Point destination = new Point(18, 9);
		level1.MoveGameObjectTo(GameObject.KEEPER, source, destination);
		assertEquals(GameObject.KEEPER, level1.GetObjectAt(destination));
	}

	@Test
	public void testToString() {
		String string = "WWWWWWWWWWWWWWWWWWWW\n"
				+ "W    W             W\n"
				+ "W C  W             W\n"
				+ "W    W      WWWWWWWW\n"
				+ "W    WWWW  WWWWWWWWW\n"
				+ "W            WWWWWWW\n"
				+ "W    WWWWW   WWWWWWW\n"
				+ "W    WWWWWWWWWWWWWWW\n"
				+ "W    WWWWWWWWWWWWWWW\n"
				+ "W    WWWWWWWWWWWWWWW\n"
				+ "W    WWWWWWWWWWWWWWW\n"
				+ "W           WWWWWWWW\n"
				+ "W       W WWWWWWWWWW\n"
				+ "WWWWWWW W WWWWWWWWWW\n"
				+ "WWWWWWW W WWWWWWWWWW\n"
				+ "WWWWWWW W WWWWWWWWWW\n"
				+ "WWWWWWW W WWWWWWWWWW\n"
				+ "WWWWWWW W WWWWWWWWWW\n"
				+ "WWWWWWW   SWWWWWWWWW\n"
				+ "WWWWWWWWWWWWWWWWWWWW\n";
		System.out.print(level1.toString());
		assertEquals(string, level1.toString());
	}
}
