import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private static final int D_W = 1024;
    private static final int D_H = 768;
    private List<Line> lines;
    public int pos = 0;
    public int playerX = 0;
    private int width = 1024;
    private int heigth = 768;
    private int roadW = 2000;
    private int segL = 200; // Segment Length
    private double camD = 0.84; // Camera Depth

    public DrawPanel() {
        this.lines = new ArrayList<>();

        for(int i = 0; i < 1600; i++) {
            Line line = new Line();
            line.z = i * segL;

            if (i > 200 && i < 700) {
                line.curve = 1;
            }

            lines.add(line);
        }

        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        String VK_UP = "VK_UP";
        KeyStroke WVK_UP = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        inputMap.put(WVK_UP, VK_UP);
        actionMap.put(VK_UP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pos += 200;
                repaint();
            }
        });

        String VK_DOWN = "VK_DOWN";
        KeyStroke WVK_DOWN = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        inputMap.put(WVK_DOWN, VK_DOWN);
        actionMap.put(VK_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pos -= 200;
                repaint();
            }
        });

        String VK_LEFT = "VK_LEFT";
        KeyStroke WVK_LEFT = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
        inputMap.put(WVK_LEFT, VK_LEFT);
        actionMap.put(VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerX -= 200;
                repaint();
            }
        });

        String VK_RIGHT = "VK_RIGHT";
        KeyStroke WVK_RIGHT = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        inputMap.put(WVK_RIGHT, VK_RIGHT);
        actionMap.put(VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerX += 200;
                repaint();
            }
        });
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

        public Line() {
            curve = x = y = z = 0;
        }

        void project(int camX, int camY, int camZ) {
            scale = camD / (z - camZ);
            X = (1 + scale * (x - camX)) * width / 2;
            Y = (1 - scale * (y - camY)) * heigth / 2;
            W = scale * roadW * width / 2;
        }
    }
}
