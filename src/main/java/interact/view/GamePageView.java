package interact.view;

import element.GameEngine;
import interact.controller.GamePageController;
import interact.model.GamePageModel;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

public class GamePageView {
	private GamePageController controller;
	private GridPane root;
	private static GridPane m_GameGrid;
    private MenuBar MENU;
    private MenuItem menuItemSaveGame;
    private MenuItem menuItemLoadGame;
    private MenuItem menuItemExit;
    private Menu menuFile;
    private MenuItem menuItemUndo;
    private RadioMenuItem radioMenuItemMusic;
    private RadioMenuItem radioMenuItemDebug;
    private MenuItem menuItemResetLevel;
    private Menu menuLevel;
    private MenuItem menuItemGame;
    private MenuItem menuItemScores;
    private Menu menuAbout;
    private Label label; 
    private Text text;
    private Timeline timeline;
    int mins = 0, secs = 0, millis = 0;
    
    public GamePageView(GamePageController controller, GamePageModel model) {
    	this.controller = controller;
    	createAndConfigurePane();
        createAndLayoutControls();
        updateControllerFromListener();
        observeModelAndUpdateControls();
    }
    
    public Parent asParent() {
        return root;
    }

    private void createAndConfigurePane() {
        root = new GridPane();
        m_GameGrid = new GridPane();
    }
    
    private void createAndLayoutControls() {
        MENU = new MenuBar();
        menuItemSaveGame = new MenuItem("Save Game");
        menuItemLoadGame = new MenuItem("Load Game");
        menuItemExit = new MenuItem("Exit");
        menuFile = new Menu("File");
        menuFile.getItems().addAll(menuItemSaveGame, menuItemLoadGame, new SeparatorMenuItem(), menuItemExit);
        menuItemUndo = new MenuItem("Undo");
        radioMenuItemMusic = new RadioMenuItem("Toggle Music");
        radioMenuItemDebug = new RadioMenuItem("Toggle Debug");
        menuItemResetLevel = new MenuItem("Reset Level");     
        menuLevel = new Menu("Level");
        menuLevel.getItems().addAll(menuItemUndo, radioMenuItemMusic, radioMenuItemDebug, new SeparatorMenuItem(), menuItemResetLevel);
        menuItemGame = new MenuItem("About This Game");
        menuItemScores = new MenuItem("Show High Scores");
        menuAbout = new Menu("About");
        menuAbout.getItems().addAll(menuItemGame, menuItemScores);
        MENU.getMenus().addAll(menuFile, menuLevel, menuAbout);      
        
        text = new Text();
        text.setFont(new Font("MV Boli", 15));
        text.setFill(Color.web("#0076a3"));
        
        label = new Label("LEVEL : 1, " + "MOVES : " + GameEngine.GetMovesCount() + ", TIME : " + "00:00:000");
        label.setWrapText(true);
        label.setTextFill(Color.web("#0076a3"));
        label.setFont(new Font("MV Boli", 15));
        
       
        root.add(MENU, 0, 0);
        root.add(m_GameGrid, 0, 1);
        root.add(label, 0, 2);
        root.add(text, 0, 2);
        
        GamePageModel.SetGameGrid(m_GameGrid);
    }

    private void updateControllerFromListener() {
    	 menuItemSaveGame.setOnAction(actionEvent -> controller.SaveGame());
         menuItemLoadGame.setOnAction(actionEvent -> controller.LoadGame());
         menuItemExit.setOnAction(actionEvent -> controller.CloseGame());
         menuItemUndo.setOnAction(actionEvent -> controller.Undo());
         radioMenuItemMusic.setOnAction(actionEvent -> controller.ToggleMusic());
         radioMenuItemDebug.setOnAction(actionEvent -> controller.ToggleDebug());
         menuItemResetLevel.setOnAction(actionEvent -> controller.ResetLevel());
         menuItemGame.setOnAction(actionEvent -> controller.ShowAbout());
         menuItemScores.setOnAction(actionEvent -> controller.ShowScores());
         timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> controller.Change(text)));
 	   
    }
    
    private void observeModelAndUpdateControls() {
        GamePageModel.NumberProperty().addListener((obs,oldX,newX)->updateNumberDisplay());
    }

	private void updateNumberDisplay() {
		int level = 1;
		timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.setAutoReverse(false);
	    timeline.play();
        if(GameEngine.GetCurrentLevel() != null) {
             level = GameEngine.GetCurrentLevel().GetIndex();
        }else{
            return;
        }
		label.setText("LEVEL : " + level + ", MOVES : " + String.valueOf(GameEngine.GetMovesCount()-GameEngine.GetLastMovesCount()) + ",");
	}
    
}
