package Dictionary;

import Dictionary.Models.English;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static Dictionary.DatabaseConfig.englishDAO;

public class App extends Application {
    public static Stage AppStage;
    public static final int dbSize;

    static {
        try {
            dbSize = englishDAO.getAllWords().size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final List<English> englishList;

    static {
        try {
            englishList = englishDAO.getAllWords();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Map<String, String> WordMapMean = new HashMap<>();
    public static Map<String, String> MeanMapWord = new HashMap<>();

    public App() throws SQLException {

    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/DictionaryUI.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            AppStage = primaryStage;
            primaryStage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            for(English english : englishList) {
                WordMapMean.put(english.getWord(), english.getMeaning());
                MeanMapWord.put(english.getMeaning(), english.getWord());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}