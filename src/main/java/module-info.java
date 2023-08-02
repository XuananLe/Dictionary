module dictionary.Dictionary {

    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires transitive java.desktop;
    requires transitive ormlite.jdbc;
    requires httpclient;
    requires httpcore;
    requires gson;
    requires org.apache.commons.csv;
    requires java.scripting;
    requires tess4j;
    requires json;
    requires commons.httpclient;
    requires opencv;
    requires jlayer;

    exports Dictionary.Services;
    exports Dictionary.Controllers;
    exports Dictionary;
    exports Dictionary.Models;

    opens Dictionary.Controllers to javafx.fxml;
    opens Dictionary.Models;
    opens Dictionary to javafx.fxml;
}