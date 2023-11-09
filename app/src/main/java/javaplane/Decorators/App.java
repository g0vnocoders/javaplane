package javaplane.Decorators;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import javaplane.Event.RepaintListener;
import javaplane.Graphics.AppCanvas;
import javaplane.Graphics.LayerManager;

public class App extends JFrame implements ActionListener {
    public LayerManager layerManager = new LayerManager();
    public List<RepaintListener> repaintListeners = new ArrayList<>();
    public AppCanvas canvas;
    
    public App() {
        super("JavaPlane");
        // Set up the UI and buttons.
        JButton changeStateButton = new JButton("Change Lamp Color");
        changeStateButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeStateButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        //resize to bg image size
        setSize(layerManager.background.getWidth(null), layerManager.background.getHeight(null));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add canvas
        canvas = new AppCanvas(layerManager, this);
        add(canvas);
        setVisible(true);

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
        if (e.getActionCommand().equals("Change Lamp Color")) {
            layerManager.toggleLayerState("1.png");
            repaint();
        }
    }
}
