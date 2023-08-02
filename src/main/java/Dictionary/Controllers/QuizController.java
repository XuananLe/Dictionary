package Dictionary.Controllers;

import Dictionary.Services.QuizFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.opencv.dnn.Layer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class QuizController implements Initializable {
    private QuizFactory quiz;

    public QuizController() throws SQLException {
        quiz = new QuizFactory();
    }
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
    private Button Sound;

    public void handleOption() {
        Sound.setVisible(false);
        AnswertheBlank.setVisible(false);
        Question.setText("Choose the correct answer");
        Submit.setVisible(false);

    }
    public void handletheBlank() {
        PlanA.setVisible(false);
        PlanB.setVisible(false);
        PlanC.setVisible(false);
        PlanD.setVisible(false);
        Sound.setVisible(false);
        AnswertheBlank.setVisible(true);
    }
    public void setQuestion(String question) {
        Question.setText(quiz.generateQuestion());
    }

    // set value for radio button
    public void setChoices() {
        PlanA.setText(quiz.getChoices()[0]);
        PlanB.setText(quiz.getChoices()[1]);
        PlanC.setText(quiz.getChoices()[2]);
        PlanD.setText(quiz.getChoices()[3]);
    }

    // button submit
//    public void Submit() {
//        if (PlanA.isSelected()) {
//            quiz.setInputAnswer(PlanA.getText());
//        } else if (PlanB.isSelected()) {
//            quiz.setInputAnswer(PlanB.getText());
//        } else if (PlanC.isSelected()) {
//            quiz.setInputAnswer(PlanC.getText());
//        } else if (PlanD.isSelected()) {
//            quiz.setInputAnswer(PlanD.getText());
//        }
//        if (quiz.checkAnswer()) {
//            quiz.increaseScore();
//            FilltheBlank.setText("Correct");
//        } else {
//            FilltheBlank.setText("Wrong");
//        }
//        PlanA.setSelected(false);
//        PlanB.setSelected(false);
//        PlanC.setSelected(false);
//        PlanD.setSelected(false);
//        setQuestion(quiz.generateQuestion());
//        setChoices();
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

