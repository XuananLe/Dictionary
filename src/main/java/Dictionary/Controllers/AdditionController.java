package Dictionary.Controllers;

import Dictionary.Alerts.Alerts;
import Dictionary.Models.English;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static Dictionary.DatabaseConfig.englishDAO;

public class AdditionController {
    private Alerts alerts = new Alerts();

    @FXML
    private TextArea Ex; // example

    @FXML
    private Button addBtn;

    @FXML
    private TextField Nw; // new word

    @FXML
    private TextField Pos; // part of speech
    @FXML
    private TextField Mn; // meaning
    @FXML
    private TextField Pro; // pronunciation
    @FXML
    private TextField Sy; // synonym
    @FXML
    private TextField An; // antonym

    @FXML
    protected void HandleClickBtn(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String word = Nw.getText();
        String ex = Ex.getText();
        String pos = Pos.getText();
        String sy = Sy.getText();
        String an = An.getText();
        String mn = Mn.getText();
        String pro = Pro.getText();
        if(word.isEmpty() || word.isBlank()){
            alert.setHeaderText("Adding word failed");
            alert.setContentText("Word cannot be empty, please try again");
            alert.showAndWait();
        }
        else if(mn.isEmpty() || mn.isBlank()){
            alert.setHeaderText("Adding word failed");
            alert.setContentText("Meaning cannot be empty, please try again");
            alert.showAndWait();
        } else{
            English english = new English(word, mn, pro, pos, ex, sy, an);
            if(!englishDAO.updateWord(english)){
                return;
            }
            alert.setHeaderText("Adding word successfully");
            alert.setContentText("Word: " + word + "\n" +
                    "Meaning: " + mn + "\n" +
                    "Part of speech: " + pos + "\n" +
                    "Pronunciation: " + pro + "\n" +
                    "Example: " + ex + "\n" +
                    "Synonym: " + sy + "\n" +
                    "Antonym: " + an + "\n");
            alert.showAndWait();
        }
    }
}
