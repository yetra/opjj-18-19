package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements {@link ILocalizationProvider}'s listener methods, without
 * implementing {@link #getString(String)}. It has the ability to register, de-register,
 * and inform ({@link #fire()}) listeners.
 *
 * @see ILocalizationProvider
 * @author Bruna Dujmović
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    private List<ILocalizationListener> listeners = new ArrayList<>();

    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies all registered listeners that a localization change event occured.
     */
    public void fire() {
        listeners.forEach(ILocalizationListener::localizationChanged);
    }

    @Override
    public abstract String getString(String key);
}
