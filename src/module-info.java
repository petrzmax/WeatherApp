module pl.arturpetrzak {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens pl.arturpetrzak.view;
    opens pl.arturpetrzak.controller;
    opens pl.arturpetrzak to javafx.graphics;
}