module pl.arturpetrzak {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires okhttp3;

    opens pl.arturpetrzak.controller;
    exports pl.arturpetrzak to javafx.graphics;
}