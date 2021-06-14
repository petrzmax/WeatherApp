package pl.arturpetrzak.controller;

import pl.arturpetrzak.view.ViewFactory;

import java.util.regex.Pattern;

public class BaseController {
    protected ViewFactory viewFactory;
    private final String fxmlName;
    protected static final Pattern specialCharactersPattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

    public BaseController(ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
