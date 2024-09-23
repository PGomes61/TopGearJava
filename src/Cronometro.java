import java.awt.*;
import javax.swing.*;

public class Cronometro {
    private int tempoDecorrido = -5; // Tempo em segundos
    private Timer timer;
    private Graphics g;
    private DrawPanel drawPanel;

    private int lap = 1;
    private int lap2 = 6412200/4;
    private int lap3 = 6412200/2;
    private int lap4 = 6412200/4*3;


    public Cronometro(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
        timer = new Timer(1000, e -> {
            tempoDecorrido++;
        });
        timer.start();

    }
    public void drawCronometro(Graphics g){
        this.g = g;
        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        if(tempoDecorrido>=0){
            g.drawString("Time: " + tempoDecorrido + "s", 23, 250); // Posição (10, 30) na tela
        }else{
            g.drawString("Time: " + "0" + "s", 23, 250); // Posição (10, 30) na tela
            
        }
        drawLap();
    }

    
    public void drawLap(){
        if(drawPanel.getPos()>=lap2&&drawPanel.getPos()<=lap3){
            this.lap = 2;
        }else if(drawPanel.getPos()>=lap3&&drawPanel.getPos()<=lap4){
            this.lap = 3;
        }else if(drawPanel.getPos()>=lap4){
            this.lap = 4;
        }
        g.drawString("Lap: " + lap, 23, 280); // Posição (10, 30) na tela
    }

    public int getLap(){
        return this.lap;
    }
}
