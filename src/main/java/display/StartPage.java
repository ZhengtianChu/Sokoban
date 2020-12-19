package display;

import interact.controller.GamePageController;
import interact.model.GamePageModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;  
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This is the Main class of this project, this class can start
 * the start page of whole game.
 * @author Zhengtian CHU
 * @since 1.0
 *
 * @see GamePageModel
 * @see GamePageController
 * @see Scene
 * @see Stage
 */
public class StartPage extends Application {

	 public static void main(String[] args) {
	     launch(args);
	     System.out.println("Done!");
	 }
	 
	 @Override
	 public void start(Stage primaryStage) throws Exception {	
	     Parent parent = FXMLLoader.load(getClass().getResource("StartPageView.fxml"));
	     Scene scene = new Scene(parent, 608, 660);
		 scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getClassLoader().getResource("display/style.css")).toExternalForm());
		  
	     primaryStage.setTitle("SOKOBAN");
	     primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
	     primaryStage.setScene(scene);
	     primaryStage.setResizable(false);
	     primaryStage.show(); 
	 } 
} 
     