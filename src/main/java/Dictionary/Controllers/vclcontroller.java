package Dictionary.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class vclcontroller {

    @FXML
    private TextArea Ex;

    @FXML
    private Button addBtn;

    @FXML
    private TextField Nw;

    @FXML
    private TextField Pos;
    @FXML
    private TextField Rw;
    @FXML
    private TextField Mn;
    @FXML
    private TextField Pro;

    @FXML
    protected void HandleClickBtn(ActionEvent event) {
        String word = Nw.getText();
        String ex = Ex.getText();
        String pos = Pos.getText();
        String rw = Rw.getText();
        String mn = Mn.getText();
        String pro = Pro.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dictionary Application");
        alert.setHeaderText("Word Added");
        alert.setContentText("New Word: " + word + "\n" + "Explaination: " + ex + "\n" + "Part of speech :" + pos + "\n" + "Related word: " + rw + "\n");

        alert.show();
    }
}
