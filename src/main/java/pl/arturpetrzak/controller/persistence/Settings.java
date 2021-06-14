package pl.arturpetrzak.controller.persistence;

import pl.arturpetrzak.Languages;

public class Settings {
    // maybe it is not best to save api keys to hard disk

    private String accuweatherApiKey;
    private String ipstackApiKey;

    private boolean usingMetricUnits;
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

    public boolean isUsingMetricUnits() {
        return usingMetricUnits;
    }

    public void setUsingMetricUnits(boolean isUsingMetricUnits) {
        this.usingMetricUnits = isUsingMetricUnits;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }
}
