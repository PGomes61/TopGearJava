import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

public class Player extends Carro implements KeyListener{
    private EnviromentVariables env = new EnviromentVariables();
    private String path;
    private ImageIcon velocidadeImg;
    public boolean pause = false, upPressed = false, leftPressed = false, downPressed = false, rightPressed = false, curva = false, colision = false;
    private double tempo3 = 0, tempo2 = 0, tempo = 0;

    public Player(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
    }

    public ImageIcon getImagem(int caso){
        switch (caso) {
            case 1:
                this.path = env.VELOCIDADE_0;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
                return velocidadeImg;
            case 2:
                this.path = env.VELOCIDADE_1;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
                return velocidadeImg;
            case 3:
                this.path = env.VELOCIDADE_2;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
                return velocidadeImg;
            case 4:
                this.path = env.VELOCIDADE_3;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
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
            case KeyEvent.VK_ESCAPE:
                this.pause = true;
                break;
            case KeyEvent.VK_ENTER:
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
        if(colision == true){
            if(tempo2 == 0 && super.velocidadeInicial + 1 <= 30) {   
                super.velocidadeInicial += 1;
                super.velocidadeInicial += 1;
                tempo2++;
            }
            tempo2--;
            if(tempo2 < 0) {
                tempo2 = 0;
            }
            return;
        }

        if(tempo2 == 0 && super.velocidadeInicial + 1 <= super.velocidadeMaxima) {   
            super.velocidadeInicial += 1;
            super.velocidadeInicial += 1;

            tempo2++;
        }
        tempo2--;
        if(tempo2 < 0) {
            tempo2 = 0;
        }
    }
    public void banguela(){
        if (tempo3 == 0 && super.velocidadeInicial-1 >=0)
        {
            super.velocidadeInicial = super.velocidadeInicial - 1;
            tempo3 = 3;
        }
        tempo3--;
        if(tempo3 < 0)
            tempo3 = 0;
    }

    public void freio(){
        if(tempo == 0 && super.velocidadeInicial -3 >= 0)
        {
            super.velocidadeInicial -= 1;;
            velocidadeInicial -= 1;;
            super.velocidadeInicial -= 1;;
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

    public void colidindo(){
        if(super.velocidadeInicial >= 30){
            super.velocidadeInicial -= 1;
            super.velocidadeInicial -= 1;
            super.velocidadeInicial -= 1;
            super.velocidadeInicial -= 1;
        }
    }
}
