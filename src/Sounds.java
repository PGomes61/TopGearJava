import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sounds{
    
    private Clip clip;

    public void play() throws LineUnavailableException{
        this.clip.start();
    } 

    public void pause() throws LineUnavailableException{
        this.clip.stop();
    }

    public void reset() throws LineUnavailableException{

        this.clip.setMicrosecondPosition(0);
    }

    public void setClip(String nomeDoAudio) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File("src/Game_sounds/" + nomeDoAudio + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioStream);
    }
}