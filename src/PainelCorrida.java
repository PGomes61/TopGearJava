import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
public class PainelCorrida extends JPanel implements Runnable{
    EnviromentVariables env = new EnviromentVariables();
    Thread gameThread;
    private JFrame frame;
    private int FPS = 60;
    private Carro carro1;
    private Carro carro2;
    private Carro carro3;
    private Carro carro4;
    private Carro carro5;
    private Carro carro6;
    List<Npc> npcs = new ArrayList<>();
    private Npc npc1;
    private Npc npc2;
    private Npc npc3;
    private Npc npc4;
    private Npc npc5;
    private Player player1;
    private DrawPanel drawPanel;
    private int aux = 0;

    public PainelCorrida(){
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }
    
    private void setNpc(){
        npcs.add(npc1);
        npcs.add(npc2);
        npcs.add(npc3);
        npcs.add(npc4);
        npcs.add(npc5);
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();
            
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if(aux == 0)
        {
            int x = ((frame.getWidth() - player1.getImagem().getIconWidth())/2);
            int y = ((frame.getHeight() - player1.getImagem().getIconHeight())/2);
            int sizeY = frame.getHeight();
            int sizeX = frame.getWidth();
            //System.out.println(x + " " + y + " " + sizeX + " " + sizeY);
            //System.out.println("Jogo rodando a " + FPS);
            System.out.println(drawPanel.pos);
            aux = 61;
        }
        aux--;
        if(player1.upPressed == true && player1.downPressed == false){
            //System.out.println("CIMA");
            drawPanel.pos += 2 * (int) player1.getVelocidade();
            player1.acelerar();
        }
        if(player1.downPressed == true && player1.upPressed == false){
            //System.out.println("BAIXO");
            drawPanel.pos += 2 * (int) player1.getVelocidade();
            player1.freio();
        }
        if(player1.leftPressed == true && player1.rightPressed == false){
            //System.out.println("ESQUERDA");
            if(player1.getVelocidade() != 0 && player1.curva == false)
                drawPanel.playerX -=60 - (player1.getVelocidade() * 0.01);
            if(player1.getVelocidade() != 0 && player1.curva == true)
                drawPanel.playerX -=100 - (player1.getVelocidade() * 0.01);
            if(player1.upPressed == false)
                player1.banguela();
        }
        if(player1.rightPressed == true && player1.leftPressed == false){
            //System.out.println("DIREITA");
            if(player1.getVelocidade() !=0 && player1.curva == false)
                drawPanel.playerX +=60 - (player1.getVelocidade() * 0.01);
            if(player1.getVelocidade() != 0 && player1.curva == true)
                drawPanel.playerX +=100 + (player1.getVelocidade() * 0.01);
            if(player1.upPressed == false)
                player1.banguela();
        }
        if(player1.upPressed == false && player1.downPressed == false){
            player1.banguela();
            drawPanel.pos += 2 * (int) player1.getVelocidade();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawPanel.drawValues(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth())/2) -10, frame.getHeight() - player1.getImagem().getIconHeight() - 100, player1.getImagem().getIconWidth(), player1.getImagem().getIconHeight(), null);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        if(player1.getVelocidade() < 100)
            g2.drawImage(player1.getImagem(1).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
        else if(player1.getVelocidade() < 200)
            g2.drawImage(player1.getImagem(2).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
        else if(player1.getVelocidade() <=299)
            g2.drawImage(player1.getImagem(3).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
        else
            g2.drawImage(player1.getImagem(4).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
        frame.revalidate();
        frame.repaint();

        g2.dispose();
    }

    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setFrame(JFrame frame){
        this.frame = frame;
    }

    public void setCarroEscolhido(int carroEscolhido){
        switch (carroEscolhido) {
            case 1:
                carro1 = new Player(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro1;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 2:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                carro2 = new Player(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro2;
                npc1 = (Npc) carro1;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 3:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Player(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro3;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro1;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 4:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Player(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro4;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro1;
                npc4 = (Npc) carro5;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 5:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Player(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
                carro6 = new Npc(env.SPRITE_C6_F, env.SPRITE_C6_E, env.SPRITE_C6_D, 2, 2, 2, 150);
                player1 = (Player) carro5;
                npc1 = (Npc) carro2;
                npc2 = (Npc) carro3;
                npc3 = (Npc) carro4;
                npc4 = (Npc) carro1;
                npc5 = (Npc) carro6;
                setNpc();
                break;
            case 6:
                carro1 = new Npc(env.SPRITE_C1_F, env.SPRITE_C1_E, env.SPRITE_C1_D, 2, 2, 2, 300);
                carro2 = new Npc(env.SPRITE_C2_F, env.SPRITE_C2_E, env.SPRITE_C2_D, 2, 2, 2, 25);
                carro3 = new Npc(env.SPRITE_C3_F, env.SPRITE_C3_E, env.SPRITE_C3_D, 2, 2, 2, 50);
                carro4 = new Npc(env.SPRITE_C4_F, env.SPRITE_C4_E, env.SPRITE_C4_D, 2, 2, 2, 75);
                carro5 = new Npc(env.SPRITE_C5_F, env.SPRITE_C5_E, env.SPRITE_C5_D, 2, 2, 2, 100);
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
        drawPanel = new DrawPanel(1600, player1, npcs);
    }
}