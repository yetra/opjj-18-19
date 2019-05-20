package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

/**
 * An extension of {@link AbstractAction} that updates its {@link #NAME} key and
 * corresponding translation value on each localization change event.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class LocalizableAction extends AbstractAction {

    /**
     * The version number of this serializable class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The translation key.
     */
    private String key;

    /**
     * The {@link ILocalizationProvider} for translating the {@link #key}.
     */
    private ILocalizationProvider lp;

    /**
     * Updates and stores the translation for the {@link #key}.
     */
    private final ILocalizationListener listener = () -> putValue(NAME, lp.getString(key));

    /**
     * Constructs a {@link LocalizableAction} for the specified key and registers a
     * listener for localization changes on the given {@link ILocalizationProvider}.
     *
     * @param key the translation key
     * @param lp the {@link ILocalizationProvider} for translating the {@link #key}
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.key = key;
        this.lp = lp;

        putValue(NAME, lp.getString(key));
        lp.addLocalizationListener(listener);
    }
}
