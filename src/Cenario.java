import javax.swing.ImageIcon;
import java.util.Random;

public class Cenario {
    ImageIcon imagem;
    int pos;
    double offset;
    EnviromentVariables env = new EnviromentVariables();
    Random random = new Random();


    public Cenario(String path, int pos){
        boolean isNegative = random.nextBoolean();
        double number;
        if (isNegative) {
            // Gera número entre [-2, -1)
            number = -2 - random.nextDouble();
        } else {
            // Gera número entre (1, 2]
            number = 1 + random.nextDouble();
        }
        this.imagem = new ImageIcon(path);
        if(path == EnviromentVariables.SPRITE_SETAE)
            this.offset = 1.3;
        if(path == EnviromentVariables.SPRITE_SETAD)
            this.offset = -1.5;
        if(path == EnviromentVariables.SPRITE_ARVORE)
            this.offset = number;
        if(path == EnviromentVariables.SPRITE_ARVORE2)
            this.offset = number;
        if(path == EnviromentVariables.SPRITE_ARVORE3)
            this.offset = number; 
        if(path == EnviromentVariables.SPRITE_ARVORE5)
            this.offset = number;
        if(path == EnviromentVariables.SPRITE_ARVORE6)
            this.offset = number;           
        if(path == EnviromentVariables.SPRITE_SEMAFORO0)
            this.offset = -1.2;
        this.pos = pos;
    }
    
    public void setImagem(String path) {
        this.imagem = new ImageIcon(path);
    }

    public ImageIcon getImagem() {
        return imagem;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }
}
