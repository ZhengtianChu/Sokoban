package interact.controller;

import display.FileHandler;
import element.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoardController implements Initializable {

    @FXML
    private Button nextButton;

    @FXML
    private Text text2;

    @FXML
    private Text rank;

    @FXML
    private Text name;

    @FXML
    private Text time;

    @FXML
    private Text moves;

    @FXML
    private Button quitButton2;

    /**
     * This method load the data from {@code scorelist} and put top 10 scores
     * them into {@code string}. If the user is in the score list, note it.
     *
     * @return a string storing top 10 scores
     * @see FileHandler#scorelist
     * @see GameEngine
     */
    private void loadList() {
        int rankNum = 0;
        StringBuilder nameString = new StringBuilder();
        StringBuilder timeString = new StringBuilder();
        StringBuilder movesString = new StringBuilder();
        StringBuilder rankString = new StringBuilder();
        int size = FileHandler.scorelist.size();
        if (size > 10) {
            size = 10;
        }
        for (int i = 0; i < size; i ++) {
            if (rankNum == 0){
                if (GameEngine.GetUsername().equals(FileHandler.scorelist.get(i).GetUsername())){
                    rankNum = i + 1;
                }
            }
            nameString.append(FileHandler.scorelist.get(i).GetUsername()).append('\n');
            timeString.append(FileHandler.scorelist.get(i).GetTime()).append("s").append('\n');
            movesString.append(FileHandler.scorelist.get(i).GetMovesCount()).append("\n");
            rankString.append(i + 1).append("\n");
        }
        name.setText(nameString.toString());
        time.setText(timeString.toString());
        moves.setText(movesString.toString());
        rank.setText(String.valueOf(rankString));

        String string ;
        if (GameEngine.GetCurrentLevel()!= null)
        {
            string = "LEVEL: " + GameEngine.GetCurrentLevel().GetIndex() + ", Your Time: " + GameEngine.GetLevelTime() + 's' + ", Your Moves: " + GameEngine.GetCurrentMovesCount() + '\n';
        }else{
            string = "Congratulations! You have completed the game" + "\n" + "with " + GameEngine.GetTotalTime() + " secs and " + GameEngine.GetMovesCount() + " steps!";
        }
        if (rankNum != 0){
         string = string + "Your highest rank is " + rankNum + "!\n";
        }else{
            string = string + "You are not in the list";
        }
        text2.setText(string);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadList();
    }

    @FXML
    void closeGame(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void next(ActionEvent event) {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }
}
