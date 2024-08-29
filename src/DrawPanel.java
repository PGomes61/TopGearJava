import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
    private static final int D_W = 1024;
    private static final int D_H = 768;
    private List<Line> lines;
    public int pos = 0;
    public double playerX = 0;
    private int width = 1024;
    private int height = 768;
    private int roadW = 2800;
    private int segL = 200; // Segment Length
    private double camD = 0.84; // Camera Depth
    private int lap = 3, count = 0;
    private Player player1;
    private int linhaHorizonte = 300;
    double amplitude = 1000;
    private int tamMaxPista;
    JFrame frame;

    public DrawPanel(int tamMaxPista, Player player1, JFrame frame) {
        this.frame = frame;
        this.tamMaxPista = tamMaxPista;
        this.lines = new ArrayList<>();
        this.player1 = player1;
        for(int i = 0; i < tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;
            double elevationAtual = 0;

            if (i > 200 + tamMaxPista * count && i < 600 + tamMaxPista * count) {
                line.curve = 1;
                line.flagTurn = 1;
            }

            if (i > 600 + tamMaxPista * count && i < 1200 + tamMaxPista * count) {
                line.curve = -1;
                line.flagTurn = -1;
            }

            // // Add hills and valleys
            // if (i > 50 && i < 150) {
            //     line.elevation = Math.sin(((i % 100) / 50.0) * Math.PI) * amplitude; // Hill
            //     if(line.elevation != Math.abs(line.elevation))
            //         line.elevation = elevationAtual;
            // } 
            // if (i > 151 && i < 250) 
            // {
            //     line.elevation = Math.sin((i % 100) / 50.0 * Math.PI) * -amplitude; // Valley
            //     if(line.elevation == Math.abs(line.elevation))
            //         line.elevation = elevationAtual;
            // }
            // if (i > 200 && i < 600) 
            // {
            //     line.elevation = Math.sin((i % 400) / 200.0 * Math.PI) * -amplitude * 10; // Valley
            //     if(line.elevation == Math.abs(line.elevation))
            //         line.elevation = elevationAtual;
            // }

            lines.add(line);
            if(i == tamMaxPista * (count + 1))
                count++;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawValues(g);
    }

    public void drawValues(Graphics g) {
        int startPos = pos / segL;
        double x = 0, dx = 0;
        for(int n = startPos; n < linhaHorizonte + startPos; n++) {
            Line l = lines.get(n % lines.size());
            l.project(playerX - (int) x, 1500, pos);

            x += dx;
            dx += l.curve;

            if(n > startPos + 1 && n < startPos + 14 && l.flagTurn == 1)
            {
                //System.out.println("Curva Direita!");
                player1.curva = true;
                width = 1024;
                height = 768;
                if(player1.getVelocidade() > 0)
                    playerX -= 0.5 * l.curve * (player1.getVelocidade() * 0.005);
            }
            if(n > startPos + 1 && n < startPos + 14 && l.flagTurn == -1)
            {
                //System.out.println("Curva Esquerda!");
                width = 1024;
                height = 768;
                player1.curva = true;
                if(player1.getVelocidade() > 0)
                    playerX += 0.5 * (- l.curve) * (player1.getVelocidade() * 0.005); 
            }

            if(n == startPos + 2){
                double roadLeftEdge = l.X - l.W;
                double roadRightEdge = l.X + l.W;
                if (playerX < (roadLeftEdge - 10000) || playerX > (roadRightEdge + 7500)) {
                    System.out.println("COLIDINDO");
                    player1.colision = true; 
                }
                else{
                    player1.colision = false;
                }
            }

            
            Color grass = ((n / 2) % 2) == 0 ? new Color(163,128,104) : new Color(120,64,8);
            Color rumble = ((n / 2) % 2) == 0 ? new Color(255,255,255) : new Color(255,0,0);
            Color road = new Color(71,74,81);
            Color trace = ((n / 2) % 4) == 0 ? new Color(255,255,255) : new Color(71,74,81);

            Line p = null;
            if (n == 0) {
                p = l;
            } else {
                p = lines.get((n - 1) % lines.size());
            }

            drawQuad(g, grass, 0, (int) p.Y, width, 0, (int) l.Y, width);
            drawQuad(g, rumble, (int) p.X, (int) p.Y, (int) (p.W * 1.2), (int) l.X, (int) l.Y, (int) (l.W * 1.2));
            drawQuad(g, road, (int) p.X, (int) p.Y, (int) p.W, (int) l.X, (int) l.Y, (int) l.W);
            drawQuad(g, trace, (int) p.X, (int) p.Y, (int) (p.W * 0.05), (int) l.X, (int) l.Y, (int) (l.W * 0.05));
            
            
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth()) / 2) - 10, frame.getHeight() - player1.getImagem().getIconHeight() - 100, player1.getImagem().getIconWidth(), player1.getImagem().getIconHeight(), null);

            g2.setColor(Color.WHITE);
            if (player1.getVelocidade() < 100)
                g2.drawImage(player1.getImagem(1).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else if (player1.getVelocidade() < 200)
                g2.drawImage(player1.getImagem(2).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else if (player1.getVelocidade() <= 299)
                g2.drawImage(player1.getImagem(3).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else
                g2.drawImage(player1.getImagem(4).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
        }

        g.setColor(Color.blue);
        g.fillRect(0, 0, D_W, 392);
    }

    void drawQuad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
        int[] xPoints = {x1 - w1, x2 - w2, x2 + w2, x1 + w1};
        int[] yPoints = {y1, y2, y2, y1};
        g.setColor(c);
        g.fillPolygon(xPoints, yPoints, 4);
    }

    public Dimension getPreferredSize() {
        return new Dimension(D_W, D_H);
    }

    public class Line {
        double x, y, z;   // 3D center of line
        double X, Y, W;   // Screen coordinates
        double scale, curve, elevation;
        int flagTurn;

        public Line() {
            this.curve = x = y = z = 0;
            this.flagTurn = 0;
            this.elevation = 0;
        }

        void project(double camX, int camY, int camZ) {
            scale = camD / (z - camZ);
            X = (1 + scale * (x - camX)) * width / 2;
            Y = (1 - scale * (y - camY + elevation)) * height / 2;
            W = scale * roadW * width/2;
        }
    }
}