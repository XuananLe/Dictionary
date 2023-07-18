
package Dictionary.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class vclcontroller {

    @FXML
    private TextField WordInput;

    @FXML
    private Button addBtn;

    @FXML
    private TextArea explanationInput;

    @FXML
    protected void HandleClickBtn(ActionEvent event) {
        String word = WordInput.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dictionary Application");
        alert.setHeaderText("Word Added");
        alert.setContentText("Word: " + word);
        alert.show();
    }
}
