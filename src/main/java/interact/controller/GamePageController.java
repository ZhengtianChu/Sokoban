package interact.controller;

import java.io.IOException;

import interact.model.GamePageModel;
import javafx.scene.text.Text;

public class GamePageController {

	private GamePageModel model;

    public GamePageController(GamePageModel model) {
        this.model=model;
    }

    public void SaveGame() {
    	try {
			model.SaveGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void LoadGame() {
		model.LoadGame();
    }
	
    public void CloseGame() {
        System.exit(0);
    }
    
    public void Undo() {
    	model.Undo();
    }

	public void ToggleMusic() { model.ToggleMusic(); }

	public void ToggleDebug() {
		model.ToggleDebug();
	}

	public void ResetLevel() {
		model.ResetLevel();
	}

	public void ShowAbout() {
		model.ShowAbout();
	}

    public void Change(Text text) {
    	model.Change(text);
    }

    public void ShowScores() {model.ShowScores();}

}
