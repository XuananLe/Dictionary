package Dictionary.Controllers;

import Dictionary.Utils.TranslateService;
import Dictionary.Utils.VoiceService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class TranslationController {
    private final List<String> languages = Arrays.asList("English", "Vietnamese", "Spanish", "French");
    private final List<String> ListOfWords = new ArrayList<>(languages);
    @FXML
    public TextArea SourceLanguage;
    @FXML
    public TextArea TranslationLanguage;
    @FXML
    private ComboBox<String> sourceLanguageComboBox;
    @FXML
    private ComboBox<String> targetLanguageComboBox;
    @FXML
    private Button chooseImageButton;
    private Window currentStage;

    @FXML
    public void initialize() {
       

        SourceLanguage.setWrapText(true);
        SourceLanguage.setStyle("-fx-font-size: 19px; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-image: url(https://xlink.vn/dm3shtsg);");

        TranslationLanguage.setWrapText(true);
        TranslationLanguage.setStyle("-fx-font-size: 19px; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-image: url(https://xlink.vn/dm3shtsg);");
        // Populate both ComboBoxes with language options
        sourceLanguageComboBox.getItems().addAll(languages);
        targetLanguageComboBox.getItems().addAll(languages);

        // Set default values for ComboBoxes
        sourceLanguageComboBox.setValue("English");
        targetLanguageComboBox.setValue("Vietnamese");

        // Add event handlers to update ComboBox options
        sourceLanguageComboBox.setOnAction(e -> updateTargetLanguageOptions());
        targetLanguageComboBox.setOnAction(e -> updateSourceLanguageOptions());
    }
    private void saveImageToFile(Image image, String destinationPath) {
//        try (InputStream is = image.getUrl().trim();
//             OutputStream os = new FileOutputStream(destinationPath)) {
//
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//
//            System.out.println("Image saved to: " + destinationPath);
//        } catch (IOException e) {
//            System.err.println("Error saving image: " + e.getMessage());
//        }
    }
    @FXML
    public void HandleFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File selectedFile = fileChooser.showOpenDialog(currentStage);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300); // Set the desired width of the image
        imageView.setFitHeight(200); // Set the desired height of the image
        imageView.setPreserveRatio(true); // Preserve the ratio of the image when scaling it

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);

                // Save the image to the Utils/Images folder with the name "Client.jpg"
                String destinationPath = "Utils/Images/Client.jpg";
                saveImageToFile(image, destinationPath);


                // Create a new stage to show the image
                Stage imageStage = new Stage();
                BorderPane imageRoot = new BorderPane();
                imageRoot.setCenter(new ImageView(image));

                Scene imageScene = new Scene(imageRoot);
                imageStage.setScene(imageScene);
                imageStage.setTitle("Image Preview");
                imageStage.show();

            } catch (Exception ex) {
                System.err.println("Error loading image: " + ex.getMessage());
            }
        }
    }

    @FXML
    public void translateWord() {
        String sourceLanguage = getLanguageCode(sourceLanguageComboBox.getValue());
        String targetLanguage = getLanguageCode(targetLanguageComboBox.getValue());
        String textToTranslate = SourceLanguage.getText();
        if (textToTranslate.isEmpty() || textToTranslate.isBlank()) {
            TranslationLanguage.setText("");
            return;
        }
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                String translation = TranslateService.translateWord(textToTranslate, sourceLanguage, targetLanguage);
                TranslationLanguage.setText(translation);
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void handleVoice() {
        String textToTranslate = SourceLanguage.getText();
        if (textToTranslate.isEmpty() || textToTranslate.isBlank()) {
            return;
        }
        VoiceService.playVoice(textToTranslate);
    }

    // Helper method to get the language code based on language name
    private String getLanguageCode(String languageName) {
        switch (languageName) {
            case "Vietnamese":
                return "vi";
            case "Spanish":
                return "es";
            case "French":
                return "fr";
            // Add more cases for additional languages
            case "English":
            default:
                return "en";
        }
    }

    // Helper method to update targetLanguageComboBox options
    private void updateTargetLanguageOptions() {
        String sourceLanguage = sourceLanguageComboBox.getValue();
        targetLanguageComboBox.getItems().clear();
        for (String language : languages) {
            if (!Objects.equals(language, sourceLanguage)) {
                targetLanguageComboBox.getItems().add(language);
            }
        }
        targetLanguageComboBox.setValue(targetLanguageComboBox.getItems().get(0));
    }

    private void updateSourceLanguageOptions() {

    }
}
