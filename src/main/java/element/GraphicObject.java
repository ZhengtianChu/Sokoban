package element;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * This class is used to Fill up the map with different colors or images
 * for different objects.
 * 
 * @author Zhengtian CHU - modified
 * @since 1.0
 */
public class GraphicObject extends Rectangle {
	private static Paint m_WallColor;
	
	public static Paint GetWallColor() {return m_WallColor;}

	public static void SetWallColor(String wallColor) {
		switch(wallColor) {
		case "Black":
			GraphicObject.m_WallColor = Color.BLACK;
			break; 
		case "Green":
			GraphicObject.m_WallColor = Color.GREEN;
			break;
		case "Blue":
			GraphicObject.m_WallColor = Color.BLUE;
			break;
		case "Grey":
			GraphicObject.m_WallColor = Color.GREY;
			break;
		case "Yellow":
			GraphicObject.m_WallColor = Color.YELLOW;
			break;
		case "Pink":
			GraphicObject.m_WallColor = Color.PINK;
			break;
		case "Purple":
			GraphicObject.m_WallColor = Color.PURPLE;
			break;
		case "Blueviolet":
			GraphicObject.m_WallColor = Color.BLUEVIOLET;
			break;
		case "Brown":
			GraphicObject.m_WallColor = Color.BROWN;
			break;
		}
	}

	/**
	 * The constructor of the {@code GraphicOnbject}. It will fill different
	 * object with different images or colors.
	 *
	 * @param obj the GameObject
	 * @see Paint
	 * @see Image
	 */
    public GraphicObject(GameObject obj) {
        Paint color;
        this.setArcHeight(10);
        this.setArcWidth(10);
        switch (obj) {
            case WALL: 
                color = GetWallColor();
                if (color == Color.BLACK) {
                	Image blackWall = new Image(this.getClass().getResourceAsStream("blackWall.png"));
                    this.setFill(new ImagePattern(blackWall));
                    blackWall = null;
                } else if(color == Color.GREY){
                	Image greyWall = new Image(this.getClass().getResourceAsStream("greyWall.png"));
                    this.setFill(new ImagePattern(greyWall));
                    greyWall = null;
                } else if(color == Color.BROWN) {
                	Image brownWall = new Image(this.getClass().getResourceAsStream("brownWall.png"));
                    this.setFill(new ImagePattern(brownWall));
                    brownWall = null;
                } else if(color == Color.YELLOW) {
                	Image yellowWall = new Image(this.getClass().getResourceAsStream("yellowWall.png"));
                    this.setFill(new ImagePattern(yellowWall));
                    yellowWall = null;
                } else {
                	this.setFill(color);
                }        
                break;

            case CRATE:
                Image boxImage = new Image(this.getClass().getResourceAsStream("brownBox.png"));
                this.setFill(new ImagePattern(boxImage));
                boxImage = null;
                break;

            case DIAMOND:
            	Image diamondImage = new Image(this.getClass().getResourceAsStream("diamond.png"));
                this.setFill(new ImagePattern(diamondImage));
				diamondImage = null;

                if (GameEngine.IsDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }
                break;
			case STAR:
				Image starImage = new Image(this.getClass().getResourceAsStream("star.jpg"));
				this.setFill(new ImagePattern(starImage));
				break;
			case PORTALUP:

			case PORTALDOWN:
				Image portalImage = new Image(this.getClass().getResourceAsStream("portal.png"));
				this.setFill(new ImagePattern(portalImage));
				break;

			case BOMB:
				Image bombImage = new Image(this.getClass().getResourceAsStream("bomb.jpg"));
				this.setFill(new ImagePattern(bombImage));
				break;

			case KEEPER:
            	this.setFill(new ImagePattern(keeperImage()));
                break;

            case FLOOR:
            	Image floorImage = new Image(this.getClass().getResourceAsStream("yellowfloor.png"));
                this.setFill(new ImagePattern(floorImage));
                floorImage = null;
                break;

            case CRATE_ON_DIAMOND:
            	Image finishImage = new Image(this.getClass().getResourceAsStream("crateOnDiamond.png"));
                this.setFill(new ImagePattern(finishImage));
                finishImage = null;
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameEngine.m_Logger.severe(message);
                throw new AssertionError(message);
        }
        this.setHeight(30);
        this.setWidth(30);     
        if (GameEngine.IsDebugActive()) {
            this.setStroke(Color.RED);
            this.setStrokeWidth(0.25);
        } 
    }

	/**
	 * This method provide different keeper pictures for different direction
	 *
	 * @return the image for keeper
	 * @see GameEngine#GetDirection()
	 * @see Image
	 */
	private Image keeperImage() {
		switch(GameEngine.GetDirection()) {
		case 1:
			return new Image(this.getClass().getResourceAsStream("keeperGoUp1.png"));
		case 3:
			return new Image(this.getClass().getResourceAsStream("keeperGodown1.png"));
		case 2:
			return new Image(this.getClass().getResourceAsStream("keeperGoRight.png"));
		case 4:
			return new Image(this.getClass().getResourceAsStream("keeperGoLeft.png"));
		case 5:
			return new Image(this.getClass().getResourceAsStream("keeperGoUp2.png"));
		case 6:
			return new Image(this.getClass().getResourceAsStream("keeperGoDown2.png"));
		case 7:
			return new Image(this.getClass().getResourceAsStream("keeperStandUp.png"));
		case 8:
			return new Image(this.getClass().getResourceAsStream("keeperStandRight.png"));
		case 9:
			return new Image(this.getClass().getResourceAsStream("keeperStandDown.png"));
		case 10:
			return new Image(this.getClass().getResourceAsStream("keeperStandLeft.png"));
		case 11:
			return new Image(this.getClass().getResourceAsStream("diamond.png"));
		default:
			return new Image(this.getClass().getResourceAsStream("keeperStandDown.png"));
		}
	}

}
