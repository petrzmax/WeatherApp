package pl.arturpetrzak.controller;

import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.view.ViewFactory;

public class BaseController {
    protected ViewFactory viewFactory;
    protected DailyForecastManager dailyForecastManager;
    private String fxmlName;


    public BaseController(DailyForecastManager dailyForecastManager, ViewFactory viewFactory, String fxmlName) {
        this.dailyForecastManager = dailyForecastManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
