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

    public static void
    processImg(BufferedImage ipimage, float scaleFactor, float offset)
            throws IOException, TesseractException {

        BufferedImage opimage
                = new BufferedImage(1050,
                1024,
                ipimage.getType());

        Graphics2D graphic
                = opimage.createGraphics();


        graphic.drawImage(ipimage, 0, 0, 1050, 1024, null);
        graphic.dispose();


        RescaleOp rescale
                = new RescaleOp(scaleFactor, offset, null);


        BufferedImage fopimage = rescale.filter(opimage, null);
        ImageIO.write(fopimage, "jpg", new File("/home/xuananle/Documents/Dictionary/iloveyou.png"));
        Tesseract it = new Tesseract();

        it.setDatapath("/usr/share/tesseract-ocr/5/tessdata");

        String str = it.doOCR(fopimage);
        System.out.println(str);
    }

    //    public static void main(String[] args) throws Exception {
//        File f = new File("/home/xuananle/Documents/Dictionary/testOCR.png");
//
//        BufferedImage ipimage = ImageIO.read(f);
//
//        // getting RGB content of the whole image file
//        double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);
//
//
//        if (d >= -1.4211511E7 && d < -7254228) {
//            processImg(ipimage, 3f, -10f);
//        } else if (d >= -7254228 && d < -2171170) {
//            processImg(ipimage, 1.455f, -47f);
//        } else if (d >= -2171170 && d < -1907998) {
//            processImg(ipimage, 1.35f, -10f);
//        } else if (d >= -1907998 && d < -257) {
//            processImg(ipimage, 1.19f, 0.5f);
//        } else if (d >= -257 && d < -1) {
//            processImg(ipimage, 1f, 0.5f);
//        } else if (d >= -1 && d < 2) {
//            processImg(ipimage, 1f, 0.35f);
//        }
//    }
    public static void main(String[] args) throws TesseractException, IOException {
        File image = new File("/home/xuananle/Pictures/Screenshots/Screenshot from 2023-07-27 17-07-24.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(testDataPath);
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
        result = TranslateManager.translateWord(result, "en", "vi");
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        System.out.println(result);
    }
}
