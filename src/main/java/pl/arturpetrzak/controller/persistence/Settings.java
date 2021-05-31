package pl.arturpetrzak.controller.persistence;

import pl.arturpetrzak.Languages;

import java.io.Serializable;

public class Settings implements Serializable {
    private String accuweatherApiKey;
    private String ipstackApiKey;

    private boolean metric;
    private Languages language;

    public String getAccuweatherApiKey() {
        return accuweatherApiKey;
    }

    public void setAccuweatherApiKey(String accuweatherApiKey) {
        this.accuweatherApiKey = accuweatherApiKey;
    }

    public String getIpstackApiKey() {
        return ipstackApiKey;
    }

    public void setIpstackApiKey(String ipstackApiKey) {
        this.ipstackApiKey = ipstackApiKey;
    }

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
