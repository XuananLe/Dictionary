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
import javafx.scene.layout.Pane;

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
    @FXML
    private Label Result = new Label();
    @FXML
    private Pane endQuiz = new Pane();
    @FXML
    private TextField Score = new TextField();
    @FXML
    private Button playAgain = new Button();
    @FXML
    private Button backtoHome = new Button();
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

    public void clearInputAnswer() {
        PlanA.setSelected(false);
        PlanB.setSelected(false);
        PlanC.setSelected(false);
        PlanD.setSelected(false);
    }

    public void handleSubmit(ActionEvent event) {
        quiz.initQuiz();
        setQuestion();
        setChoices();
        setInputAnswer();
        if (quiz.getTypeOfQuestion() == 2) {
            Sound.setVisible(true);
            System.out.println("Sound button visible");
        } else {
            Sound.setVisible(false);
        }
        if (quiz.getTypeOfQuestion() == 3) {
            AnswertheBlank.setVisible(true);
            PlanA.setVisible(false);
            PlanB.setVisible(false);
            PlanC.setVisible(false);
            PlanD.setVisible(false);
        } else {
            AnswertheBlank.setVisible(false);
            PlanA.setVisible(true);
            PlanB.setVisible(true);
            PlanC.setVisible(true);
            PlanD.setVisible(true);
        }
        // quiz.playAgain();
        System.out.println("Submit button clicked!");
        clearInputAnswer();
        AnswertheBlank.clear();

    }

    public void soundButton(ActionEvent event) {
        if (quiz.getTrueAnswer() != null) {
            VoiceService.playVoice(quiz.getQuestion());
        }
    }
    public void finalQuiz() {
        endQuiz.setVisible(true);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endQuiz.setVisible(false);
        Result.setVisible(false);

        // only once choice
        PlanA.setToggleGroup(group);
        PlanB.setToggleGroup(group);
        PlanC.setToggleGroup(group);
        PlanD.setToggleGroup(group);


        if (quiz.getPlayTimes() == 0) {
            quiz.initQuiz();
            setQuestion();
            setChoices();
            if (quiz.getTypeOfQuestion() == 2) {
                Sound.setVisible(true);
            } else {
                Sound.setVisible(false);
            }
            if (quiz.getTypeOfQuestion() == 3) {
                AnswertheBlank.setVisible(true);
                PlanA.setVisible(false);
                PlanB.setVisible(false);
                PlanC.setVisible(false);
                PlanD.setVisible(false);
            } else {
                AnswertheBlank.setVisible(false);
                PlanA.setVisible(true);
                PlanB.setVisible(true);
                PlanC.setVisible(true);
                PlanD.setVisible(true);
            }
        }

        // right click submit button call handle submit
        Submit.setOnAction(this::handleSubmit);
        Sound.setOnAction(this::soundButton);

    }
}
