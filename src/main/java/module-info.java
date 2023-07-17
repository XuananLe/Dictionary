module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    exports Dictionary;
    opens Dictionary to javafx.fxml;
}