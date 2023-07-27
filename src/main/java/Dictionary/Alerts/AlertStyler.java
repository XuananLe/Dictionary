package Dictionary.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class AlertStyler {
    private Alert alert;
    private DialogPane dialogPane;

    private AlertStyler(Alert alert) {
        this.alert = alert;
        this.dialogPane = alert.getDialogPane();
    }

    public static AlertStyler on(Alert alert) {
        return new AlertStyler(alert);
    }

    public AlertStyler applyVintageStyle() {
        String vintageStyle = "-fx-background-color: #f9e4b7; -fx-border-color: #b3804d; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-image: url(\"https://example.com/aged_paper_texture.png\");";
        dialogPane.setStyle(vintageStyle);
        return this;
    }

    public AlertStyler setTitle(String title) {
        String titleStyle = "-fx-font-family: 'Lucida Handwriting', cursive; -fx-font-size: 18px; -fx-text-fill: #b3804d;";
        alert.setHeaderText(title);
        Label titleLabel = new Label(title);
        titleLabel.setStyle(titleStyle);
        dialogPane.setHeader(titleLabel);
        return this;
    }

    public AlertStyler setButtonStyle() {
        String buttonStyle = "-fx-background-color: #b3804d; -fx-text-fill: #f9e4b7; -fx-font-size: 14px; -fx-font-weight: bold;";
        dialogPane.lookupButton(ButtonType.OK).setStyle(buttonStyle);
        return this;
    }

    public AlertStyler setMinSize() {
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        return this;
    }

    public Alert build() {
        return alert;
    }
}
