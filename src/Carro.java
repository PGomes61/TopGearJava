import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Carro {
    protected String path;
    protected ImageIcon imagem;
    protected ArrayList<ImageIcon> sprites = new ArrayList<ImageIcon>(); 
    protected double velocidadeInicial = 2;
    private double aceleracao;
    private double peso;
    private double tracao; 
    protected double velocidadeMaxima = 300;
    private double tempo3 = 0, tempo2 = 0, tempo = 0;

    public Carro(String defaultImagePath1, String defaultImagePath2, String defaultImagePath3, double aceleracao, double peso, double tracao, double velocidadeMaxima) {
        this.path = defaultImagePath1;
        this. imagem = new ImageIcon(path);
        ImageIcon sprite1 = new ImageIcon(defaultImagePath1);
        ImageIcon sprite2 = new ImageIcon(defaultImagePath2);
        ImageIcon sprite3 = new ImageIcon(defaultImagePath3);
        sprites.add(sprite1);
        sprites.add(sprite2);
        sprites.add(sprite3);
        // try {
        //     // ClassLoader classLoader = getClass().getClassLoader();
        //     // this.imagem = ImageIO.read(classLoader.getResource(defaultImagePath1));
        //     // BufferedImage sprite1 = ImageIO.read(classLoader.getResource(defaultImagePath1));
        //     // BufferedImage sprite2 = ImageIO.read(classLoader.getResource(defaultImagePath2));
        //     // BufferedImage sprite3 = ImageIO.read(classLoader.getResource(defaultImagePath3));
        //     sprites.add(sprite1);
        //     sprites.add(sprite2);
        //     sprites.add(sprite3);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        this.aceleracao = aceleracao;
        this.peso = peso;
        this.tracao = tracao;
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public ImageIcon getImagem() {
        return imagem;
    }
}
