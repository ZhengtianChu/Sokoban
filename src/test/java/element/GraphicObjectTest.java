package element;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphicObjectTest {
	@BeforeClass
	public static void beforeClass() {
		GraphicObject.SetWallColor("Black");
	}

	@Test
	public void testGetWallColor() {
		assertEquals((Paint)Color.BLACK, GraphicObject.GetWallColor());
	}

	@Test
	public void testSetWallColor() {
		GraphicObject.SetWallColor("Green");
		assertEquals((Paint)Color.GREEN, GraphicObject.GetWallColor());
	}

}
