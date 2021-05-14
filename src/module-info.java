module pl.arturpetrzak {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.json;
    requires okhttp;

    opens pl.arturpetrzak.controller to javafx.fxml;
    exports pl.arturpetrzak to javafx.graphics;
}