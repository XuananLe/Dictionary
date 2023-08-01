package Dictionary.Service;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class DeviceInfoService {
    public static Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    public static double ScreenWidth = getScreenBounds().getWidth();
    public static double ScreenHeight = getScreenBounds().getHeight();

    public static Rectangle2D getScreenBounds() {
        return screenBounds;
    }
}
