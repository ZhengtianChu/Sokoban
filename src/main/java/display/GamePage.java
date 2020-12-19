package display;

import interact.controller.GamePageController;
import interact.model.GamePageModel;
import interact.view.GamePageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This is the start class of main Game page.
 * @author Zhengtian CHU
 * @since 1.0
 *
 * @see GamePageModel
 * @see GamePageController
 * @see Scene
 * @see Stage
 */
public class GamePage extends Application{

	 public static void main(String[] args) {
	     launch(args);
	     System.out.println("Done!");
	 }
	 
	 @Override
	 public void start(Stage primaryStage) throws Exception {
		 GamePageModel model = new GamePageModel(primaryStage);
		 GamePageController controller = new GamePageController(model);
		 GamePageView view = new GamePageView(controller, model);
		 Scene scene = new Scene(view.asParent(), 608, 660);
	     
	     primaryStage.setTitle("SOKOBAN");
	     primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
	     primaryStage.setScene(scene);
	     primaryStage.setResizable(false);
	     primaryStage.show();
	     model.LoadDefaultSaveFile();
	 } 
}
