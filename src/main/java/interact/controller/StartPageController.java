package interact.controller;

import java.net.URL;
import java.util.ResourceBundle;

import display.GamePage;
import element.GraphicObject;
import element.GameEngine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartPageController implements Initializable{

	ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private Button startButton;

    @FXML
    private Button quitButton;

    @FXML
    private ChoiceBox<String> wallcolor;    

    @FXML
    private TextField username;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	loadData();
    }
  
    private void loadData() {
    	list.removeAll(list);
    	String a1 = "Black";
    	String a2 = "Green"; 
    	String a3 = "Blue";
    	String a4 = "Grey";
    	String a5 = "Yellow";
    	String a6 = "Pink";
    	String a7 = "Purple";
    	String a8 = "Blueviolet";
    	String a9 = "Brown";
    	list.addAll(a1, a2, a3, a4, a5, a6, a7, a8, a9);
    	wallcolor.getItems().addAll(list);
    }

    @FXML
    void startGame(ActionEvent event) throws Exception {
    	String color=wallcolor.getSelectionModel().getSelectedItem();
    	GraphicObject.SetWallColor(color);
    	GamePage newGame = new GamePage();
    	Stage GameStage = (Stage) startButton.getScene().getWindow();
    	newGame.start(GameStage);
    }

    @FXML
    void quitGame(ActionEvent event) {
    	System.out.println("Bye");
    	System.exit(0);
    }

}
