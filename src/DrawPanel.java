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
    private List<Npc> npcs;
    private double playerX = 0;
    private int width = 1024;
    private int segL = 200; // Segment Length
    private int lap, count = 0, pos = 0;
    private Player player1;
    private int linhaHorizonte = 300;
    double amplitude = 1000;
    private int tamMaxPista;
    JFrame frame;

    public DrawPanel(Player player1, JFrame frame, List<Npc> npcs, int pista) {
        this.frame = frame;
        this.npcs = npcs;
        this.lines = new ArrayList<>();
        this.player1 = player1;
        switch(pista){
            case 1:
                pista1();
                break;
            case 2:
                pista2();
                break;
            case 3:
                pista3();
                break;
            default:
                break;
        }
    }

    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        drawValues(g);
    }

    public void drawValues(Graphics g) {
        int startPos = pos / segL;
        Graphics2D g2 = (Graphics2D) g;
        for(int n = startPos; n < linhaHorizonte + startPos; n++) {
            Line l = lines.get(n % lines.size());

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
            
            g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth()) / 2) - 30, frame.getHeight() - player1.getImagem().getIconHeight() - 300, player1.getImagem().getIconWidth() * 3, player1.getImagem().getIconHeight() * 3, null);

            g2.setColor(Color.WHITE);
            if (player1.getVelocidade() < 100)
                g2.drawImage(player1.getImagem(1).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else if (player1.getVelocidade() < 200)
                g2.drawImage(player1.getImagem(2).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else if (player1.getVelocidade() <= 299)
                g2.drawImage(player1.getImagem(3).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else
                g2.drawImage(player1.getImagem(4).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);

            //Desenhando NPCS
            g.setColor(Color.blue);
            g.fillRect(0, 0, D_W, 392);
        }

        for (Npc npc : npcs) {
            if (npc.getPos() > pos) {
                int npcIndex = (npc.getPos() / segL) % lines.size();
                Line npcLine = lines.get(npcIndex);
                Line nextLine = lines.get(npcIndex + 1); // Próxima linha para interpolação
        
                // Fração do NPC dentro do segmento atual (aumentando a fração)
                double fraction = (double) (npc.getPos() % segL) / (segL);
        
                // Interpolação mais suave entre a linha atual e a próxima
                double interpolatedX = npcLine.X + fraction * (nextLine.X - npcLine.X);
                double interpolatedY = npcLine.Y + fraction * (nextLine.Y - npcLine.Y);
                double interpolatedW = npcLine.W + fraction * (nextLine.W - npcLine.W);
        
                // Calcular a posição horizontal e vertical do NPC
                double npcX = interpolatedX + (interpolatedW * npc.getOffset()); // Ajuste de offset
                int scale = (npc.getPos() - pos) / 550;
                int npcY = (int) (interpolatedY - (50 - scale / 2));
        
                // Desenhar o NPC na posição interpolada
                if (100 - scale > 10) {
                    g2.drawImage(npc.getImagem().getImage(), (int) npcX, npcY, 100 - scale, 50 - scale / 2, null);
                }
            }
        }
        npcs.get(0).npcOffset();
        npcs.get(1).npcOffset();
        npcs.get(2).npcOffset();   
        npcs.get(3).npcOffset();
        npcs.get(4).npcOffset();

        int arvoreIndex = (50000 / segL) % lines.size();
        Line arvoreLine = lines.get(arvoreIndex);
        double fraction2 = (double) (50000 % segL) / (segL);
        double interpolatedX = arvoreLine.X + fraction2 * (arvoreLine.X - arvoreLine.X);
        double interpolatedY = arvoreLine.Y + fraction2 * (arvoreLine.Y - arvoreLine.Y);
        double interpolatedW = arvoreLine.W + fraction2 * (arvoreLine.W - arvoreLine.W);
        double arvoreX = interpolatedX + (interpolatedW * 2); // Ajuste de offset
        int scale = (50000 - pos) / 550;
        int arvoreY = (int) (interpolatedY - (50 - scale / 2));

        if (100 - scale > 10) {
            g2.drawImage(npcs.get(1).getImagem().getImage(), (int) arvoreX, arvoreY, 100 - scale, 50 - scale / 2, null);
        }
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

    private void pista1(){
        this.tamMaxPista = 8000;
        this.lap = 3;
        for(int i = 0; i < tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;
            //double elevationAtual = 0;


            if (i > 800 + this.tamMaxPista * count && i < 1000 + this.tamMaxPista * count) {
                line.curve = 1.0;
                line.flagTurn = 1;
            }

            if (i > 1300 + this.tamMaxPista * count && i < 1450 + this.tamMaxPista * count) {
                line.curve = 0.5;
                line.flagTurn = 1;
            }

            if (i > 1450 + this.tamMaxPista * count && i < 1750 + this.tamMaxPista * count) {
                line.curve = -0.7;
                line.flagTurn = -1;
            }
        
            if (i > 1750 + this.tamMaxPista * count && i < 2000 + this.tamMaxPista * count) {
                line.curve = 1.2;
                line.flagTurn = 1;
            }
            
            if (i > 2300 + this.tamMaxPista * count && i < 2450 + this.tamMaxPista * count) {
                line.curve = -0.6;
                line.flagTurn = -1;
            }

            if (i > 2450 + this.tamMaxPista * count && i < 2600 + this.tamMaxPista * count) {
                line.curve = 0.7;
                line.flagTurn = 1;
            }

            if (i > 3000 + this.tamMaxPista * count && i < 3150 + this.tamMaxPista * count) {
                line.curve = 0.5;
                line.flagTurn = 1;
            }

            if (i > 3150 + this.tamMaxPista * count && i < 3300 + this.tamMaxPista * count) {
                line.curve = -0.9;
                line.flagTurn = -1;
            }

            if (i > 3300 + this.tamMaxPista * count && i < 3450 + this.tamMaxPista * count) {
                line.curve = 0.5;
                line.flagTurn = 1;
            }

            if (i > 3600 + this.tamMaxPista * count && i < 3800 + this.tamMaxPista * count) {
                line.curve = 1.0;
                line.flagTurn = 1;
            }

            if (i > 4300 + this.tamMaxPista * count && i < 4750 + this.tamMaxPista * count) {
                line.curve = -1.2;
                line.flagTurn = -1;
            }

            if (i > 4750 + this.tamMaxPista * count && i < 4850 + this.tamMaxPista * count) {
                line.curve = 0.2;
                line.flagTurn = 1;
            }

            if (i > 5250 + this.tamMaxPista * count && i < 5450 + this.tamMaxPista * count) {
                line.curve = 1.0;
                line.flagTurn = 1;
            }

            if (i > 5800 + this.tamMaxPista * count && i < 6000 + this.tamMaxPista * count) {
                line.curve = 1.0;
                line.flagTurn = 1;
            }

            if (i > 6300 + this.tamMaxPista * count && i < 6500 + this.tamMaxPista * count) {
                line.curve = -0.7;
                line.flagTurn = -1;
            }

            if (i > 6750 + this.tamMaxPista * count && i < 6900 + this.tamMaxPista * count) {
                line.curve = 0.5;
                line.flagTurn = 1;
            }
            
            if (i > 6900 + this.tamMaxPista * count && i < 7050 + this.tamMaxPista * count) {
                line.curve = -0.6;
                line.flagTurn = -1;
            }

            if (i > 7050 + this.tamMaxPista * count && i < 7200 + this.tamMaxPista * count) {
                line.curve = 0.5;
                line.flagTurn = 1;
            }
            
            // if (i > 600 + this.tamMaxPista * count && i < 1200 + this.tamMaxPista * count) {
            //     line.curve = -1;
            //     line.flagTurn = -1;
            // }
        
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

    private void pista2(){
        this.tamMaxPista = 1600;
        this.lap = 3;
        for(int i = 0; i < tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;

            if (i > 200 + this.tamMaxPista * count && i < 600 + this.tamMaxPista * count) {
                line.curve = -1;
                line.flagTurn = -1;
            }
        
            if (i > 600 + this.tamMaxPista * count && i < 1200 + this.tamMaxPista * count) {
                line.curve = 1;
                line.flagTurn = 1;
            }
        
            lines.add(line);
            if(i == tamMaxPista * (count + 1))
                count++;
        }
    }
    private void pista3(){
        this.tamMaxPista = 1600;
        this.lap = 3;
        for(int i = 0; i < tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;

            if (i > 200 + this.tamMaxPista * count && i < 600 + this.tamMaxPista * count) {
                line.curve = 1;
                line.flagTurn = 1;
            }
        
            if (i > 600 + this.tamMaxPista * count && i < 1200 + this.tamMaxPista * count) {
                line.curve = -1;
                line.flagTurn = -1;
            }
        
            lines.add(line);
            if(i == tamMaxPista * (count + 1))
                count++;
        }
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public int getSegL() {
        return this.segL;
    }

    public int getLinhaHorizonte() {
        return this.linhaHorizonte;
    }

    public void setPosAcrescimo(int pos) {
        this.pos += pos;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPlayerXDecrescimo(double playerX) {
        this.playerX -= playerX;
    }

    public void setPlayerXAcrescimo(double playerX) {
        this.playerX += playerX;
    }

    public double getPlayerX() {
        return this.playerX;
    }
}