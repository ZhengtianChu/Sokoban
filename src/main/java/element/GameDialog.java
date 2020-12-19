package element;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * This class is used to generate dialogs in the game.
 *
 * @author Zhengtian CHU - modified
 * @since 2.0
 */
public class GameDialog {
    static Stage dialog = new Stage();
    private static TextField textField;

    /**
     * Create the view of dialog
     *
     * @param dialogTitle         the specified title
     * @param dialogMessage       the specified message
     * @param dialogMessageEffect the specified effect
     */
    public static Stage CreateView(String dialogTitle, String dialogMessage, Effect dialogMessageEffect) {
        dialog.setResizable(false);
        dialog.setTitle(dialogTitle);

        Text text1 = new Text(dialogMessage);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(javafx.scene.text.Font.font(14));

        if (dialogMessageEffect != null) {
            text1.setEffect(dialogMessageEffect);
        }

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setBackground(Background.EMPTY);
        dialogVbox.getChildren().add(text1);

        Scene dialogScene = new Scene(dialogVbox, 350, 150);
        dialog.setScene(dialogScene);
        return dialog;
    }

    /**
     * Create a text input dialog to ask the player's name for the record
     */
    public static void ShowNameDialog(){
        dialog = new Stage();
        Text text = new Text("You are in TOP 10 NOW!!!\nEnter Your Name Here: ");
        textField = new TextField("Unknown");

        Button button = new Button("OK");
        button.setOnAction(actionEvent -> next());
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setBackground(Background.EMPTY);
        dialogVbox.getChildren().add(text);
        dialogVbox.getChildren().add(textField);
        dialogVbox.getChildren().add(button);
        Scene scene = new Scene(dialogVbox, 220,130);
        dialog.setTitle("TOP 10");
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private static void next() {
        GameEngine.SetUsername(textField.getText());
        dialog.close();
    }

}