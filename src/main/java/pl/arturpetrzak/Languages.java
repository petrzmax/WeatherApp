package pl.arturpetrzak;

public enum Languages {
    ENGLISH("en"),
    POLISH("pl"),
    GERMAN("de");

    private final String languageCode;

    Languages(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return languageCode;
    }
}
