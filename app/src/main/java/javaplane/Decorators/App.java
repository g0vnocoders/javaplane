package javaplane.Decorators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

import javax.swing.JFrame;

import javaplane.Main;
import javaplane.Event.RepaintListener;
import javaplane.Graphics.AppCanvas;
import javaplane.Graphics.LayerManager;
import javax.swing.*;

public class App extends JFrame implements ActionListener {
    public LayerManager layerManager = new LayerManager();
    public List<RepaintListener> repaintListeners = new ArrayList<>();
    public AppCanvas canvas;
    public JLabel errText = new JLabel("<html>Виберіть режим симулятора, щоб почати.</html>");
    public JLabel helpText = new JLabel();
    public App() {
        super("JavaPlane");
        //resize to bg image size
        setSize(layerManager.background.getWidth(null), layerManager.background.getHeight(null));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add canvas
        canvas = new AppCanvas(layerManager, this);
        add(canvas);
        setVisible(true);
        //align
        helpText.setBounds(850, 140, 300, 500);
        helpText.setBackground(new java.awt.Color(240, 240, 240));
        errText.setBounds(850, 140, 300, 200);
        errText.setBackground(new java.awt.Color(240, 240, 240));
        //load help from file instructions.html
        try {
            InputStream stream = getClass().getResourceAsStream("/instruction.html");
            String text = new String(stream.readAllBytes());
            helpText.setText(text);
        } catch (Exception e) {
            System.out.println("Error loading help: " + e);
        }

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.add(helpText);
        controlPanel.add(errText);
        add(controlPanel);
        //додаємо обробку помилок на текстовий інформатор
        new GlobalExceptions(new UserErrorEvent() {
            public void onError(String message) {
                errText.setText("<html><div style='color:red;'>" + message + "</div></html>");
            }
            public void onWarning(String message) {
                helpText.setText("<html>" + message + "</html>");
            }
        });
    }
    //перемальовуємо вікно разом з панеллю
    public void repaint(){
        System.out.println("repaint");
        super.repaint();
        canvas.revalidate();
        canvas.repaint();
        super.repaint();
    }
    //глобальний обробник подій
    public void actionPerformed(ActionEvent e) {}
}
