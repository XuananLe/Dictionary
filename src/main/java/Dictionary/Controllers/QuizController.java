package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import Dictionary.Services.QuizFactory;

public class QuizController {
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
    private Button Submit, getWord;
    @FXML
    private Label FilltheBlank;
    @FXML
    private TextField AnswertheBlank;

    public void HandletheBlank() {
        FilltheBlank.setText("Fill the blank");
        AnswertheBlank.setVisible(true);
        Submit.setVisible(true);
        getWord.setVisible(false);
    }

    public void HandleOption() {
        FilltheBlank.setText("Choose the correct answer");
        AnswertheBlank.setVisible(false);
        Submit.setVisible(false);
        getWord.setVisible(true);
    }

    public void HandleSubmit() {
        if (AnswertheBlank.getText().equals("")) {
            FilltheBlank.setText("Please fill the blank");
        } else {
            FilltheBlank.setText("Correct");
        }
    }

    // set value for label
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
    public void Submit() {
        if (PlanA.isSelected()) {
            quiz.setInputAnswer(PlanA.getText());
        } else if (PlanB.isSelected()) {
            quiz.setInputAnswer(PlanB.getText());
        } else if (PlanC.isSelected()) {
            quiz.setInputAnswer(PlanC.getText());
        } else if (PlanD.isSelected()) {
            quiz.setInputAnswer(PlanD.getText());
        }
        if (quiz.checkAnswer()) {
            quiz.increaseScore();
            FilltheBlank.setText("Correct");
        } else {
            FilltheBlank.setText("Wrong");
        }
        PlanA.setSelected(false);
        PlanB.setSelected(false);
        PlanC.setSelected(false);
        PlanD.setSelected(false);
        setQuestion(quiz.generateQuestion());
        setChoices();
    }

}
