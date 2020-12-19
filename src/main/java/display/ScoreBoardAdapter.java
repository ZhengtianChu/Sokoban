package display;

import interact.model.GamePageModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This file represents an adapter pattern for the score board in this
 * game. In this game, we have to create two kinds of score boards, that
 * is the score board after completing each level of the game {@link NormalScoreBoard}
 * and the score board in the final {@link FinalScoreBoard}. They are both
 * board but have something different in details, some I created a Adapter
 * for them. Thus, users can create different board through it, the boards
 * are both implements the interface {@link Board}.
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
public class ScoreBoardAdapter
{
	public static void CreateFinalBoard() {
		Board board = new FinalAdapter();
		board.Show();
	}

	public static void CreateNormalBoard() {
		Board board = new NormalAdapter();
		board.Show();
	}
}

/**
 * The interface of boards
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
interface Board
{
  void CreateView();
  void Show();
}

/**
 *  The class of final score board.
 *
 * @author Zhengtian CHU
 * @since 2.0
 * @see Stage
 * @see Text
 */
class FinalScoreBoard
{
	  Stage scoreBoard = new Stage();

	/**
	 * The constructor of final score board
	 */
	public FinalScoreBoard() {
		  CreateFinalView();
	  }

	/**
	 * This function creates the view of score board
	 *
	 * @see Stage
	 * @see Scene
	 */
	public void CreateFinalView()
	  {       
	  	scoreBoard.initOwner(GamePageModel.GetPrimaryStage());
	  	scoreBoard.setTitle("Final Score");
	  	scoreBoard.setResizable(false);

	  	Parent parent = null;
	  	try {
	  		parent = FXMLLoader.load(getClass().getResource("ScoreBoardView.fxml"));
	  	} catch (IOException e) {
	  		e.printStackTrace();
	  	}
	  	assert parent != null;
	  	Scene scene = new Scene(parent, 480, 600);
	  	scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getClassLoader().getResource("display/style2.css")).toExternalForm());
	  	scoreBoard.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
	  	scoreBoard.setScene(scene);
	  }

	/**
	 * show the stage
	 */
	public void Show() {
		  scoreBoard.show();
	  }
}

/**
 *  The class of score board after finishing each level.
 *
 * @author Zhengtian CHU
 * @since 2.0
 * @see Stage
 * @see Text
 */
class NormalScoreBoard
{
	Stage scoreBoard = new Stage();

	/**
	 * The constructor of normal score board
	 */
	public NormalScoreBoard() {
		  CreateNormalView();
	}

	/**
	 * This function creates the view of score board
	 *
	 * @see Stage
	 * @see Scene
	 */
	public void CreateNormalView()
	{       
		scoreBoard.initOwner(GamePageModel.GetPrimaryStage());
		scoreBoard.setTitle("High Score");
		scoreBoard.setResizable(false);
		scoreBoard.setAlwaysOnTop(true);
		scoreBoard.isAlwaysOnTop();

		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("ScoreBoardView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		assert parent != null;
		Scene scene = new Scene(parent, 470, 600);
		scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getClassLoader().getResource("display/style.css")).toExternalForm());
		scoreBoard.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
		scoreBoard.setScene(scene);
	}

	/**
	 * show the stage
	 */
	public void Show() {
		scoreBoard.show();
	}
}

/**
 * This class is the adapter of the final score board
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
class FinalAdapter implements Board
{
	private FinalScoreBoard finalBoard;
	public FinalAdapter() {
		finalBoard = new FinalScoreBoard();
	}
	
	@Override
	public void CreateView() {
		finalBoard.CreateFinalView();
	}
	
	@Override
	public void Show() {
		finalBoard.Show();
	}
}

/**
 * This class is the adapter of the normal score board
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
class NormalAdapter implements Board
{
	private NormalScoreBoard normalBoard;
	public NormalAdapter() {
		normalBoard = new NormalScoreBoard();
	}

	@Override
	public void CreateView() {
		normalBoard.CreateNormalView();
	}

	@Override
	public void Show() {
		normalBoard.Show();
	}

}


