package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProviderBridge;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A class derived from {@link LocalizationProviderBridge} that is registered as a
 * {@link java.awt.event.WindowListener} to the given {@link JFrame} so it can be
 * connected when frame is opened and disconnected when frame is closed.
 *
 * @see LocalizationProviderBridge
 * @author Bruna Dujmović
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Constructs a {@link LocalizationProviderBridge} that wraps the given provider
     * and registers itself as a {@link java.awt.event.WindowListener} to the specified
     * {@link JFrame}.
     *
     * @param provider the provider to wrap
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
