package javaplane.Graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class LayerManager {
    public Image background;
    public Map<String, Image> layers = new HashMap<String, Image>();
    public Map<String, Boolean> layerStates = new HashMap<String, Boolean>();
    public ArrayList<String> files = new ArrayList<String>();
    public LayerManager(){
        //scan resources/graphics for images
        scanResources();
        //load background
        background = layers.get("background.png");
        layers.remove("background.png");
    }

    private void scanResources(){
        //scan resources/graphics for images and load each to layers map
        URL url = getClass().getClassLoader().getResource("graphics");
        System.out.println("Loading images from: " + url.getPath());
        //check if jar
        if(url.getPath().contains(".jar")){
            //load from jar
            loadFromJar(url);
            return;
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(url.getPath()))) {
            for (Path entry : stream) {
                //load png
                if(entry.toString().endsWith(".png")){
                    //get filename
                    String filename = entry.getFileName().toString();
                    //add to files list
                    files.add(filename);
                    //load image
                    layers.put(filename, ImageIO.read(entry.toFile()));
                    //set layer state to true
                    layerStates.put(filename, true);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading images: " + e);
        }
    }
    //load files using zip stream
    private void loadFromJar(URL url) {
        //load from jar
        try {
            //get jar path
            String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
            //load jar
            java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath);
            //get entries
            java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
            //loop through entries
            while (entries.hasMoreElements()) {
                //get entry
                java.util.jar.JarEntry entry = entries.nextElement();
                //check if entry is in graphics folder
                if(entry.getName().startsWith("graphics/")){
                    //load png
                    if(entry.getName().endsWith(".png")){
                        //get filename
                        String filename = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);
                        //add to files list
                        files.add(filename);
                        //load image
                        layers.put(filename, ImageIO.read(jar.getInputStream(entry)));
                        //set layer state to true
                        layerStates.put(filename, true);
                    }
                }
            }
            //close jar
            jar.close();
        } catch (IOException e) {
            System.out.println("Error loading images: " + e);
        }
    }

    public void setLayerState(String layer, boolean state){
        layerStates.put(layer, state);
    }
    public void toggleLayerState(String layer){
        layerStates.put(layer, !layerStates.get(layer));
    }
    public void disableAllLayers(){
        for(String layer : layers.keySet()){
            setLayerState(layer, false);
        }
    }
    public Boolean getLayerState(String layer){
        return layerStates.get(layer);
    }
    public void paint(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        // Draw the background.
        g.drawImage(background, 0, 0, null);
        // Draw the layers depending on the state.
        for(String layer : layers.keySet()){
            if(layerStates.get(layer)){
                g2d.drawImage(layers.get(layer), 0, 0, null);
            }
        }
    }
}
