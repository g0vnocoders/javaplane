package javaplane.Audio;

import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javaplane.Main;
import javaplane.Decorators.App;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class AudioManager {
    public static void play(String name){
        //play an mp3 file in a new thread
        new Thread(() -> {
            AudioManager.playSync(name);
        }).start();
    }
    private static Dictionary<String, byte[]> buffers = new Hashtable<String, byte[]>();
    public static synchronized void playSync(String name){
        //play an mp3 file
        try{
            if(buffers.get(name) != null){
                InputStream bufferedIn = new ByteArrayInputStream(buffers.get(name));
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.stop();
                clip.flush();
                clip.start();
                clip.setFramePosition(0);
                return;
            }
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/audio/" + name + ".wav");
            if(stream == null){
                stream = Main.class.getClassLoader().getResourceAsStream("audio/" + name + ".wav");
            }

            //add buffer for mark/reset support
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            buffers.put(name, buffer);

            //load audio stream
            InputStream bufferedIn = new ByteArrayInputStream(buffer);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            //prevent sudden stop
            clip.addLineListener((e) -> {
                if(e.getType() == javax.sound.sampled.LineEvent.Type.STOP){
                    return;
                }
            });
            clip.setFramePosition(0);
            clip.start();
            clip.setFramePosition(0);
        }
        catch(Exception e){
            System.out.println("Error playing audio: " + e);
        }
    }
}
