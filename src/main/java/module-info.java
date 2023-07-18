module dictionary.Dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    exports Dictionary;
    opens Dictionary to javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires ormlite.jdbc;
    exports Dictionary.Models;
    exports Dictionary.Controllers;
    opens Dictionary.Controllers to javafx.fxml;


}