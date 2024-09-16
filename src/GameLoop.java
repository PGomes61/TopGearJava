import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
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
    List<Line> line = new ArrayList<>();
    private JFrame frame;
    private Carro carro1, carro2, carro3, carro4, carro5, carro6;
    private Npc npc1, npc2, npc3, npc4, npc5;
    private Player player1;
    private DrawPanel drawPanel;
    private Random random = new Random();
    private int pistaEscolhida, count = 0;
    
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
        if (gameThread != null) {
            try {
                gameThread.interrupt(); // Sinaliza a thread para parar
                gameThread.join(); // Espera a thread terminar
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                gameThread = null; // Permite criar uma nova thread na próxima vez
            }
        }
    }

    public void resumeThread() {
        player1.pause = false; // Sinaliza que a thread deve continuar
        synchronized (this) {
            this.notify(); // Notifica a thread para acordar
        }
    }

    public void pauseThread() {
        player1.pause = true;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        lastTime = System.nanoTime();
        
        while (running) {
            synchronized (this) {
                while (player1.pause) {
                    try {
                        this.wait(); // Coloca a thread em espera enquanto está pausada
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lastTime = System.nanoTime();
                }
            }

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
        if(player1.start == false){
            count++;
            if(count > 1){
                player1.start = true;
                count = 0;
            }
            int startPos = drawPanel.getPos() / drawPanel.getSegL();
            for(int n = startPos; n < drawPanel.getLinhaHorizonte() + startPos; n++) {
                Line l = drawPanel.getLines().get(n % drawPanel.getLines().size());   
                l.project(drawPanel.getPlayerX(), 1500, drawPanel.getPos());
            }
            return;
        }

        /////// IMPLEMENTAÇÃO DA CURVA ///////
        line = drawPanel.getLines();
        int startPos = drawPanel.getPos() / drawPanel.getSegL();
        double x = 0, dx = 0;
        for(int n = startPos; n < drawPanel.getLinhaHorizonte() + startPos; n++) {

            Line l = drawPanel.getLines().get(n % drawPanel.getLines().size());
            Line p = line.get(startPos);
            
            l.project(drawPanel.getPlayerX() - (int) x, 1500, drawPanel.getPos());

            x += 1.5 * dx;
            dx += l.curve;

            if(n > startPos + 1 && n < startPos + 14 && (p.flagTurn == 1 || p.flagTurn == -1)) {
                double auxCurva = 0.5 * p.curve * (player1.getVelocidade() * 0.05);
                player1.curva = true;
                drawPanel.bgOffset -= l.curve * 0.3; // Camada de fundo se move mais devagar
                drawPanel.mgOffset -= l.curve * 0.4;  // Camada de meio se move com velocidade intermediária
                drawPanel.fgOffset -= l.curve * 0.5;  // Camada de primeiro plano se move mais rápido
                if (drawPanel.bgOffset >= 0 || drawPanel.bgOffset <= -512) {
                    drawPanel.bgOffset = -512;  // Resetar o offset para criar um looping contínuo
                }
                if (drawPanel.mgOffset >= 0 || drawPanel.mgOffset <= -512) {
                    drawPanel.mgOffset = -512;  // Resetar o offset para criar um looping contínuo\
                }
                if (drawPanel.fgOffset >= 0 || drawPanel.fgOffset <= -512) {
                    drawPanel.fgOffset = -512;  // Resetar o offset para criar um looping contínuo
                }

                if(player1.getVelocidade() > 0) {
                    drawPanel.setPlayerXDecrescimo(auxCurva);
                }
            }

            if(p.curve == 0.0) {
                player1.curva = false;
            } else {
                player1.curva = true;
            }

            // COLISÃO DA GRAMA
            if(n == startPos + 1) {
                double roadLeftEdge = l.X - l.W - 6000;
                double roadRightEdge = l.X + l.W + 6000;
                if(drawPanel.getPlayerX() < roadLeftEdge || drawPanel.getPlayerX() > roadRightEdge) {
                    System.out.println("COLIDINDO");
                    player1.colidindo();
                    player1.colision = true; 
                }
                else {
                    player1.colision = false;
                }
            }

            // if(l.flagTurn == 0) {
            //     System.out.println("l.flagTurn == 0!");
            // }
        }
        ///////////////////////////////////////

        ////////  MOVIMENTAÇÃO DO CARRO  ///////
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
    
            // if(player1.getVelocidade() <= 50.0 && player1.getVelocidade() != 0.0 && !player1.curva) {
            //     drawPanel.setPlayerXDecrescimo(10 + (player1.getVelocidade() / 10.0));
            // }
    
            if(player1.getVelocidade() != 0.0 && player1.curva == false) {
                System.out.println("player1.curva == false");
                drawPanel.setPlayerXDecrescimo(100 + aux);
            }
    
            if(player1.getVelocidade() != 0.0 && player1.curva == true) {
                System.out.println("player1.curva == true");
                drawPanel.setPlayerXDecrescimo(100 + aux);
            }
    
            if (!player1.upPressed) {
                player1.banguela();
            }
        }

        if(player1.rightPressed && !player1.leftPressed) {
            // Lógica para movimento à direita
            double aux = (player1.getVelocidade() * 0.01);
            
            if (player1.getVelocidade() != 0 && player1.curva == false) {
                System.out.println("player1.curva == false");
                drawPanel.setPlayerXAcrescimo(100 + aux);              
            }

            if(player1.getVelocidade() > 0 && player1.curva == true) {
                System.out.println("player1.curva == true");
                drawPanel.setPlayerXAcrescimo(100 + aux);
            }
    
            if(!player1.upPressed) {
                player1.banguela();
            }
        }

        if(!player1.upPressed && !player1.downPressed) {
            player1.banguela();
            drawPanel.setPosAcrescimo(2 * (int) player1.getVelocidade());
        }

        ////////////////////////////////////////////////

        /////NPCs///////
        for (Npc npc : npcs) {
            // Fazer algo com cada NPC
            int j = random.nextInt(300);
            for(int i = 0; i < j; i++)
                npc.setPos(1 + npc.getPos());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel.drawValues(g);
        if(player1.start == false){
            if(count < 300){
                g.drawImage(EnviromentVariables.SPRITE_SEMAFOROG.getImage(), 483, 213, EnviromentVariables.SPRITE_SEMAFOROG.getIconWidth()/2, EnviromentVariables.SPRITE_SEMAFOROG.getIconHeight()/2, null);
            }
            if(count < 280){
                g.drawImage(EnviromentVariables.SPRITE_SEMAFOROY.getImage(), 483, 213, EnviromentVariables.SPRITE_SEMAFOROY.getIconWidth()/2, EnviromentVariables.SPRITE_SEMAFOROY.getIconHeight()/2, null);
            }
            if(count < 230){
                g.drawImage(EnviromentVariables.SPRITE_SEMAFOROR.getImage(), 483, 213, EnviromentVariables.SPRITE_SEMAFOROR.getIconWidth()/2, EnviromentVariables.SPRITE_SEMAFOROR.getIconHeight()/2, null);
            }
            if(count < 120){
                g.drawImage(EnviromentVariables.SPRITE_SEMAFORO.getImage(), 483, 213, EnviromentVariables.SPRITE_SEMAFORO.getIconWidth()/2, EnviromentVariables.SPRITE_SEMAFORO.getIconHeight()/2, null);
            }
        }
    }

    public void setCarroEscolhido(int carroEscolhido){
        Iterator<Npc> iterator = npcs.iterator();
        while (iterator.hasNext()) {
            Npc npc = iterator.next();
            iterator.remove(); // Remove o carro da lista
        }
        switch (carroEscolhido) {
            case 1:
                carro1 = new Player(EnviromentVariables.SPRITE_C1_F, EnviromentVariables.SPRITE_C1_E, EnviromentVariables.SPRITE_C1_D, 2, 2, 2, 300, this);
                carro2 = new Npc(EnviromentVariables.SPRITE_C2_F, EnviromentVariables.SPRITE_C2_E, EnviromentVariables.SPRITE_C2_D, 2, 2, 2, 25, 100, 100);
                carro3 = new Npc(EnviromentVariables.SPRITE_C3_F, EnviromentVariables.SPRITE_C3_E, EnviromentVariables.SPRITE_C3_D, 2, 2, 2, 50, 200, 200);
                carro4 = new Npc(EnviromentVariables.SPRITE_C4_F, EnviromentVariables.SPRITE_C4_E, EnviromentVariables.SPRITE_C4_D, 2, 2, 2, 75, 300, 300);
                carro5 = new Npc(EnviromentVariables.SPRITE_C5_F, EnviromentVariables.SPRITE_C5_E, EnviromentVariables.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(EnviromentVariables.SPRITE_C6_F, EnviromentVariables.SPRITE_C6_E, EnviromentVariables.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro1;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 2:
                carro1 = new Npc(EnviromentVariables.SPRITE_C1_F, EnviromentVariables.SPRITE_C1_E, EnviromentVariables.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Player(EnviromentVariables.SPRITE_C2_F, EnviromentVariables.SPRITE_C2_E, EnviromentVariables.SPRITE_C2_D, 2, 2, 2, 250, this);
                carro3 = new Npc(EnviromentVariables.SPRITE_C3_F, EnviromentVariables.SPRITE_C3_E, EnviromentVariables.SPRITE_C3_D, 2, 2, 2, 50, 200, 200);
                carro4 = new Npc(EnviromentVariables.SPRITE_C4_F, EnviromentVariables.SPRITE_C4_E, EnviromentVariables.SPRITE_C4_D, 2, 2, 2, 75, 300, 300);
                carro5 = new Npc(EnviromentVariables.SPRITE_C5_F, EnviromentVariables.SPRITE_C5_E, EnviromentVariables.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(EnviromentVariables.SPRITE_C6_F, EnviromentVariables.SPRITE_C6_E, EnviromentVariables.SPRITE_C6_D, 2, 2, 2, 150,500, 500);
                player1 = (Player) carro2;
                npc1 = (Npc) carro1;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 3:
                carro1 = new Npc(EnviromentVariables.SPRITE_C1_F, EnviromentVariables.SPRITE_C1_E, EnviromentVariables.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(EnviromentVariables.SPRITE_C2_F, EnviromentVariables.SPRITE_C2_E, EnviromentVariables.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Player(EnviromentVariables.SPRITE_C3_F, EnviromentVariables.SPRITE_C3_E, EnviromentVariables.SPRITE_C3_D, 2, 2, 2, 50, this);
                carro4 = new Npc(EnviromentVariables.SPRITE_C4_F, EnviromentVariables.SPRITE_C4_E, EnviromentVariables.SPRITE_C4_D, 2, 2, 2, 75, 300, 300);
                carro5 = new Npc(EnviromentVariables.SPRITE_C5_F, EnviromentVariables.SPRITE_C5_E, EnviromentVariables.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(EnviromentVariables.SPRITE_C6_F, EnviromentVariables.SPRITE_C6_E, EnviromentVariables.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro3;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro1;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 4:
                carro1 = new Npc(EnviromentVariables.SPRITE_C1_F, EnviromentVariables.SPRITE_C1_E, EnviromentVariables.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(EnviromentVariables.SPRITE_C2_F, EnviromentVariables.SPRITE_C2_E, EnviromentVariables.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Npc(EnviromentVariables.SPRITE_C3_F, EnviromentVariables.SPRITE_C3_E, EnviromentVariables.SPRITE_C3_D, 2, 2, 2, 50, 300, 300);
                carro4 = new Player(EnviromentVariables.SPRITE_C4_F, EnviromentVariables.SPRITE_C4_E, EnviromentVariables.SPRITE_C4_D, 2, 2, 2, 75, this);
                carro5 = new Npc(EnviromentVariables.SPRITE_C5_F, EnviromentVariables.SPRITE_C5_E, EnviromentVariables.SPRITE_C5_D, 2, 2, 2, 100, 400, 400);
                carro6 = new Npc(EnviromentVariables.SPRITE_C6_F, EnviromentVariables.SPRITE_C6_E, EnviromentVariables.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro4;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro1;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 5:
                carro1 = new Npc(EnviromentVariables.SPRITE_C1_F, EnviromentVariables.SPRITE_C1_E, EnviromentVariables.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(EnviromentVariables.SPRITE_C2_F, EnviromentVariables.SPRITE_C2_E, EnviromentVariables.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Npc(EnviromentVariables.SPRITE_C3_F, EnviromentVariables.SPRITE_C3_E, EnviromentVariables.SPRITE_C3_D, 2, 2, 2, 50, 300, 300);
                carro4 = new Npc(EnviromentVariables.SPRITE_C4_F, EnviromentVariables.SPRITE_C4_E, EnviromentVariables.SPRITE_C4_D, 2, 2, 2, 75, 400, 400);
                carro5 = new Player(EnviromentVariables.SPRITE_C5_F, EnviromentVariables.SPRITE_C5_E, EnviromentVariables.SPRITE_C5_D, 2, 2, 2, 100, this);
                carro6 = new Npc(EnviromentVariables.SPRITE_C6_F, EnviromentVariables.SPRITE_C6_E, EnviromentVariables.SPRITE_C6_D, 2, 2, 2, 150, 500, 500);
                player1 = (Player) carro5;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro1;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 6:
                carro1 = new Npc(EnviromentVariables.SPRITE_C1_F, EnviromentVariables.SPRITE_C1_E, EnviromentVariables.SPRITE_C1_D, 2, 2, 2, 300, 100, 100);
                carro2 = new Npc(EnviromentVariables.SPRITE_C2_F, EnviromentVariables.SPRITE_C2_E, EnviromentVariables.SPRITE_C2_D, 2, 2, 2, 25, 200, 200);
                carro3 = new Npc(EnviromentVariables.SPRITE_C3_F, EnviromentVariables.SPRITE_C3_E, EnviromentVariables.SPRITE_C3_D, 2, 2, 2, 50, 300, 300);
                carro4 = new Npc(EnviromentVariables.SPRITE_C4_F, EnviromentVariables.SPRITE_C4_E, EnviromentVariables.SPRITE_C4_D, 2, 2, 2, 75, 400, 400);
                carro5 = new Npc(EnviromentVariables.SPRITE_C5_F, EnviromentVariables.SPRITE_C5_E, EnviromentVariables.SPRITE_C5_D, 2, 2, 2, 100, 500, 500);
                carro6 = new Player(EnviromentVariables.SPRITE_C6_F, EnviromentVariables.SPRITE_C6_E, EnviromentVariables.SPRITE_C6_D, 2, 2, 2, 150, this);
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
        drawPanel = new DrawPanel(player1, frame, npcs, pistaEscolhida);
    }

    public void setPistaEscolhida(int pistaEscolhida){
        this.pistaEscolhida = pistaEscolhida;
    }

    private void setNpc(){
        npcs.add(npc1);
        npcs.add(npc2);
        npcs.add(npc3);
        npcs.add(npc4);
        npcs.add(npc5);
    }
   
}


    