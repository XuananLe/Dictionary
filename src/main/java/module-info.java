module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    exports Dictionary;
    opens Dictionary to javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires ormlite.jdbc;
    opens Dictionary.Models;

    exports Dictionary.Controllers to javafx.fxml;
}