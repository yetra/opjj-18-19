package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;

/**
 * This {@link ThreadFactory} implementation is used to produce daemonic threads.
 *
 * @author Bruna Dujmović
 *
 */
public class DaemonicThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);

        return thread;
    }
}
