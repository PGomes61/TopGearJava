import javax.swing.ImageIcon;

public class Cenario {
    ImageIcon imagem;
    int pos;
    double offset;
    EnviromentVariables env = new EnviromentVariables();


    public Cenario(String path, int pos){
        this.imagem = new ImageIcon(path);
        if(path == EnviromentVariables.SPRITE_SETAE)
            this.offset = 1.3;
        if(path == EnviromentVariables.SPRITE_SETAD)
            this.offset = -1.5;
        this.pos = pos;
    }
    
    public void setImagem(ImageIcon imagem) {
        this.imagem = imagem;
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
