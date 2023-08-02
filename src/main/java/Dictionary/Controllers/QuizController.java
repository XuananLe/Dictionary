package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.opencv.dnn.Layer;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {
    @FXML
    private RadioButton PlanA;
    @FXML
    private RadioButton PlanB;
    @FXML
    private RadioButton PlanC;
    @FXML
    private RadioButton PlanD;
    @FXML
    private Label Question;
    @FXML
    private Button Submit;
    @FXML
    private TextField AnswertheBlank;
    @FXML
    private Label layer;
    @FXML
    private Button Sound;

    public void handleOption() {
        Sound.setVisible(false);
        layer.setVisible(false);
    }
    public void handletheBlank() {
        PlanA.setVisible(false);
        PlanB.setVisible(false);
        PlanC.setVisible(false);
        PlanD.setVisible(false);
        Sound.setVisible(false);
        layer.setVisible(true);
    }
    public void test() {
        handletheBlank();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
