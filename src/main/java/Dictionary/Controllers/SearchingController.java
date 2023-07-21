package Dictionary.Controllers;

import Dictionary.Models.English;
import Dictionary.Utils.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Dictionary.DatabaseConfig.englishDAO;

public class SearchingController implements Initializable {
    @FXML
    public ListView<String> searchResultsListView;
    @FXML
    public TextField searchBox;
    @FXML
    public ObservableList<String> observableWord = FXCollections.observableArrayList();
    @FXML
    public Label notAvailableAlert = new Label("");

    @FXML public Label countRes = new Label("0 kết quả liên quan ");
    @FXML
    public TextArea wordDefination = new TextArea();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        searchResultsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String selectedWord = searchResultsListView.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    try {
                        English english = englishDAO.getWord(selectedWord);
                        if (english != null) {
                            wordDefination.setText(englishDAO.renderDefinition(english));
                        } else {
                            wordDefination.setText("Definition not found for: " + selectedWord);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        wordDefination.setText("Error fetching definition.");
                    }
                }
            }
        });
    }

    @FXML
    public void handleSearch(KeyEvent keyEvent) {
        String searchTerm = searchBox.getText();
        if (searchTerm.isEmpty() || searchTerm.isBlank()) {
            wordDefination.setText("");
            searchResultsListView.getItems().clear();
            countRes.setText(searchResultsListView.getItems().size() + " Kết quả liên quan");
            notAvailableAlert.setText("");
            return;
        }
        try {
            searchResultsListView.getItems().clear();
            searchTerm = searchTerm.toLowerCase();
            searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
            searchTerm = searchTerm.strip();
            searchTerm = searchTerm.trim();
            List<English> list = englishDAO.containWord(searchTerm);
            if (list.size() == 0) {
                searchResultsListView.getItems().clear();
                countRes.setText(searchResultsListView.getItems().size() + " Kết quả liên quan");
                wordDefination.setText("");
                notAvailableAlert.setText("Rất tiếc từ điển không hỗ trợ từ này");
                return;
            }
            wordDefination.setText(englishDAO.renderDefinition(list.get(0)));
            for (English english : list) {
                System.out.println(english.getWord());
                searchResultsListView.getItems().add(english.getWord());
            }
            countRes.setText(String.valueOf(searchResultsListView.getItems().size()) + " Kết quả liên quan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void speakWord() {
        VoiceManager.playVoice(searchBox.getText());
    }
    @FXML
    public ListView<String> handleSearchListView(KeyEvent keyEvent) {
        String searchTerm = searchBox.getText();
        if (searchTerm.isEmpty() || searchTerm.isBlank()) {
            return null;
        }
        ListView<String> searchResultsListView = new ListView<>();
        try {
            searchTerm = searchTerm.toLowerCase();
            searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
            for (English english : englishDAO.containWord(searchTerm)) {
                searchResultsListView.getItems().add(english.getWord());
                System.out.println(english.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return searchResultsListView;
        }
        return searchResultsListView;
    }
}
