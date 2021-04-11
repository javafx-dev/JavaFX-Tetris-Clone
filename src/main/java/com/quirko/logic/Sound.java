package com.quirko.logic;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    static Clip Sound;

    public static void PlaySound(String file){
        File SoundFile = new File(file);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Sound = (Clip) AudioSystem.getLine(info);
            Sound.open(audioStream);
            Sound.start();

        } catch (Exception e) {}
    }

    public static void StopSound() {
        Sound.stop();
    }


}
