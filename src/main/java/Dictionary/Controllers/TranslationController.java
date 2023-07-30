package Dictionary.Controllers;

import Dictionary.Utils.EncodingServerService;
import Dictionary.Utils.RecordingService;
import Dictionary.Utils.TranslateService;
import Dictionary.Utils.VoiceService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class TranslationController {
    private final List<String> languages = Arrays.asList("English", "Vietnamese", "Spanish", "French");
    @FXML
    public TextArea SourceLanguage;
    @FXML
    public TextArea TranslationLanguage;
    public Button recordButton = new Button();

    private int count = 0;
    @FXML
    private ComboBox<String> sourceLanguageComboBox;
    @FXML
    private ComboBox<String> targetLanguageComboBox;


    @FXML
    public void initialize() {
       

        SourceLanguage.setWrapText(true);
        SourceLanguage.setStyle("-fx-font-size: 19px; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-image: url(https://xlink.vn/dm3shtsg);");

        TranslationLanguage.setWrapText(true);
        TranslationLanguage.setStyle("-fx-font-size: 19px; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-image: url(https://xlink.vn/dm3shtsg);");
        sourceLanguageComboBox.getItems().addAll(languages);
        targetLanguageComboBox.getItems().addAll(languages);

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
    private final RecordingService recordingService = RecordingService.getInstance();

    public void handleRecording() throws IOException {
        recordButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ++count;
                if (count % 2 == 1) {
                    System.out.println("Recording");
                    recordButton.setStyle("-fx-background-color: #ff0000");
                    recordingService.startRecording();
                    SourceLanguage.setText("");
                    TranslationLanguage.setText("");
                } else {
                    System.out.println("Stop recording");
                    recordButton.setStyle("-fx-background-color: #ffffff");
                    recordingService.stopRecording();

                    translateAndDeleteFile();
                }
            }
        });
    }

    private void translateAndDeleteFile() {
        CompletableFuture<List<String>> serverCommunicationFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return EncodingServerService.sendWavToServer();
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        });

        serverCommunicationFuture.thenAccept(list -> {
            if (list.isEmpty()) {
                System.out.println("No text received from server.");
            } else {
                String textToTranslate = list.get(0);
                SourceLanguage.setText(textToTranslate);
                translateWord();

                deleteRecordedFile();
            }
        });
    }

    private void deleteRecordedFile() {
        try {
            // Replace "Client.wav" with the actual recorded file name and path if needed
            File recordedFile = new File("Client.wav");
            if (recordedFile.exists()) {
                if (recordedFile.delete()) {
                    System.out.println("Recorded file deleted successfully.");
                } else {
                    System.err.println("Failed to delete the recorded file.");
                }
            }
        } catch (Exception ignored) {

        }
    }
}
