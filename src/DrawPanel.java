import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private static final int D_W = 1024;
    private static final int D_H = 768;
    private List<Line> lines;
    public int pos = 0;
    public double playerX = 0;
    private int width = 1024;
    private int heigth = 768;
    private int roadW = 2000;
    private int segL = 200; // Segment Length
    private double camD = 0.84; // Camera Depth
    private int lap = 100, count = 0;
    private Carro player1;

    public DrawPanel(int tamMaxPista, Carro player1) {
        this.lines = new ArrayList<>();
        this.player1 = player1;
        for(int i = 0; i < tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;

            if (i > 200 + tamMaxPista * count && i < 600 + tamMaxPista * count) {
                line.curve = 1;
                line.flagTurn = 1;
            }

            if (i > 650 + tamMaxPista * count && i < 1050 + tamMaxPista * count) {
                line.curve = -1;
                line.flagTurn = -1;
            }

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

        for(int n = startPos; n < 300 + startPos; n++) {
            Line l = lines.get(n % lines.size());
            l.project(playerX - (int) x, 1500, pos);

            x += dx;
            dx += l.curve;

            if(n > startPos + 25 && n < startPos + 37 && l.flagTurn == 1)
            {
                
                //System.out.println("Curva Direita!");
                if(player1.getVelocidade() > 0)
                    playerX -= 5/60.0 * l.curve * player1.getVelocidade() * 0.018;
            }
            
            if(n > startPos + 25 && n < startPos + 37 && l.flagTurn == -1)
            {
                //System.out.println("Curva Esquerda!");
                if(player1.getVelocidade() > 0)
                    playerX += 5/60.0 * (- l.curve) * player1.getVelocidade() * 0.018;
            }

            Color grass = ((n / 2) % 2) == 0 ? new Color(16,200,16) : new Color(0,154,0);
            Color rumble = ((n / 2) % 2) == 0 ? new Color(255,255,255) : new Color(255,0,0);
            Color road = Color.black;
            Color trace = ((n / 2) % 4) == 0 ? new Color(255,255,255) : new Color(0,0,0);

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
        double scale, curve;
        int flagTurn;

        public Line() {
            this.curve = x = y = z = 0;
            this.flagTurn = 0;
        }

        void project(double camX, int camY, int camZ) {
            scale = camD / (z - camZ);
            X = (1 + scale * (x - camX)) * width / 2;
            Y = (1 - scale * (y - camY)) * heigth / 2;
            W = scale * roadW * width / 2;
        }
    }
}
