import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
public class PainelCorrida extends JPanel implements Runnable{
    Thread gameThread;
    private JFrame frame;
    private int FPS = 60;
    private Carro player1 = new Carro("src/Carro/Carro1F.png");
    private DrawPanel drawPanel = new DrawPanel(1600, player1);
    private int aux = 0;

    public PainelCorrida(){
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(player1);
        this.setFocusable(true);
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
            int x = ((frame.getWidth() - player1.getImagem(0).getIconWidth())/2);
            int y = ((frame.getHeight() - player1.getImagem(0).getIconHeight())/2);
            int sizeY = frame.getHeight();
            int sizeX = frame.getWidth();
            System.out.println(x + " " + y + " " + sizeX + " " + sizeY);
            System.out.println("Jogo rodando a " + FPS);
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
        //percurso.desenharRetangulo(g2);
        //g2.setColor(Color.white);
        g2.drawImage(player1.getImagem(0).getImage(), ((frame.getWidth() - player1.getImagem(0).getIconWidth())/2) -10, frame.getHeight() - player1.getImagem(0).getIconHeight() - 100, player1.getImagem(0).getIconWidth(), player1.getImagem(0).getIconHeight(), null);
        //g2.fillRect(10, 10, 50, 50);

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
        //percurso.percursoThread = new Thread(this.percurso);
        gameThread.start();
        //percurso.percursoThread.start();
    }

    public void setFrame(JFrame frame){
        this.frame = frame;
    }
}

