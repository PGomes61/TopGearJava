import javax.swing.ImageIcon;

public class Cenario {
    ImageIcon imagem;
    int pos;

    public Cenario(String path, int pos){
        this.imagem = new ImageIcon(path);
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

}
