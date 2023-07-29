package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;


public class ImageController {
    @FXML
    private Button chooseImageButton;
    private Window currentStage;

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
    public void HandleFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        if (selectedFile != null) {
            try {
                Image originalImage = new Image(selectedFile.toURI().toString());
                Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();

                // Create a new ImageView and set the resized image
                ImageView imageView = new ImageView(originalImage);
                imageView.setFitWidth(screenWidth / 2);
                imageView.setFitHeight(screenHeight / 2);
                imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image

                // Save the image to the destination path
                String workingDir = System.getProperty("user.dir");

                // Use a relative path for destinationPath
                String destinationPath = workingDir + "/Client.jpg";

                saveImageToFile(selectedFile, destinationPath);

                // Create a new stage to show the resized image
                Stage imageStage = new Stage();
                BorderPane imageRoot = new BorderPane();
                imageRoot.setCenter(imageView);
                Scene imageScene = new Scene(imageRoot);
                imageStage.setScene(imageScene);

                imageStage.setTitle("Image Preview");
                imageStage.show();
            } catch (Exception ex) {
                System.err.println("Error loading image: " + ex.getMessage());
            }
        }
    }
}
