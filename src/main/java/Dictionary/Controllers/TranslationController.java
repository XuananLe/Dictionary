package Dictionary.Controllers;

import Dictionary.Utils.TranslateManager;
import Dictionary.Utils.VoiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
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
    public void initialize() {
        SourceLanguage.setLayoutX(16.0);
        SourceLanguage.setLayoutY(84.0);
        SourceLanguage.setPrefWidth(284.0);
        SourceLanguage.setPrefHeight(246.0);
        SourceLanguage.setWrapText(true);
        SourceLanguage.setStyle("-fx-font-size: 19px; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-image: url(https://xlink.vn/dm3shtsg);");

        TranslationLanguage.setLayoutX(16.0);
        TranslationLanguage.setLayoutY(84.0);
        TranslationLanguage.setPrefWidth(284.0);
        TranslationLanguage.setPrefHeight(246.0);
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
                String translation = TranslateManager.translateWord(textToTranslate, sourceLanguage, targetLanguage);
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
        VoiceManager.playVoice(textToTranslate);
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
