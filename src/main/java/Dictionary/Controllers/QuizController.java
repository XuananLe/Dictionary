package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class QuizController {
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
    private Button Submit,getWord;
    @FXML
    private Label FilltheBlank;
    @FXML
    private TextField AnswertheBlank;
    public void HandletheBlank(){
        FilltheBlank.setText("Fill the blank");
        AnswertheBlank.setVisible(true);
        Submit.setVisible(true);
        getWord.setVisible(false);
    }
    public void HandleOption(){
        FilltheBlank.setText("Choose the correct answer");
        AnswertheBlank.setVisible(false);
        Submit.setVisible(false);
        getWord.setVisible(true);
    }
    public void HandleSubmit(){
        if(AnswertheBlank.getText().equals("")){
            FilltheBlank.setText("Please fill the blank");
        }
        else{
            FilltheBlank.setText("Correct");
        }
    }
    
}
