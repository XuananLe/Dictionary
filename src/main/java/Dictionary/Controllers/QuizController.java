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
    private RadioButton PlanA = new RadioButton();
    @FXML
    private RadioButton PlanB = new RadioButton();
    @FXML
    private RadioButton PlanC = new RadioButton();
    @FXML
    private RadioButton PlanD = new RadioButton();

    ToggleGroup group = new ToggleGroup();

    @FXML
    private Label Question = new Label();
    @FXML
    private Button Submit = new Button();
    @FXML
    private TextField AnswertheBlank = new TextField();
    @FXML
    private Button Sound = new Button();
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
        setQuestion();
        setChoices();
        setInputAnswer();
        // quiz.playAgain();
        System.out.println("Submit button clicked!");

    }

    public void soundButton(ActionEvent event) {
        VoiceService.playVoice("hello");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PlanA.setToggleGroup(group);
        PlanB.setToggleGroup(group);
        PlanC.setToggleGroup(group);
        PlanD.setToggleGroup(group);
        if (quiz.getPlayTimes() == -1) {
            quiz.initQuiz();
        }
        setQuestion();
        setChoices();

        Sound.setVisible(true);
        // right click submit button call handle submit
        Submit.setOnAction(this::handleSubmit);
        Sound.setOnAction(this::soundButton);

    }
}
