package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * An interface that should be implemented in classes whose objects should be able to
 * provide translations for a given key. When the selected language has changed, this
 * provider should notify all registered listeners.
 *
 * @author Bruna Dujmović
 *
 */
public interface ILocalizationProvider {

    /**
     * Registers the given {@link ILocalizationListener}.
     *
     * @param l the listener to register
     */
    void addLocalizationListener(ILocalizationListener l);

    /**
     * Deregisters the given {@link ILocalizationListener}.
     *
     * @param l the listener to remove
     */
    void removeLocalizationListener(ILocalizationListener l);

    /**
     * Returns the localization string for a given key.
     *
     * @param key the key of the localization string
     * @return the localization string for the given key
     */
    String getString(String key);
}
