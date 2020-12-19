package element;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.KeyCode;

public class GameEngineTest {
	static GameEngine engine;
	@Before
	public void before() {
		String file = "display/SampleGame.skb";   
    	InputStream in = getClass().getClassLoader().getResourceAsStream(file);
    	GameEngine.SetInputStream(in);
    	engine = (GameEngine) EngineFactory.Manufacture("GameEngine"); 
	}
	
	@Test
	public void testMove() {
        engine.Move(new Point(0,-1));
        assertEquals(new Point(18,9),GameEngine.GetCurrentLevel().GetKeeperPosition());
        GameEngine.SetMovesCount(0);
	}
	
	@Test
	public void testHandleKey() {
    	System.out.println("currentKeeper position:"+GameEngine.GetCurrentLevel().GetKeeperPosition());
    	engine.HandleKey(KeyCode.LEFT);
    	engine.HandleKey(KeyCode.LEFT);
        assertEquals(new Point(11,5),GameEngine.GetCurrentLevel().GetKeeperPosition());
        engine.HandleKey(KeyCode.DOWN);
        assertEquals(new Point(12,5),GameEngine.GetCurrentLevel().GetKeeperPosition());
	}

}
