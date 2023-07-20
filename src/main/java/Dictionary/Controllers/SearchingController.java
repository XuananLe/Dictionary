package Dictionary.Controllers;

import Dictionary.Models.English;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.net.URL;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Dictionary.DatabaseConfig.englishDAO;

public class SearchingController{
    @FXML
    public ListView<String> searchResultsListView;
    @FXML
    public TextField searchBox;
    @FXML
    public ObservableList<String> observableWord = FXCollections.observableArrayList();

    @FXML
    public Label countRes = new Label("0 kết quả liên quan ");

    @FXML
    public void handleSearch(KeyEvent keyEvent) {
        String searchTerm = searchBox.getText();
        if(searchTerm.isEmpty() || searchTerm.isBlank()){
            searchResultsListView.getItems().clear();
            countRes.setText(String.valueOf(searchResultsListView.getItems().size()) + " Kết quả liên quan");
            return;
        }
        try {
            searchTerm = searchTerm.toLowerCase();
            searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
            searchResultsListView.getItems().clear();
            for (English english : englishDAO.containWord(searchTerm)) {
                    System.out.println(english.getWord());
                    searchResultsListView.getItems().add(english.getWord());
            }
            countRes.setText(String.valueOf(searchResultsListView.getItems().size()) + " Kết quả liên quan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public ListView<String> handleSearchListView(KeyEvent keyEvent) {
        String searchTerm = searchBox.getText();
        if(searchTerm.isEmpty() || searchTerm.isBlank()){
            return null;
        }
        ListView<String> searchResultsListView = new ListView<>();
        try {
            searchTerm = searchTerm.toLowerCase();
            searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
            for (English english : englishDAO.containWord(searchTerm)) {
                searchResultsListView.getItems().add(english.getWord());
                System.out.println(english.getWord());
                searchResultsListView.getItems().add("anh yeu em vcl dit nhau di");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return searchResultsListView;
        }
        return searchResultsListView;
    }
    @FXML
    public void performSearch(ActionEvent actionEvent) {
//        String searchTerm = searchBox.getText();
//        try {
//            ObservableList<English> searchResults = FXCollections.observableArrayList();
//            for (English english : englishDAO.queryForAll()) {
//                if (english.getWord().toLowerCase().contains(searchTerm.toLowerCase())) {
//                    searchResults.add(english);
//                }
//            }
//
//            // Do something with the search results
//            // For example, display the results in a ListView or TableView
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle any exceptions that occur during the database query
//        }
    }
}
