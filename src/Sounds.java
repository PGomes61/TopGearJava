import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Sounds{
    
    private Clip clip;

    Scanner sc = new Scanner(System.in);

    public void play() throws LineUnavailableException{

        this.clip.start();

        @SuppressWarnings("unused")
        String response = sc.next();
        
    } 

    public void pause() throws LineUnavailableException{

        this.clip.stop();

        @SuppressWarnings("unused")
        String response = sc.next();

    }

    public void reset() throws LineUnavailableException{

        this.clip.setMicrosecondPosition(0);
        
        @SuppressWarnings("unused")
        String response = sc.next();

    }

    public void setClip(String nomeDoAudio) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

        File file = new File("./Game_sounds/" + nomeDoAudio + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioStream);

    }
}