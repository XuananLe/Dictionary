package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class vclcontroller {
    @FXML
    public TextField WordInput;
    @FXML
    public Button addBtn;
    @FXML
    public TextArea explanationInput;
    @FXML
    public Label Success;

    @FXML
    public void HandleClickBtn(ActionEvent actionEvent) {
        String add = WordInput.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Tu tieng anh moi:" + add);
        alert.showAndWait();
    }
}
