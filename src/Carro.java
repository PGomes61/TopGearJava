import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

public class Carro implements KeyListener{
    private String path;
    private ImageIcon imagem, velocidadeImg;
    public boolean upPressed = false, leftPressed = false, downPressed = false, rightPressed = false, curva = false, colision = false, wPressed = false, sPressed = false;
    private double velocidadeInicial = 2; // V_0
    private double velocidadeMaxima = 300;
    private double tempo3 = 0, tempo2 = 0, tempo = 0;

    public Carro(String defaultImagePath){
        this.path = defaultImagePath;
        this.imagem = new ImageIcon(path);
    }
    public ImageIcon getImagem(int caso){
        switch (caso) {
            case 0:
                return imagem;
            case 1:
                this.path = "src/Velocidade/0km.png";
                this.velocidadeImg = new ImageIcon(path);
                return velocidadeImg;
            case 2:
                this.path = "src/Velocidade/1km.png";
                this.velocidadeImg = new ImageIcon(path);
                return velocidadeImg;
            case 3:
                this.path = "src/Velocidade/2km.png";
                this.velocidadeImg = new ImageIcon(path);
                return velocidadeImg;
            case 4:
                this.path = "src/Velocidade/3km.png";
                this.velocidadeImg = new ImageIcon(path);
                return velocidadeImg;
            default:
                return imagem;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(rightPressed == false && velocidadeInicial !=0)
                {
                    leftPressed = true;
                    path = "src/Carro/Carro1E.png";
                    imagem = new ImageIcon(path);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(leftPressed == false && velocidadeInicial !=0)
                {
                    rightPressed = true;
                    path = "src/Carro/Carro1D.png";
                    imagem = new ImageIcon(path);
                }
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;    
                break;
            case KeyEvent.VK_W:
                wPressed = true;
                break;
            case KeyEvent.VK_S:
                sPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                if(rightPressed == false)
                {
                    path = "src/Carro/Carro1F.png";
                    imagem = new ImageIcon(path);
                }
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                if(leftPressed == false)
                {
                    path = "src/Carro/Carro1F.png";
                    imagem = new ImageIcon(path);
                }
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false; 
                break;
            case KeyEvent.VK_W:
                wPressed = false;
                break;
            case KeyEvent.VK_S:
                sPressed = false;
                break;
        }
    }

    public void acelerar(){   
        if(tempo2 == 0 && velocidadeInicial + 1 <= velocidadeMaxima)
        {   
                velocidadeInicial = velocidadeInicial + 1;
                System.out.println((int)velocidadeInicial);
                velocidadeInicial = velocidadeInicial + 1;
                System.out.println((int)velocidadeInicial);
                tempo2++;
        }
        tempo2--;
        if(tempo2 < 0)
            tempo2 = 0;
    }
    public void banguela(){
        if (tempo3 == 0 && velocidadeInicial-1 >=0)
        {
            velocidadeInicial = velocidadeInicial - 1;
            System.out.println((int)velocidadeInicial);
            tempo3 = 3;
        }
        tempo3--;
        if(tempo3 < 0)
            tempo3 = 0;
    }

    public void freio(){
        if(tempo == 0 && velocidadeInicial -3 >= 0)
        {
            velocidadeInicial -= 1;
            System.out.println((int) velocidadeInicial);
            velocidadeInicial -= 1;
            System.out.println((int) velocidadeInicial);
            velocidadeInicial -= 1;
            System.out.println((int) velocidadeInicial);
            tempo = 1;
        }
        else
            velocidadeInicial = 0;
        tempo--;
        if(tempo < 0)
            tempo = 0;
    }
    public double getVelocidade(){
        return velocidadeInicial;
    }
}
