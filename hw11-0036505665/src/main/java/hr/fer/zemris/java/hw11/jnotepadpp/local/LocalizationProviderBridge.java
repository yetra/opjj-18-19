package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class is a decorator for a given {@link ILocalizationProvider}. It manages a
 * connection status using the {@link #connect()} and {@link #disconnect()} methods.
 *
 * @author Bruna Dujmović
 * 
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * The wrapped {@link ILocalizationProvider} object.
     */
    private ILocalizationProvider parent;

    /**
     * A flag for checking if this bridge is connected.
     */
    private boolean connected;

    /**
     * The current language.
     */
    private String language;

    /**
     * Constructs a {@link LocalizationProviderBridge} that wraps the given parent.
     *
     * @param parent the parent to wrap
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        this.parent = parent;
        this.language = parent.getCurrentLanguage();
    }

    /**
     * Connects this bridge by registering a listener for localization changes.
     */
    public void connect() {
        if (connected) {
            return;
        }
        connected = true;

        String connectLanguage = parent.getCurrentLanguage();
        if (!language.equals(connectLanguage)) {
            fire();
            language = connectLanguage;
        }
        
        parent.addLocalizationListener(localizationChange);
    }

    /**
     * Disconnets this bridge by deregistering the listener for localization changes.
     */
    public void disconnect() {
        connected = false;
        language = parent.getCurrentLanguage();

        parent.removeLocalizationListener(localizationChange);
    }

    @Override
    public String getString(String key) {
        return parent.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    /**
     * Notifies all registered listeners that a localization change occurred.
     */
    private ILocalizationListener localizationChange = this::fire;
}
