package Dictionary.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class EncodingService {

    public static String imageToBase64(String ImagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(ImagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }


    // Decode base64 từ server trả về thành ảnh lưu tên result.jpeg
    public static void base64ToImage(String base64Image) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        Files.write(Paths.get("result.png"), decodedBytes);
    }

    public static void base64ToWav(String base64Wav) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Wav);
        Files.write(Paths.get("Result.wav"), decodedBytes);
    }

    public static String wavToBase64(String wavPath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(wavPath));
        return Base64.getEncoder().encodeToString(fileContent);
    }


}
