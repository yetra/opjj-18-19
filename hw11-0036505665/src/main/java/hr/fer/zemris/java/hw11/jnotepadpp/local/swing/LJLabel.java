package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

/**
 * An extension of {@link JLabel} that supports localization.
 *
 * @author Bruna Dujmović
 *
 */
public class LJLabel extends JLabel {

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
     * Updates the label with a new translation for the {@link #key}.
     */
    private final ILocalizationListener listener = this::updateLabel;

    /**
     * Constructs a {@link LJLabel} for the given translation key and localization
     * provider.
     *
     * @param key the translation key
     * @param lp the {@link ILocalizationProvider} for translating the {@link #key}
     */
    public LJLabel(String key, ILocalizationProvider lp) {
        this.key = key;
        this.lp = lp;

        updateLabel();
        lp.addLocalizationListener(listener);
    }

    /**
     * Updates the text of this label using the {@link #lp}.
     */
    private void updateLabel() {
        setText(lp.getString(key));
    }

}
