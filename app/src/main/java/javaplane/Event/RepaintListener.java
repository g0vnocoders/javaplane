package javaplane.Event;

import java.awt.Graphics;
import java.util.EventListener;

public interface RepaintListener extends EventListener {
    void repaintRequested(Graphics g);
}