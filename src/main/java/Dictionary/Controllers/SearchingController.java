package Dictionary.Controllers;

import Dictionary.Models.English;
import Dictionary.Utils.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.net.URL;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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

    @FXML
    public Label countRes = new Label("0 kết quả liên quan ");
    @FXML
    public TextArea wordDefination = new TextArea();

    @FXML
    public English currentWord = new English();

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
                            currentWord = english;
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
            currentWord = list.get(0);
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
        VoiceManager.playVoice(currentWord.getWord());
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

    public void deleteWord(ActionEvent actionEvent) throws SQLException {
        if (currentWord.getWord().equals("")) {
            return;
        }
        if (englishDAO.deleteWord(currentWord.getWord())) {
            JOptionPane.showMessageDialog(null, "Xóa thành công"); // Thay vào đây 1 cái alert @Quân Nguyễn ơi
            searchBox.setText("");
            wordDefination.setText("");
            searchResultsListView.getItems().remove(currentWord.getWord());
            countRes.setText(searchResultsListView.getItems().size() + " Kết quả liên quan");
            notAvailableAlert.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }
    }

    public void updateWord() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Word Information");
        dialog.setHeaderText(null);

        Label nameLabel = new Label("Word:");
        TextField nameField = new TextField(currentWord.getWord());

        Label definitionLabel = new Label("Definition:");
        TextField definitionField = new TextField(currentWord.getMeaning());

        Label typeLabel = new Label("Part of speech:");
        TextField typeField = new TextField(currentWord.getType());

        Label pronunciationLabel = new Label("Pronunciation:");
        TextField pronunciationField = new TextField(currentWord.getPronunciation());

        Label exampleLabel = new Label("Example:");
        TextField exampleField = new TextField(currentWord.getExample());

        Label synonymLabel = new Label("Synonym:");
        TextField synonymField = new TextField(currentWord.getSynonym());

        Label antonymLabel = new Label("Antonym:");
        TextField antonymField = new TextField(currentWord.getAntonyms());

        GridPane gridPane = new GridPane();
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameField, 2, 1);
        gridPane.add(definitionLabel, 1, 2);
        gridPane.add(definitionField, 2, 2);
        gridPane.add(typeLabel, 1, 3);
        gridPane.add(typeField, 2, 3);
        gridPane.add(pronunciationLabel, 1, 4);
        gridPane.add(pronunciationField, 2, 4);
        gridPane.add(exampleLabel, 1, 5);
        gridPane.add(exampleField, 2, 5);
        gridPane.add(synonymLabel, 1, 6);
        gridPane.add(synonymField, 2, 6);
        gridPane.add(antonymLabel, 1, 7);
        gridPane.add(antonymField, 2, 7);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        var result = dialog.showAndWait();
        if (result.isPresent()) {
            String word = nameField.getText();
            String definition = definitionField.getText();
            String type = typeField.getText();
            String pronunciation = pronunciationField.getText();
            String example = exampleField.getText();
            String synonym = synonymField.getText();
            String antonym = antonymField.getText();
            English english = new English(word, definition, pronunciation, type, example, synonym, antonym);
            try {
                englishDAO.updateWord(english);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Update Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Word information updated successfully!");
                successAlert.showAndWait();
                currentWord = english;
                wordDefination.setText(englishDAO.renderDefinition(currentWord));
            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Update Failed");
                errorAlert.setHeaderText(null);
            }
        }
    }
}
