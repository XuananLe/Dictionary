package Dictionary.Controllers;

import Dictionary.Alerts.Alerts;
import Dictionary.Models.English;
import Dictionary.Utils.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
        if(word.isEmpty() || word.isBlank()){
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
        if(ress.size() <= 0) {
            return;
        }
        var res = ress.get(0);
        if(res != null){
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
