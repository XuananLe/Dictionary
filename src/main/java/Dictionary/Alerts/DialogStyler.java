package Dictionary.Alerts;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class DialogStyler {
    private Dialog dialog;
    private DialogPane dialogPane;

    private DialogStyler(Dialog dialog) {
        this.dialog = dialog;
        this.dialogPane = dialog.getDialogPane();
    }

    public static DialogStyler on(Dialog dialog) {
        return new DialogStyler(dialog);
    }

    public DialogStyler applyVintageStyle() {
        String vintageStyle = "-fx-background-color: #f9e4b7; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-image: url(/Images/Background.jpg);";
        dialogPane.setStyle(vintageStyle);
        return this;
    }

    public DialogStyler setTitle(String title) {
        String titleStyle = "-fx-font-family: 'Lucida Handwriting', cursive; -fx-font-size: 18px; -fx-text-fill: #000000;";

        // Create a new label and apply styling
        Label titleLabel = new Label(title);
        titleLabel.setStyle(titleStyle);

        // Set the title as the header of the dialog pane
        dialogPane.setHeader(titleLabel);

        return this;
    }

    public DialogStyler setWindowTitle(String title) {
        dialog.setTitle(title);
        return this;
    }

    public DialogStyler setMinSize() {
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        return this;
    }

    public Dialog build() {
        return dialog;
    }
}
