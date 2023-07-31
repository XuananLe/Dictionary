package Dictionary;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageExample extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("result.png"));

        ImageView imageView = new ImageView(image);
        imageView.setX(50);
        imageView.setY(25);

        imageView.setFitHeight(455);
        imageView.setFitWidth(500);

        imageView.setPreserveRatio(true);

        Group root = new Group(imageView);


        Scene scene = new Scene(root, 600, 500);

        stage.setTitle("Loading an image");

        stage.setScene(scene);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
