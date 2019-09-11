package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

import java.awt.*;

public interface IColorProvider {

    Color getCurrentColor();

    void addColorChangeListener(ColorChangeListener l);

    void removeColorChangeListener(ColorChangeListener l);
}
