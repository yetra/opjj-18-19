package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The listener interface for receiving localization change events. When a localization
 * change event occurs, the {@link #localizationChanged()} method is invoked.
 *
 * @author Bruna Dujmović
 *
 */
public interface ILocalizationListener {

    /**
     * Invoked when the localization is changed.
     */
    void localizationChanged();
}