package element;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameObjectTest {
	GameObject obj = GameObject.WALL;

	@Test
	public void testGameObject() {
		GameObject obj = GameObject.CRATE;	
		assertEquals('C', obj.GetCharSymbol());
	}

	@Test
	public void testFromChar() {
		assertEquals(GameObject.DIAMOND, GameObject.FromChar('d'));
		assertEquals(GameObject.WALL, GameObject.FromChar('p'));
	}

	@Test
	public void testGetStringSymbol() {
		assertEquals("W", obj.GetStringSymbol());
	}

	@Test
	public void testGetCharSymbol() {
		assertEquals('W', obj.GetCharSymbol());
	}

}
