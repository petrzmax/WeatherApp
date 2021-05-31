package pl.arturpetrzak.controller.persistence;

import pl.arturpetrzak.Languages;

import java.io.Serializable;

public class Settings implements Serializable {
    private boolean metric;
    private Languages language;

    public boolean isMetric() {
        return metric;
    }

    public void setMetric(boolean metric) {
        this.metric = metric;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }
}
