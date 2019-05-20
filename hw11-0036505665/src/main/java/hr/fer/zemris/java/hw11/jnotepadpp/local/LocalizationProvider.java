package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A singleton class that is an extension of {@link AbstractLocalizationProvider}
 * and implements the {@link #getString(String)} method.
 *
 * @see AbstractLocalizationProvider
 * @author Bruna Dujmović
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * The base name of the resource bundle.
     */
    private static final String BASE_NAME = "hr.fer.zemris.java.hw11.jnotepadpp.local.JNotepadPP";

    /**
     * A reference to the only instance of this class.
     */
    private static LocalizationProvider provider;

    /**
     * The current language.
     */
    private String language;

    /**
     * The resource bundle for the current language.
     */
    private ResourceBundle resourceBundle;

    /**
     * Constructs a {@link LocalizationProvider} with "en" as the default language.
     */
    private LocalizationProvider() {
        setLanguage("en");
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    public static LocalizationProvider getInstance() {
        if (provider == null) {
            provider = new LocalizationProvider();
        }

        return provider;
    }

    /**
     * Sets the current language to the specified language.
     *
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;

        Locale locale = Locale.forLanguageTag(language);
        resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
        fire();
    }

    @Override
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

}
