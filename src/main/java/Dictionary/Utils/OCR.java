package Dictionary.Utils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;

public class OCR {
    private static final String testDataPath = "/usr/share/tesseract-ocr/5/tessdata";

    public static String translateTextImage(String ImagePath, String sourceLanguage, String targetLanguage) throws TesseractException, IOException {
        File image = new File(ImagePath);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(testDataPath);
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        result = TranslateService.translateWord(result, sourceLanguage, targetLanguage);
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        return result;
    }
    public static void main(String[] args) throws TesseractException, IOException {
        System.out.println(translateTextImage("/home/xuananle/Documents/Dictionary/result.jpg", "en", "vi"));
    }
}
