package Dictionary.Controllers;

import Dictionary.Services.QuizFactory;
import Dictionary.Services.VoiceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.SQLException;
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

    ToggleGroup group = new ToggleGroup();

    @FXML
    private Label Question;
    @FXML
    private Button Submit;
    @FXML
    private TextField AnswertheBlank;
    @FXML
    private Button Sound;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PlanA.setToggleGroup(group);
        PlanB.setToggleGroup(group);
        PlanC.setToggleGroup(group);
        PlanD.setToggleGroup(group);

        Sound.setVisible(true);
    }
    // public void handleOption() {
    // Sound.setVisible(false);
    // AnswertheBlank.setVisible(false);
    // Question.setText("Choose the correct answer");
    // Submit.setVisible(false);

    // }

    // public void handletheBlank() {
    // PlanA.setVisible(false);
    // PlanB.setVisible(false);
    // PlanC.setVisible(false);
    // PlanD.setVisible(false);
    // Sound.setVisible(false);
    // AnswertheBlank.setVisible(true);
    // }

    private QuizFactory quiz;

    public QuizController() throws SQLException {
        quiz = new QuizFactory();
    }

    public void setQuestion() {
        Question.setText(quiz.generateQuestion());
    }

    public void setChoices() {
        PlanA.setText(quiz.getChoices()[0]);
        PlanB.setText(quiz.getChoices()[1]);
        PlanC.setText(quiz.getChoices()[2]);
        PlanD.setText(quiz.getChoices()[3]);
    }

    public void setInputAnswer() {
        if (PlanA.isSelected()) {
            quiz.setInputAnswer(PlanA.getText());
        }
        if (PlanB.isSelected()) {
            quiz.setInputAnswer(PlanB.getText());
        }
        if (PlanC.isSelected()) {
            quiz.setInputAnswer(PlanC.getText());
        }
        if (PlanD.isSelected()) {
            quiz.setInputAnswer(PlanD.getText());
        }
    }

    public void handleSound() {
        VoiceService.playVoice(quiz.getTrueAnswer());
    }

    // if(quiz.getPlayTimes==-1)
    // {
    // quiz.initQuiz();
    // setQuestion();
    // setChoices();
    // }

    public void handleSubmit(ActionEvent event) {
        setInputAnswer();
        quiz.playAgain();
        setQuestion();
        setChoices();
    }

    public void soundButton(ActionEvent event) {
       VoiceService.playVoice("hello");
    }
}
