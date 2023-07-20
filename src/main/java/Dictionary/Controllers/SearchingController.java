package Dictionary.Controllers;

import Dictionary.Models.English;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

import java.sql.SQLException;

import static Dictionary.DatabaseConfig.englishDAO;

public class SearchingController {
    public ListView<English> searchResultsListView;
    @FXML
    public TextField searchBox;

    @FXML
    public FilteredList<English> filteredList;

    public void initialize() {
        filteredList = new FilteredList<>(FXCollections.observableArrayList());

        filteredList.setPredicate(item -> true);
    }

    @FXML
    public void handleSearch(KeyEvent keyEvent) {
        String searchTerm = searchBox.getText();

        filteredList.setPredicate(english ->
                english.getWord().toLowerCase().contains(searchTerm.toLowerCase()));

        try {
            System.err.println(searchTerm);
            ObservableList<English> searchResults = FXCollections.observableArrayList();
            for (English english : englishDAO.containWord(searchTerm)) {
                 if (english.getWord().toLowerCase().contains(searchTerm.toLowerCase())) {
                    searchResults.add(english);
                }
            }
            filteredList.setPredicate(english ->
                    english.getWord().toLowerCase().contains(searchTerm.toLowerCase()));
            // Do something with the search results
            // display the results in a ListView or TableView
            searchResultsListView.setItems(filteredList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during the database query
        }
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
