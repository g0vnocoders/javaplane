package javaplane.Graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javaplane.Decorators.App;
import javaplane.Event.BBClickListener;
import javaplane.Event.RepaintListener;


/*
 Event manager that can register click event given Bounding Box and executes the callback
 */
public class EventManager {
    private Map<String, Rectangle> Rectangles;
    private Map<String, BBClickListener> callbacks;
    private App app;
    public Boolean showHitBoxes = true;

    public EventManager(App application) {
        this.Rectangles = new HashMap<String, Rectangle>();
        this.callbacks = new HashMap<String, BBClickListener>();
        this.app = application;
        //register global click event
        app.canvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                onClick(evt.getX(), evt.getY());
            }
        });
        //add paint to app listener
        app.repaintListeners.add(new RepaintListener() {
            public void repaintRequested(java.awt.Graphics g) {
                paint(g);
            };
        });
    }

    public void registerClickEvent(String RectangleName, Rectangle pos, BBClickListener callback) {
        Rectangles.put(RectangleName, pos);
        callbacks.put(RectangleName, callback);
    }
    public void onClick(int x, int y) {
        for (Map.Entry<String, Rectangle> entry : Rectangles.entrySet()) {
            String RectangleName = entry.getKey();
            Rectangle Rectangle = entry.getValue();
            if (Rectangle.contains(x, y)) {
                callbacks.get(RectangleName).onClick();
            }
        }
    }
    public void paint(java.awt.Graphics g) {
        if (showHitBoxes) {
            for (Map.Entry<String, Rectangle> entry : Rectangles.entrySet()) {
                //random hue color
                g.setColor(Color.getHSBColor((float) Math.random(), 1, 1));
                Rectangle Rectangle = entry.getValue();
                g.drawRect(Rectangle.x, Rectangle.y, Rectangle.width, Rectangle.height);
            }
        }
    }
}   