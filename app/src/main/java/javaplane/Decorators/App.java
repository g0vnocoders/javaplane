package javaplane.Decorators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javaplane.Event.RepaintListener;
import javaplane.Graphics.AppCanvas;
import javaplane.Graphics.LayerManager;
import javax.swing.*;

public class App extends JFrame implements ActionListener {
    public LayerManager layerManager = new LayerManager();
    public List<RepaintListener> repaintListeners = new ArrayList<>();
    public AppCanvas canvas;
    public JButton start = new JButton("Start");
    public JButton reset = new JButton("Reset");
    public JButton voice = new JButton("Voice");
    public JButton help = new JButton("Help");
    public JLabel helpText = new JLabel("<html>Натисніть кнопку Start, щоб почати симуляцію.</html>");
    public App() {
        super("JavaPlane");
        //resize to bg image size
        setSize(layerManager.background.getWidth(null), layerManager.background.getHeight(null));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add canvas
        canvas = new AppCanvas(layerManager, this);
        add(canvas);
        setVisible(true);
        //align buttons
        start.setBounds(850, 0, 100, 50);
        reset.setBounds(850, 50, 100, 50);
        voice.setBounds(1000, 0, 100, 50);
        help.setBounds(1000, 50, 100, 50);
        helpText.setBounds(850, 100, 300, 500);
        helpText.setBackground(new java.awt.Color(240, 240, 240));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.add(start);
        controlPanel.add(reset);
        controlPanel.add(voice);
        controlPanel.add(help);
        controlPanel.add(helpText);
        add(controlPanel);
        //додаємо обробку помилок на текстовий інформатор
        new GlobalExceptions(new UserErrorEvent() {
            public void onError(String message) {
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
    }
    //глобальний обробник подій
    public void actionPerformed(ActionEvent e) {
    }
}
