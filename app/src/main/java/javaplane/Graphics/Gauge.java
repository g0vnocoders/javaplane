package javaplane.Graphics;

import java.awt.*;

public class Gauge {
    public static final int scaler = 260;
    public static final int scalery = 157;

    public static void draw(Graphics g2d, int xoffset, int yoffset, double gauge, Image image){
        g2d.setColor(Color.RED);
        double input = 1.0-gauge;
        //convert 0 1 to 0 - 180 degrees
        float angle = (float) (180 - input * 180) / 180 * (float) Math.PI;

        int sinx = scaler - (int) (scalery*2 * Math.sin(angle));
        int cosx = scaler - (int) (scaler*2 * Math.cos(angle));

        int extra_y = sinx;
        if(angle > Math.PI / 2){
            extra_y = 0;
        }
        Polygon p = new Polygon(new int[]{0, 0, cosx, cosx, scaler/2}, 
                                new int[]{scalery, 0, extra_y, sinx, (int)(scalery*0.8f)}, 
                                5);
        p.translate(xoffset, yoffset);
        //g2d.drawPolygon(p);
        //cut area outside hexagon in test image
        g2d.setClip(p);
        g2d.drawImage(image, xoffset, yoffset, null);
        //reset clip
        g2d.setClip(null);
    }
    public static void drawCenteredString(Graphics g, double gauge, Rectangle rect) {
        String text = String.valueOf((int)gauge);
        // Get the FontMetrics
        Font font = new Font("Seven Segment", Font.PLAIN, 40);
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
    
}
