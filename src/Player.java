import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Player extends Carro implements KeyListener{
    private GameLoop game;
    private String path;
    private ImageIcon velocidadeImg;
    public boolean start = false, pause = false, upPressed = false, leftPressed = false, downPressed = false, rightPressed = false, curva = false, colision = false;
    private double tempo3 = 0, tempo2 = 0, tempo = 0;
    private Sounds acelerando;
    private Sounds freando;

    public Player(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade, GameLoop game){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
        this.game = game;
        this.acelerando = new Sounds();
        this.freando = new Sounds();
        
        new Thread(() -> {
                        try {
                            this.acelerando.setClip("car_accelerate");
                            this.freando.setClip("car_break");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
        
    }

    public ImageIcon getImagem(int caso){
        switch (caso) {
            case 1:
                this.path = EnviromentVariables.VELOCIDADE_0;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
                return velocidadeImg;
            case 2:
                this.path = EnviromentVariables.VELOCIDADE_1;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
                return velocidadeImg;
            case 3:
                this.path = EnviromentVariables.VELOCIDADE_2;
                velocidadeImg = new ImageIcon(path);
                // try {
                //     this.velocidadeImg = ImageIO.read(getClass().getResource(path));
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
                return velocidadeImg;
            case 4:
                this.path = EnviromentVariables.VELOCIDADE_3;
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
                    if(start == true)
                        super.imagem = super.sprites.get(1);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(leftPressed == false && super.velocidadeInicial !=0)
                {
                    rightPressed = true;
                    if(start == true)
                        super.imagem = super.sprites.get(2);
                }
                break;
            case KeyEvent.VK_DOWN:
                if(upPressed == false)
                    downPressed = true;

                    new Thread(() -> {
                        try {
                            freando.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                
                new Thread(() -> {
                    try {
                        acelerando.play();
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }).start();

                break;
            case KeyEvent.VK_ESCAPE:
                if(this.pause == false)
                    game.pauseThread();
                break;
            case KeyEvent.VK_ENTER:
                if(this.pause == true)
                    game.resumeThread();
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

                new Thread(() -> {
                    try {
                        freando.pause();
                        freando.reset();
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }).start();

                break;
            case KeyEvent.VK_UP:
                upPressed = false; 

                new Thread(() -> {
                    try {
                        acelerando.pause();
                        acelerando.reset();
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }).start();

                break;
        }
    }

    public void acelerar(){   
        if(colision == true){
            if(tempo2 == 0 && super.velocidadeInicial + 1 <= 30) {   
                super.velocidadeInicial += super.aceleracao - (super.peso / 2);
                super.velocidadeInicial += super.aceleracao - (super.peso / 2);
                //super.velocidadeInicial += 1;
                tempo2++;
            }
            tempo2--;
            if(tempo2 < 0) {
                tempo2 = 0;
            }
        }

        if(tempo2 == 0 && super.velocidadeInicial + 1 <= super.velocidadeMaxima) {   
        	super.velocidadeInicial += super.aceleracao / super.peso ;


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
