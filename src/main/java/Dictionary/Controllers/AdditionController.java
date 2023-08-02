package Dictionary.Controllers;

import Dictionary.Alerts.AlertStyler;
import Dictionary.Models.English;
import Dictionary.Services.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

import static Dictionary.App.AppStage;
import static Dictionary.DatabaseConfig.englishDAO;

public class AdditionController {

    @FXML
    private TextArea Ex; // example

    @FXML
    private Button addBtn;

    @FXML
    private TextArea Nw; // new word

    @FXML
    private TextArea Pos; // part of speech
    @FXML
    private TextArea Mn; // meaning
    @FXML
    private TextArea Pro; // pronunciation
    @FXML
    private TextArea Sy; // synonym
    @FXML
    private TextArea An; // antonym

    public void resetText() {
        Ex.setText("");
        Pos.setText("");
        Mn.setText("");
        Pro.setText("");
        Sy.setText("");
        An.setText("");
    }

    public void handleAdd() throws SQLException {
        String word = Nw.getText();
        if (word.isEmpty() || word.isBlank()) {
            resetText();
            return;
        }
        word = StringUtils.normalizeString(word);
        var ress = englishDAO.findWord(word);
        if (ress.isEmpty()) {
            resetText();
            return;
        }
        var res = ress.get(0);
        if (res != null) {
            Ex.setText(res.getExample());
            Pos.setText(res.getType());
            Mn.setText(res.getMeaning());
            Pro.setText(res.getPronunciation());
            Sy.setText(res.getSynonym());
            An.setText(res.getAntonyms());
        }
    }

    @FXML
    protected void HandleClickBtn() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String word = Nw.getText();
        String ex = Ex.getText();
        String pos = Pos.getText();
        String sy = Sy.getText();
        String an = An.getText();
        String mn = Mn.getText();
        String pro = Pro.getText();
        if (word.isEmpty() || word.isBlank()) {
            AlertStyler.on(alert)
                    .applyVintageStyle()
                    .setTitle("Error")
                    .setWindowTitle("Adding Word Failed")
                    .setButtonStyle()
                    .setMinSize()
                    .build();
            alert.initOwner(AppStage);
            alert.setContentText("Word cannot be empty, please try again");
            alert.showAndWait();
        } else if (mn.isEmpty() || mn.isBlank()) {
            AlertStyler.on(alert)
                    .applyVintageStyle()
                    .setTitle("Error")
                    .setWindowTitle("Adding Word Failed")
                    .setButtonStyle()
                    .setMinSize()
                    .build();
            alert.initOwner(AppStage);
            alert.setContentText("Meaning cannot be empty, please try again");
            alert.showAndWait();
        } else {
            word = StringUtils.normalizeString(word);
            English english = new English(word, mn, pro, pos, ex, sy, an);
            if (englishDAO.updateWord(english)) {
                AlertStyler.on(alert)
                        .applyVintageStyle()
                        .setTitle("Success")
                        .setWindowTitle("Adding Word Success")
                        .setButtonStyle()
                        .setMinSize()
                        .build();
                alert.initOwner(AppStage);
                alert.setContentText("Word: " + word + " has been added successfully");
                alert.showAndWait();
                resetText();
            }

        }
    }

}
