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
     * Constructs a {@link LocalizationProviderBridge} that wraps the given parent.
     *
     * @param parent the parent to wrap
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        this.parent = parent;
    }

    /**
     * Connects this bridge by registering a listener for localization changes.
     */
    public void connect() {
        if (connected) {
            return;
        }

        parent.addLocalizationListener(localizationChange);
        connected = true;
    }

    /**
     * Disconnets this bridge by deregistering the listener for localization changes.
     */
    public void disconnect() {
        connected = false;

        parent.removeLocalizationListener(localizationChange);
    }

    @Override
    public String getString(String key) {
        return parent.getString(key);
    }

    /**
     * Notifies all registered listeners that a localization change occurred.
     */
    private ILocalizationListener localizationChange = this::fire;
}
