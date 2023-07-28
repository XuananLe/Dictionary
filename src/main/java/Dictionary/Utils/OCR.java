package Dictionary.Utils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;

public class OCR {
    private static final String testDataPath = "/usr/share/tesseract-ocr/5/tessdata";
     public static void main(String[] args) throws TesseractException, IOException {
        File image = new File("/home/xuananle/Pictures/Screenshots/Screenshot from 2023-03-02 10-05-31.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(testDataPath);
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
        result = TranslateManager.translateWord(result, "en", "vi");
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
    }
}
