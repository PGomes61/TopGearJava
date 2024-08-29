import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {
    private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
    private static final double TARGET_FPS = 60.0;
    private static final double TIME_PER_UPDATE = 1.0 / TARGET_FPS;
    
    private Thread gameThread;
    private boolean running;
    private double accumulator = 0.0;
    private long lastTime;


    // Outras variáveis já existentes
    EnviromentVariables env = new EnviromentVariables();
    private JFrame frame;
    private Carro carro1, carro2, carro3, carro4, carro5, carro6;
    private Player player1;
    private DrawPanel drawPanel;
    private int aux = 0;

    
    public GameLoop() {
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    public void startThread() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            running = true;
            gameThread.start();
        }
    }

    public void stopThread() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setFrame(JFrame frame){
        this.frame = frame;
    }

    @Override
    public void run() {
        lastTime = System.nanoTime();
        
        while (running) {
            long currentTime = System.nanoTime();
            double elapsed = (currentTime - lastTime) / NANOSECONDS_PER_SECOND;
            lastTime = currentTime;

            accumulator += elapsed;

            // Atualize o jogo enquanto houver tempo acumulado suficiente
            while (accumulator >= TIME_PER_UPDATE) {
                update();  // Atualiza a lógica do jogo
                accumulator -= TIME_PER_UPDATE;
            }

            repaint();  // Renderiza o jogo

            try {
                Thread.sleep(1); // Pequena pausa para evitar busy-waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (aux == 0) {
            int x = ((frame.getWidth() - player1.getImagem().getIconWidth())/2);
            int y = ((frame.getHeight() - player1.getImagem().getIconHeight())/2);
            int sizeY = frame.getHeight();
            int sizeX = frame.getWidth();
            System.out.println(x + " " + y + " " + sizeX + " " + sizeY);
            System.out.println("Jogo rodando a " + TARGET_FPS);
            aux = 61;
        }
        aux--;
    
        // Lógica do movimento do jogador (como já está implementado)
        if (player1.upPressed && !player1.downPressed) {
            System.out.println("CIMA");
            drawPanel.pos += 2 * (int) player1.getVelocidade();
            player1.acelerar();
        }
        if (player1.downPressed && !player1.upPressed) {
            drawPanel.pos += 2 * (int) player1.getVelocidade();
            player1.freio();
        }
        if (player1.leftPressed && !player1.rightPressed) {
            // Lógica para movimento à esquerda
            System.out.println("ESQUERDA");
            if (player1.getVelocidade() != 0 && !player1.curva)
                drawPanel.playerX -= 60 - (player1.getVelocidade() * 0.01);
            if (player1.getVelocidade() != 0 && player1.curva)
                drawPanel.playerX -= 100 - (player1.getVelocidade() * 0.01);
            if (!player1.upPressed)
                player1.banguela();
        }
        if (player1.rightPressed && !player1.leftPressed) {
            // Lógica para movimento à direita
            if (player1.getVelocidade() != 0 && !player1.curva)
                drawPanel.playerX += 60 - (player1.getVelocidade() * 0.01);
            if (player1.getVelocidade() != 0 && player1.curva)
                drawPanel.playerX += 100 + (player1.getVelocidade() * 0.01);
            if (!player1.upPressed)
                player1.banguela();
        }
        if (!player1.upPressed && !player1.downPressed) {
            player1.banguela();
            drawPanel.pos += 2 * (int) player1.getVelocidade();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel.drawValues(g);
    }

    public void setCarroEscolhido(int carroEscolhido){
        switch (carroEscolhido) {
            case 1:
                carro1 = new Player(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                player1 = (Player) carro1;
                break;
            case 2:
                carro2 = new Player(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                player1 = (Player) carro2;
                break;
            case 3:
                carro3 = new Player(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                player1 = (Player) carro3;
                break;
            case 4:
                carro4 = new Player(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                player1 = (Player) carro4;
                break;
            case 5:
                carro5 = new Player(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                player1 = (Player) carro5;
                break;
            case 6:
                carro6 = new Player(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro6;
                break;
            default:
                break;
        }
        this.addKeyListener(player1);
        drawPanel = new DrawPanel(1600, this.player1, this.frame);
    }
}


    