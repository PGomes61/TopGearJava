import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sounds {

    private Clip clip;
    private FloatControl volumeControl;

    public void play() throws LineUnavailableException {
        this.clip.start();
    }

    public void pause() throws LineUnavailableException {
        this.clip.stop();
    }

    public void reset() throws LineUnavailableException {
        this.clip.setMicrosecondPosition(0);
    }
    
    public void setTo(int x) throws LineUnavailableException {
        this.clip.setMicrosecondPosition(x);
    }

    public void setClip(String nomeDoAudio) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/Game_sounds/" + nomeDoAudio + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioStream);

        // Obtendo controle de volume
        if (this.clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            this.volumeControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        this.setVolume(0.65f);
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum(); // Valor mínimo de volume em dB
            float max = volumeControl.getMaximum(); // Valor máximo de volume em dB
            float volumeDB = (max - min) * volume + min; // Converte volume (0.0 - 1.0) para dB
            volumeControl.setValue(volumeDB);
        }
    }
}
