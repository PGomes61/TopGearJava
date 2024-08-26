import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

public class Player extends Carro implements KeyListener{
    private String path;
    private ImageIcon velocidadeImg;
    public boolean upPressed = false, leftPressed = false, downPressed = false, rightPressed = false, curva = false, colision = false;
    private double tempo3 = 0, tempo2 = 0, tempo = 0;

    public Player(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
    }

    public ImageIcon getImagem(int caso){
        switch (caso) {
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
                return super.imagem;
        }
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(rightPressed == false && super.velocidadeInicial !=0)
                {
                    leftPressed = true;
                    super.imagem = super.sprites.get(1);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(leftPressed == false && super.velocidadeInicial !=0)
                {
                    rightPressed = true;
                    super.imagem = super.sprites.get(2);
                }
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;    
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
                    super.imagem = super.sprites.get(0);
                }
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                if(leftPressed == false)
                {
                    super.imagem = super.sprites.get(0);
                }
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false; 
                break;
        }
    }

    public void acelerar(){   
        if(colision == false){
            if(tempo2 == 0 && super.velocidadeInicial + 1 <= super.velocidadeMaxima)
            {   
                super.velocidadeInicial += 1;
                System.out.println((int)super.velocidadeInicial);
                super.velocidadeInicial += 1;
                System.out.println((int)super.velocidadeInicial);
                tempo2++;
            }
            tempo2--;
            if(tempo2 < 0)
                tempo2 = 0;
            return;
        }
        if(super.velocidadeInicial - 2 >= 30){
            super.velocidadeInicial -= 1;
            System.out.println((int)super.velocidadeInicial);
            super.velocidadeInicial -= 1;
            System.out.println((int)super.velocidadeInicial);
            super.velocidadeInicial -= 1;
            System.out.println((int)super.velocidadeInicial);
            super.velocidadeInicial -= 1;
            System.out.println((int)super.velocidadeInicial);
        }
    }
    public void banguela(){
        if (tempo3 == 0 && super.velocidadeInicial-1 >=0)
        {
            super.velocidadeInicial = super.velocidadeInicial - 1;
            System.out.println((int)super.velocidadeInicial);
            tempo3 = 3;
        }
        tempo3--;
        if(tempo3 < 0)
            tempo3 = 0;
    }

    public void freio(){
        if(tempo == 0 && super.velocidadeInicial -3 >= 0)
        {
            super.velocidadeInicial -= 1;
            System.out.println((int) super.velocidadeInicial);
            velocidadeInicial -= 1;
            System.out.println((int) super.velocidadeInicial);
            super.velocidadeInicial -= 1;
            System.out.println((int) super.velocidadeInicial);
            tempo = 1;
        }
        else
            super.velocidadeInicial = 0;
        tempo--;
        if(tempo < 0)
            tempo = 0;
    }
    public double getVelocidade(){
        return super.velocidadeInicial;
    }
}
