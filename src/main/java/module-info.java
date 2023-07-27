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
    requires gson;
    requires org.apache.commons.csv;
    requires java.scripting;
    requires tess4j;
    requires json;
    requires opencv;
    requires jlayer;
    exports Dictionary.Utils;
    exports Dictionary.Controllers;
    opens Dictionary.Controllers to javafx.fxml;
    opens Dictionary.Models;
}