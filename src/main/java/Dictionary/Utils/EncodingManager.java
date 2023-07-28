package Dictionary.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
public class EncodingManager {
    public static String imageToBase64(String ImagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(ImagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }
    public static void base64ToImage(String base64Image, String pathFile) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        Files.write(Paths.get("result.jpg"), decodedBytes);
    }
    public static void main(String [] args) throws IOException {
        String base64Image = imageToBase64("/home/xuananle/Pictures/Screenshots/Screenshot from 2023-03-02 10-05-31.png");
        base64ToImage(base64Image, "/home/xuananle/Pictures/Screenshots/Screenshot from 2023-03-02 10-05-31.png");
    }
}
