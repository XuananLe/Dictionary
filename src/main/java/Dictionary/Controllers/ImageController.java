package Dictionary.Controllers;

import Dictionary.Utils.EncodingServerService;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Window;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;


public class ImageController {
    @FXML
    public Button chooseImageButton;
    @FXML
    public Window fileWindow;
    @FXML
    public ImageView imageView = new ImageView();


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
                progressIndicator.setProgress(-1);
                progressIndicator.setVisible(true);

                Image originalImage = new Image(selectedFile.toURI().toString());
                Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();


                String workingDir = System.getProperty("user.dir");

                String destinationPath = workingDir + "/Client.png";

                saveImageToFile(selectedFile, destinationPath);


                ImageView imageView = new ImageView(originalImage);
                imageView.setFitWidth(screenWidth / 2);
                imageView.setFitHeight(screenHeight / 2);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);

                StackPane dialogContent = new StackPane(imageView);
                dialogContent.getChildren().add(progressIndicator);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Image Preview");
                dialog.getDialogPane().setContent(dialogContent);

                ButtonType okButton = ButtonType.OK;
                dialog.getDialogPane().getButtonTypes().add(okButton);

                dialog.showAndWait();


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
                       EncodingServerService.sendImageToServer();
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
