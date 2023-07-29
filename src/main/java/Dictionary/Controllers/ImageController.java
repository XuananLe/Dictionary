package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
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
                String destinationPath = "/home/quanng/Documents/Dictionary/Client.jpg";
                saveImageToFile(selectedFile, destinationPath);

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
}
