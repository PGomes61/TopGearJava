import java.util.ArrayList;
import java.util.List;

public class Racetrack {
    private List<Line> lines;
    private Curva curva = new Curva();
    private DrawPanel drawPanel;
    private int laps, count = 0;
    private int tamPista;

    public Racetrack() {
        for(int i = 0; i < this.tamPista * this.laps; i++) {
            Line line = new Line();
            line.z = i * drawPanel.getSegL();

            if(i > 200 + this.tamPista * this.count && i < 600 + this.tamPista * this.count) {
                line.curve = 1;
                line.flagTurn = 1;
            }

            if(i > 600 + this.tamPista * this.count && i < 1200 + this.tamPista * this.count) {
                line.curve = -1;
                line.flagTurn = -1;
            }

            (this.lines).add(line);
            if(i == this.tamPista * (this.count + 1)) {
                count++;
            }
        }
    }

    public void setTamPista(int tamPista) {
        this.tamPista = tamPista;
    }

    public int getTamPista() {
        return this.tamPista;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public int getLaps() {
        return this.laps;
    }

    public List<Line> getLines() {
        return this.lines;
    }
}
