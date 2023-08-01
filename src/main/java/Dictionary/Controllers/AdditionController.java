package Dictionary.Controllers;

import Dictionary.Alerts.AlertStyler;
import Dictionary.Alerts.Alerts;
import Dictionary.Models.English;
import Dictionary.Service.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

import static Dictionary.DatabaseConfig.englishDAO;

public class AdditionController {
    private Alerts alerts = new Alerts();

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

    public void handleAdd() throws SQLException {
        String word = Nw.getText();
        if (word.isEmpty() || word.isBlank()) {
            Ex.setText("");
            Pos.setText("");
            Mn.setText("");
            Pro.setText("");
            Sy.setText("");
            An.setText("");
            return;
        }
        word = StringUtils.normalizeString(word);
        var ress = englishDAO.findWord(word);
        if (ress.size() <= 0) {
            Ex.setText("");
            Pos.setText("");
            Mn.setText("");
            Pro.setText("");
            Sy.setText("");
            An.setText("");
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

                alert.setContentText("Word: " + word + " has been added successfully");
                alert.showAndWait();
                Nw.setText("");
                Ex.setText("");
                Pos.setText("");
                Mn.setText("");
                Pro.setText("");
                Sy.setText("");
                An.setText("");
            }

        }
    }

    // You can call this method to hide the SuccessAlert after a certain time duration

}
