import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Npc extends Carro{
    gameThread Thread;


    public Npc(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
    }
}