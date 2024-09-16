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
    private List<Cenario> cenarios = new ArrayList<>();
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
        setCenario();
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

    private void setCenario(){
        Cenario setaE = new Cenario(EnviromentVariables.SPRITE_SETAE, 5000);
        Cenario setaD = new Cenario(EnviromentVariables.SPRITE_SETAD, 6000);
        Cenario linha = new Cenario(EnviromentVariables.SPRITE_LINHACHEGADA, 180000);
        cenarios.add(setaE);
        cenarios.add(setaD);
        cenarios.add(linha);
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
            
            g2.setColor(Color.WHITE);
            if (player1.getVelocidade() < 100)
                g2.drawImage(player1.getImagem(1).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else if (player1.getVelocidade() < 200)
                g2.drawImage(player1.getImagem(2).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else if (player1.getVelocidade() <= 299)
                g2.drawImage(player1.getImagem(3).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);
            else
                g2.drawImage(player1.getImagem(4).getImage(), frame.getWidth() - 100, frame.getHeight() - 100, null);

            g.setColor(Color.blue);
            g.fillRect(0, 0, D_W, 392);
        }

        //Desenhando NPCS
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
        
                double baseDepth = 1000;  // Valor base para controle da perspectiva
                double scaleDepth = 300;  // Controla o quão rápido os NPCs diminuem à distância

                // Distância do NPC ao jogador
                double distance = npc.getPos() - pos;

                // Novo fator de escala com base na distância
                double scaleFactor = baseDepth / (distance + scaleDepth);

                // Ajuste os tamanhos do NPC com o novo fator de escala
                int npcWidth = (int) (700 * scaleFactor);  // Largura do NPC ajustada pela escala
                int npcHeight = (int) (350 * scaleFactor);  // Altura do NPC ajustada pela escala

                // Posicionamento na tela
                int npcY = (int) (interpolatedY - npcHeight);

                // Desenhar o NPC com o novo tamanho
                if ((npcHeight > 11 || npcWidth > 11) && (npcHeight < 400 || npcWidth < 400)) {
                    g2.drawImage(npc.getImagem().getImage(), (int) npcX, npcY, npcWidth, npcHeight, null);
                }
            }
        }
        
        npcs.get(0).npcOffset();
        npcs.get(1).npcOffset();
        npcs.get(2).npcOffset();   
        npcs.get(3).npcOffset();
        npcs.get(4).npcOffset();

        for (Cenario cenario : cenarios) {
            if (cenario.getPos() - 800 > pos) {
                int cenarioIndex = (cenario.getPos() / segL) % lines.size();
                Line cenarioLine = lines.get(cenarioIndex);
                Line cenarioNextLine = lines.get(cenarioIndex + 1); // Próxima linha para interpolação
        
                // Fração do cenário dentro do segmento atual (aumentando a fração)
                double fraction = (double) (cenario.getPos() % segL) / segL;
        
                // Interpolação suave apenas para Y
                double interpolatedY = cenarioLine.Y + fraction * (cenarioNextLine.Y - cenarioLine.Y);
        
                // Mantendo o X estático
                double cenarioX = cenarioLine.X + (cenarioLine.W * cenario.getOffset()); // Ajuste de offset
        
                double baseDepth = 1000;  // Valor base para controle da perspectiva
                double scaleDepth = 300;  // Controla o quão rápido os objetos diminuem à distância
        
                // Distância do cenário ao jogador
                double distance = cenario.getPos() - pos;
        
                // Novo fator de escala com base na distância
                double scaleFactor = baseDepth / (distance + scaleDepth);
        
                // Ajuste os tamanhos do cenário com o novo fator de escala
                int cenarioWidth = (int) (cenario.getImagem().getIconWidth() * scaleFactor * 9);  // Largura ajustada pela escala
                int cenarioHeight = (int) (cenario.getImagem().getIconHeight() * scaleFactor * 9);  // Altura ajustada pela escala
        
                // Posicionamento na tela (mantém o Y interpolado e ajusta a altura)
                int cenarioY = (int) (interpolatedY - cenarioHeight);
        
                // Desenhar o cenário com o novo tamanho
                if (cenarioHeight > 11 || cenarioWidth > 11) {
                    g2.drawImage(cenario.getImagem().getImage(), (int) cenarioX, cenarioY, cenarioWidth, cenarioHeight, null);
                }
            }
        }
        g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth()) / 2) - 30, frame.getHeight() - player1.getImagem().getIconHeight() - 300, player1.getImagem().getIconWidth() * 3, player1.getImagem().getIconHeight() * 3, null);
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

    private void makeTurn(int pos1, int pos2, double curve, Line line, int i) {
        if (i > pos1 + this.tamMaxPista * this.count && i < pos2 + this.tamMaxPista * this.count) {
            line.curve = curve;
            
            if (line.curve > 0) {
                line.flagTurn = 1;
            } else if (line.curve < 0) {
                line.flagTurn = -1;
            }

            if (i == pos2 + this.tamMaxPista * this.count - 1) {
                line.flagTurn = 0;
            }
        }
    }

    private void pista1(){
        this.tamMaxPista = 8000;
        this.lap = 3;
        setPosInicial();
        for(int i = 0; i < this.tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;
            //double elevationAtual = 0;

            makeTurn(800, 1000, 1.0, line, i);
            makeTurn(1300, 1450, 0.5, line, i);
            makeTurn(1450, 1750, -0.7, line, i);
            makeTurn(1750, 2000, 1.2, line, i);
            makeTurn(2300, 2450, -0.6, line, i);
            makeTurn(3000, 3150, 0.5, line, i);
            makeTurn(3150, 3300, -0.9, line, i);
            makeTurn(3300, 3450, 0.5, line, i);
            makeTurn(3600, 3800, 1.0, line, i);
            makeTurn(4300, 4750, -1.2, line, i);
            makeTurn(4750, 4850, 0.2, line, i);
            makeTurn(5250, 5450, 1.0, line, i);            
            makeTurn(5800, 6000, 1.0, line, i);
            makeTurn(6300, 6500, -0.7, line, i);
            makeTurn(6750, 6900, 0.5, line, i);
            makeTurn(6900, 7050, -0.6, line, i);
            makeTurn(7050, 7200, 0.5, line, i);
            
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
            if(i == tamMaxPista * (count + 1)) {
                count++;
            }
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

    private void setPosInicial(){
        npcs.get(0).setOffset(-0.5);
        npcs.get(0).setPos(9000);

        npcs.get(1).setOffset(0.5);
        npcs.get(1).setPos(7000);

        npcs.get(2).setOffset(-0.5);
        npcs.get(2).setPos(5000);

        npcs.get(3).setOffset(0.5);
        npcs.get(3).setPos(3000);

        npcs.get(4).setOffset(-0.5);
        npcs.get(4).setPos(1000);
    }
}