import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Carro{
    protected String path;
    protected ImageIcon imagem;
    protected ArrayList<ImageIcon> sprites = new ArrayList<ImageIcon>(); 
    protected double velocidadeInicial = 2;
    private double aceleracao;
    private double peso;
    private double tracao; 
    protected double velocidadeMaxima = 300;
    private double tempo3 = 0, tempo2 = 0, tempo = 0;

    public Carro(String defaultImagePath1,String defaultImagePath2, 
                    String defaultImagePath3, double aceleracao, double peso, double tracao, double velocidadeMaxima){
        this.path = defaultImagePath1;
        this.imagem = new ImageIcon(path);
        sprites.add(new ImageIcon(defaultImagePath1));
        sprites.add(new ImageIcon(defaultImagePath2));
        sprites.add(new ImageIcon(defaultImagePath3));
        this.aceleracao = aceleracao;
        this.peso = peso;
        this.tracao = tracao;
        this. velocidadeMaxima = velocidadeMaxima;
    }

    public ImageIcon getImagem(){
        return imagem;
    }
}