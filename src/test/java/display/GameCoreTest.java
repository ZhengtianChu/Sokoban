package display;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import element.GameObject;
import element.Level;

public class GameCoreTest {	
	List<Level> list;
	@Test
	public void testGetMapSetName() {
		GameCore.SetMapSetName("hard");
		assertEquals("hard", GameCore.GetMapSetName());
	}

	@Test
	public void testSetMapSetName() {
		GameCore.SetMapSetName("easy");
		assertEquals("easy", GameCore.GetMapSetName());
	}
	
	@Test
	public void testLoadGameFile() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("display/SampleGame.skb");
		list = GameCore.LoadGameFile(input);
		assertEquals("Simple Start", list.get(0).GetName());
		Point position = new Point(17,16);
		assertEquals(position, list.get(2).GetKeeperPosition());
		assertEquals(GameObject.CRATE, list.get(3).GetObjectAt(position));
	}
}
