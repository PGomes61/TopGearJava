import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    List<Npc> npcs = new ArrayList<>();
    private JFrame frame;
    private Carro carro1, carro2, carro3, carro4, carro5, carro6;
    private Npc npc1, npc2, npc3, npc4, npc5;
    private Player player1;
    private DrawPanel drawPanel;
    private Random random = new Random();
    //private int aux = 0;
    
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

    public void setFrame(JFrame frame) {
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
        // if (aux == 0) {
        //     int x = ((frame.getWidth() - player1.getImagem().getIconWidth())/2);
        //     int y = ((frame.getHeight() - player1.getImagem().getIconHeight())/2);
        //     int sizeY = frame.getHeight();
        //     int sizeX = frame.getWidth();
        //     System.out.println(x + " " + y + " " + sizeX + " " + sizeY);
        //     System.out.println("Jogo rodando a " + TARGET_FPS);
        //     aux = 61;
        // }
        // aux--;
    
        // Lógica do movimento do jogador (como já está implementado)
        if (player1.upPressed && !player1.downPressed) {
            System.out.println("CIMA");
            drawPanel.setPosAcrescimo(2 * (int) player1.getVelocidade());
            player1.acelerar();
        }
        if (player1.downPressed && !player1.upPressed) {
            drawPanel.setPosAcrescimo(2 * (int) player1.getVelocidade());
            player1.freio();
        }

        if (player1.leftPressed && !player1.rightPressed) {
            // Lógica para movimento à esquerda
            double aux = (player1.getVelocidade() * 0.01);
            if(player1.getVelocidade() != 0 && !player1.curva) {
                drawPanel.setPlayerXDecrescimo(60 + aux);
                for (Npc npc : npcs) {
                    npc.setX(npc.getX() + ((60 + aux)/7));
                }
            }
            if(player1.getVelocidade() != 0 && player1.curva) {
                drawPanel.setPlayerXDecrescimo(100 + aux);
                for (Npc npc : npcs) {
                    npc.setX(npc.getX() + ((100 + aux)/7));
                }
            }

            if (!player1.upPressed) {
                player1.banguela();
            }
        }
        if(player1.rightPressed && !player1.leftPressed) {
            // Lógica para movimento à direita
            double aux = (player1.getVelocidade() * 0.01);
            if (player1.getVelocidade() != 0 && !player1.curva) {
                drawPanel.setPlayerXAcrescimo(60 + aux);              
                for (Npc npc : npcs) {
                    npc.setX(npc.getX() - ((60 + aux)/7));
                }  
            }
            if(player1.getVelocidade() != 0 && player1.curva) {
                drawPanel.setPlayerXAcrescimo(100 + aux);
                for (Npc npc : npcs) {
                    npc.setX(npc.getX() - ((100 + aux)/7));
                }
            }

            if(!player1.upPressed) {
                player1.banguela();
            }
        }
        if(!player1.upPressed && !player1.downPressed) {
            player1.banguela();
            drawPanel.setPosAcrescimo(2 * (int) player1.getVelocidade());
        }

        /////// IMPLEMENTAÇÃO DA CURVA ///////

        int startPos = drawPanel.getPos() / drawPanel.getSegL();
        double x = 0, dx = 0;
        for(int n = startPos; n < drawPanel.getLinhaHorizonte() + startPos; n++) {

            Line l = drawPanel.getLines().get(n % drawPanel.getLines().size());
            
            l.project(drawPanel.getPlayerX() - (int) x, 1500, drawPanel.getPos());

            x += dx;
            dx += l.curve;

            if(n > startPos + 1 && n < startPos + 14 && (l.flagTurn == 1 || l.flagTurn == -1)) {
                double auxCurva = 0.5 * l.curve * (player1.getVelocidade() * 0.05);
                player1.curva = true;
                if(player1.getVelocidade() > 0) {
                    drawPanel.setPlayerXDecrescimo(auxCurva);
                }
            }

            // COLISÃO DA GRAMA
            if(n == startPos + 1) {
                double roadLeftEdge = l.X - l.W - 6000;
                double roadRightEdge = l.X + l.W + 6000;
                if(drawPanel.getPlayerX() < roadLeftEdge || drawPanel.getPlayerX() > roadRightEdge) {
                    System.out.println("COLIDINDO");
                    player1.colision = true; 
                }
                else {
                    player1.colision = false;
                }
            }
        }
        ///////////////////////////////////////

        /////NPCs///////
        for (Npc npc : npcs) {
            // Fazer algo com cada NPC
            npc.setPos(random.nextInt(300) + npc.getPos());
            System.out.println(drawPanel.getPos());
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
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25, 100, 100);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50, 200, 200);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75, 300, 300);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro1;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 2:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Player(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50, 200, 200);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75, 300, 300);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150,500, 500);
                player1 = (Player) carro2;
                npc1 = (Npc) carro1;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 3:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Player(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75, 300, 300);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro3;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro1;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 4:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50, 300, 300);
                carro4 = new Player(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro4;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro1;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 5:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50, 300, 300);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75, 400, 400);
                carro5 = new Player(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro5;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro1;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 6:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50, 300, 300);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75, 400, 400);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100, 500, 500);
                carro6 = new Player(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro6;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro1;
                setNpc();
                break;
            default:
                break;
        }
        this.addKeyListener(player1);
        drawPanel = new DrawPanel(1600, player1, frame, npcs);
    }
    private void setNpc(){
        npcs.add(npc1);
        npcs.add(npc2);
        npcs.add(npc3);
        npcs.add(npc4);
        npcs.add(npc5);
    }
}


    