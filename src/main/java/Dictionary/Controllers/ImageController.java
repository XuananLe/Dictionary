package Dictionary.Controllers;

import Dictionary.Utils.EncodingServerService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Window;

import java.io.*;
import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static Dictionary.App.AppStage;


public class ImageController {
    @FXML
    public Button chooseImageButton;
    @FXML
    public Window fileWindow;

    public ProgressIndicator progressIndicator = new ProgressIndicator(-1);


    private void saveImageToFile(File sourceFile, String destinationPath) {
        try (InputStream is = new FileInputStream(sourceFile);
             OutputStream os = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            System.out.println("Image saved to: " + destinationPath);
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }

    @FXML
    public void HandleFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File selectedFile = fileChooser.showOpenDialog(fileWindow);
        if (selectedFile != null) {
            try {

                Image originalImage = new Image(selectedFile.toURI().toString());
                Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();


                String workingDir = System.getProperty("user.dir");

                String destinationPath = workingDir + "/Client.png";
                saveImageToFile(selectedFile, destinationPath);


                ImageView ClinetImageView = new ImageView(originalImage);
                ClinetImageView.setFitWidth(screenWidth / 3);
                ClinetImageView.setFitHeight(screenHeight / 3);
                ClinetImageView.setPreserveRatio(true);
                ClinetImageView.setSmooth(true);

                StackPane dialogContent = new StackPane(ClinetImageView);
                ButtonType okButton = ButtonType.OK;


                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Image Preview");
                dialog.getDialogPane().setContent(dialogContent);
                dialog.getDialogPane().getButtonTypes().add(okButton);
                dialog.initOwner(AppStage);
                dialog.showAndWait();
                progressIndicator.setVisible(true);

                var executorService = Executors.newSingleThreadExecutor();
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                    try {
                        EncodingServerService.sendImageToServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, executorService);
                completableFuture.thenRun(() -> {
                    try {
                        Platform.runLater(() -> {
                            File resultFile = new File("result.png");
                            Image ResultImage = null;
                            try {
                                ResultImage = new Image(resultFile.toURI().toURL().toString());
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            ImageView ResultImageView = new ImageView(ResultImage);
                            ResultImageView.setFitHeight(screenHeight / 3);
                            ResultImageView.setFitWidth(screenWidth / 3);
                            ResultImageView.preserveRatioProperty().setValue(true);

                            HBox hbox = new HBox(ClinetImageView, ResultImageView);

                            Dialog<ButtonType> resultDialog = new Dialog<>();
                            resultDialog.setTitle("Result");
                            resultDialog.initOwner(AppStage);
                            resultDialog.getDialogPane().setContent(hbox);
                            resultDialog.getDialogPane().getButtonTypes().add(okButton);

                            progressIndicator.setVisible(false);
                            resultDialog.showAndWait();
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception ex) {
                System.err.println("Error loading image: " + ex.getMessage());
            }
        }
    }

}
