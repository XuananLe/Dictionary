package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    @FXML
    public static  Tooltip tooltip1, tooltip2, tooltip3, tooltip4, tooltip5, tooltip6;
    @FXML
    public static  Button addBtn, translateBtn, searchBtn, attachBtn, exitBtn, quizBtn;
    @FXML
    public static AnchorPane container;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBtn.setOnAction(event -> showComponent("/View/SearchingUI.fxml"));

        addBtn.setOnAction(event -> showComponent("/View/AdditionUI.fxml"));

        translateBtn.setOnAction(event -> showComponent("/View/TranslationUI.fxml"));
        attachBtn.setOnAction(event -> showComponent("/View/ImageTranslateUI.fxml"));
        quizBtn.setOnAction(event -> showComponent("/View/QuizUI.fxml"));

        tooltip1.setShowDelay(Duration.seconds(0.1));
        tooltip2.setShowDelay(Duration.seconds(0.1));
        tooltip3.setShowDelay(Duration.seconds(0.1));
        tooltip4.setShowDelay(Duration.seconds(0.1));
        tooltip5.setShowDelay(Duration.seconds(0.1));
        tooltip6.setShowDelay(Duration.seconds(0.1));
        try {
            showComponent("/View/SearchingUI.fxml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        exitBtn.setOnMouseClicked(e -> System.exit(0));
    }

    public static final void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    public static final void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(DictionaryController.class.getResource(path)));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
