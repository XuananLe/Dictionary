package Dictionary.Controllers;

import Dictionary.Services.QuizFactory;
import Dictionary.Services.VoiceService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuizController implements Initializable {
    ToggleGroup group = new ToggleGroup();

    @FXML
    private RadioButton PlanA = new RadioButton();
    @FXML
    private RadioButton PlanB = new RadioButton();
    @FXML
    private RadioButton PlanC = new RadioButton();
    @FXML
    private RadioButton PlanD = new RadioButton();

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
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setText(quiz.getChoices()[List.of(PlanA, PlanB, PlanC, PlanD).indexOf(button)]);
        }
    }

    public void setInputAnswer() {
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            if (button.isSelected()) {
                quiz.setInputAnswer(button.getText());
            }
        }
    }

    public void clearInputAnswer() {
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setSelected(false);
        }
        AnswertheBlank.clear();
    }

    public void handleSubmit(ActionEvent event) {
        if (quiz.getPlayTimes() == 5) {
            finalQuiz();
            return;
        }
        startQuiz();
        System.out.println("Submit button clicked!");

    }

    public void soundButton(ActionEvent event) {
        if (quiz.getTrueAnswer() != null) {
            VoiceService.playVoice(quiz.getQuestion());
        }
    }

    public void startQuiz() {
        setAvailable();
        quiz.initQuiz();
        setQuestion();
        setChoices();
        handleVisible();
        clearInputAnswer();
        quiz.increasePlayTimes();
        System.out.println("Quiz started! : " + quiz.getPlayTimes());
    }

    public void finalQuiz() {
        // endQuiz.setVisible(true);
    }

    public void setAvailable() {
        PlanA.setDisable(false);
        PlanB.setDisable(false);
        PlanC.setDisable(false);
        PlanD.setDisable(false);
    }

    public void whenSelected(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        System.out.println(selectedRadioButton.getText() + " selected");

        group.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            if (!radioButton.equals(selectedRadioButton)) {
                radioButton.setDisable(true);
            }
        });

        quiz.setInputAnswer(selectedRadioButton.getText());
        if (quiz.checkAnswer()) {
            Result.setText("Correct!");
        } else {
            Result.setText("Wrong, " + quiz.correctAnswer());
        }

        Result.setVisible(true);

    }
    public void showScore() {
        String score = quiz.endQuiz();
        Dialog<ButtonType> dialog = new Dialog<>();

        // Set the title
        dialog.setTitle("Quiz Score");

        // Set the content text
        dialog.setContentText("Your score is: " + score);

        // Create two button types for 'Home' and 'Play Again'
        ButtonType homeButtonType = new ButtonType("Home");
        ButtonType playAgainButtonType = new ButtonType("Play Again");

        // Add the button types to the dialog
        dialog.getDialogPane().getButtonTypes().addAll(homeButtonType, playAgainButtonType);

        // Show the dialog and capture the result
        Optional<ButtonType> result = dialog.showAndWait();

        // Handle the result
        if (result.isPresent()) {
            if (result.get() == homeButtonType) {
                // The user chose 'Home', show Searching scene

            } else if (result.get() == playAgainButtonType) {
                // The user chose 'Play Again', restart the quiz
                quiz.initQuiz();

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endQuiz.setVisible(false);
        Result.setVisible(false);

        // only once choice can be selected
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setToggleGroup(group);
        }

        // handleExistence();
        startQuiz();
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setOnAction(this::whenSelected);
        }

        // right click submit button call handle submit
        Submit.setOnAction(this::handleSubmit);
        Sound.setOnAction(this::soundButton);

    }

    public void handleVisible() {
        Result.setVisible(false);
        if (quiz.getTypeOfQuestion() == 2) {
            Sound.setVisible(true);
        } else {
            Sound.setVisible(false);
        }
        if (quiz.getTypeOfQuestion() == 3) {
            AnswertheBlank.setVisible(true);
            for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
                button.setVisible(false);
            }
        } else {
            AnswertheBlank.setVisible(false);
            for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
                button.setVisible(true);
            }
        }
    }
}
