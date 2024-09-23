import java.awt.*;
import javax.swing.*;

public class Cronometro {
    private int tempoDecorrido = -5; // Tempo em segundos
    private Timer timer;
    private Graphics g;
    private DrawPanel drawPanel;
    
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
            g.drawString("Time: " + tempoDecorrido + "s", 10, 230); // Posição (10, 30) na tela
        }else{
            g.drawString("Time: " + "0" + "s", 10, 230); // Posição (10, 30) na tela
            
        }
    }

    public void setTempoDecorrido(int tempoDecorrido){
        this.tempoDecorrido = tempoDecorrido;
    }
}
