module pl.arturpetrzak {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires kotlin.stdlib;


    opens pl.arturpetrzak.controller to javafx.fxml;
    opens pl.arturpetrzak.model to com.fasterxml.jackson.databind;
    exports pl.arturpetrzak to javafx.graphics, com.fasterxml.jackson.databind;
    exports pl.arturpetrzak.controller.persistence to com.fasterxml.jackson.databind;

}