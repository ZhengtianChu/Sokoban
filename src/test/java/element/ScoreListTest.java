package element;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScoreListTest {
	ScoreList scorelist = new ScoreList("Jack", 10, 60);

	@Test
	public void testGetUsername() {
		assertEquals("Jack", scorelist.GetUsername());
	}

	@Test
	public void testSetUsername() {
		scorelist.SetUsername("Harry");
		assertEquals("Harry", scorelist.GetUsername());
	}

	@Test
	public void testGetTime() {
		assertEquals(10, scorelist.GetTime());
	}

	@Test
	public void testSetTime() {
		scorelist.SetTime(20);
		assertEquals(20, scorelist.GetTime());
	}

	@Test
	public void testGetMovesCount() {
		assertEquals(60, scorelist.GetMovesCount());
	}

	@Test
	public void testSetMovesCount() {
		scorelist.SetMovesCount(50);
		assertEquals(50, scorelist.GetMovesCount());
	}
}
