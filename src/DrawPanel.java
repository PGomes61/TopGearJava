import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

public class DrawPanel extends JPanel {
    public double bgOffset = -512;
    public double mgOffset = -512;
    public double fgOffset = -512;
    private static final int D_W = 1024;
    private static final int D_H = 768;
    private List<Line> lines;
    private List<Npc> npcs;
    public Cenario linha = new Cenario(EnviromentVariables.SPRITE_SEMAFORO0, 12000);
    private List<Cenario> cenarios = new ArrayList<>();
    private double playerX = 0;
    private int width = 1024;
    private int segL = 200; // Segment Length
    private int lap, count = 0, pos = 0, posFinal;
    private Player player1;
    private int linhaHorizonte = 300;
    double amplitude = 1000;
    private int tamMaxPista;
    JFrame frame;
    private Map map;
    private Cronometro cronometro;
    private int bigAux=0;

    public DrawPanel(Player player1, JFrame frame, List<Npc> npcs, int pista) {
        this.frame = frame;
        this.npcs = npcs;
        this.lines = new ArrayList<>();
        this.player1 = player1;
        setCenario();
        sortCenario(cenarios);
        this.map = new Map(this);
        this.cronometro = new Cronometro(this);
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

    private void sortCenario(List<Cenario> cenarios){
        Collections.sort(cenarios, new Comparator<Cenario>() {
            @Override
            public int compare(Cenario c1, Cenario c2) {
                return Double.compare(c2.getPos(), c1.getPos()); // Ordena em ordem decrescente
            }
        });
    }

    public void sortNpc(List<Npc> npcs){
        Collections.sort(npcs, new Comparator<Npc>() {
            @Override
            public int compare(Npc n1, Npc n2) {
                return Double.compare(n2.getPos(), n1.getPos()); // Ordena em ordem decrescente
            }
        });
    }

    private void setCenario(){
        Cenario setaE = new Cenario(EnviromentVariables.SPRITE_SETAE, 5000);
        Cenario setaD = new Cenario(EnviromentVariables.SPRITE_SETAD, 6000);
        for(int i = 0; i < 10000; i++){
            Cenario arvores = new Cenario(EnviromentVariables.SPRITE_ARVORE, 9000 + i * 6000);
            Cenario arvores2 = new Cenario(EnviromentVariables.SPRITE_ARVORE2, 9000 + i * 6000);
            Cenario arvores3 = new Cenario(EnviromentVariables.SPRITE_ARVORE3, 9000 + i * 6000);
            Cenario arvores5 = new Cenario(EnviromentVariables.SPRITE_ARVORE5, 9000 + i * 6000);
            Cenario arvores6 = new Cenario(EnviromentVariables.SPRITE_ARVORE6, 9000 + i * 6000);
            cenarios.add(arvores);
            cenarios.add(arvores2);
            cenarios.add(arvores3);
            cenarios.add(arvores5);
            cenarios.add(arvores6);
        }
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

            g.setColor(Color.blue);
            g.fillRect(0, 0, D_W, 392);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_L.getImage(), (int) bgOffset, 367, this);
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_L.getImage(), (int) bgOffset + getWidth(), 367, this);

            //Desenha a camada de meio (velocidade intermediária)
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_M.getImage(), (int) mgOffset, 377, this);
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_M.getImage(), (int) mgOffset + getWidth(), 377, this);

            // Desenha a camada de primeiro plano (move mais rápido)
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_P.getImage(), (int) fgOffset, 382, this);
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_P.getImage(), (int) fgOffset + getWidth(), 382, this);

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

        //Desenhando Cenario
        for (Cenario cenario : cenarios) {
            if (cenario.getPos() - 1800 > pos) {
                ImageIcon cenarioImage = cenario.getImagem();
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
                int cenarioWidth = (int) (cenarioImage.getIconWidth() * scaleFactor * 9);  // Largura ajustada pela escala
                int cenarioHeight = (int) (cenarioImage.getIconHeight() * scaleFactor * 9);  // Altura ajustada pela escala
                
                // Posicionamento na tela (mantém o Y interpolado e ajusta a altura)
                int cenarioY = (int) (interpolatedY - cenarioHeight);
        
                // Desenhar o cenário com o novo tamanho
                if ((cenarioHeight > 26 || cenarioWidth > 26) && (cenarioHeight > 27 || cenarioWidth > 27)) {
                    g2.drawImage(cenarioImage.getImage(), (int) cenarioX, cenarioY, cenarioWidth, cenarioHeight, null);
                }
            }
        }

        //Desenhando NPCS
        //sortNpc(npcs);
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
                npc.setHeight(npcHeight);
                npc.setWidth(npcWidth);
                npc.setX(npcX);
                npc.setY(npcY);

                // Desenhar o NPC com o novo tamanho
                if ((npcHeight > 15 || npcWidth > 15) && (npcHeight < 200 || npcWidth < 200)) {
                    g2.drawImage(npc.getImagem().getImage(), (int) npcX, npcY, npcWidth, npcHeight, null);
                }
            }
            
        }
        map.drawMiniMap(g2);

        ajusteNpcLap();

        cronometro.drawCronometro(g);
        
        npcs.get(0).npcOffset();
        npcs.get(1).npcOffset();
        npcs.get(2).npcOffset();   
        npcs.get(3).npcOffset();
        npcs.get(4).npcOffset();
        g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth()) / 2) - 25, frame.getHeight() - player1.getImagem().getIconHeight() - 200, (int) (player1.getImagem().getIconWidth() * 4), (int) (player1.getImagem().getIconHeight() * 2.5), null);
    }
    
    public void ajusteNpcLap(){
        if(cronometro.getLap()==2&&bigAux==0){
            for(Npc npcs : npcs){
                npcs.setPosLap();
            }
            bigAux=1;
        }else if(cronometro.getLap()==3&&bigAux==1){
            for(Npc npcs : npcs){
                npcs.setPosLap();
            }
            bigAux=2;
        }else if(cronometro.getLap()==4&&bigAux==2){
            for(Npc npcs : npcs){
                npcs.setPosLap();
            }
            bigAux=3;
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

    private void makeTurn(int pos1, int pos2, double curve, Line line, int i) {
        if (i > pos1 + this.tamMaxPista * this.count && i < pos2 + this.tamMaxPista * this.count) {
            System.out.println(pos1);
            line.curve = curve;
            
            if (line.curve > 0) {
                line.flagTurn = 1;
            } else if (line.curve < 0) {
                line.flagTurn = -1;
            }

            if (i == pos2 + this.tamMaxPista * this.count - 1) {
                line.flagTurn = 0;
                line.curve = 0;
            }
        }
    }

    private void pista1(){
        this.tamMaxPista = 8000;
        this.lap = 4;
        int segurança = 1000;
        setPosInicial();
        Cenario linhaa = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 1612000);
        Cenario linhab = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 3212000);
        Cenario linhac = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 4812000);
        Cenario linhad = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 6412000);
        cenarios.add(linhaa);
        cenarios.add(linhab);
        cenarios.add(linhac);
        cenarios.add(linhad);
        posFinal = 6412200;
        for(int i = 0; i < (this.tamMaxPista * lap) + segurança; i++) {
            Line line = new Line();
            line.z = i * segL;
            //double elevationAtual = 0;

            makeTurn(800, 1000, 1.0, line, i);
            makeTurn(1100, 1400, 0.5, line, i);

            makeTurn(1500, 1650, -0.7, line, i);

            makeTurn(1750, 2200, 1.2, line, i);
            makeTurn(2600, 2850, -0.6, line, i);

            makeTurn(3000, 3350, 0.5, line, i);


            makeTurn(3700-10, 3850-10, 0.5, line, i); //curva meio cima
            makeTurn(3900-10, 4050-10, -0.5, line, i); //curva meio cima
            makeTurn(4100-10, 4150-10, 0.5, line, i); //curva meio cima


            //cima
            makeTurn(3600+700, 4000+700, 1.0, line, i); // 4300  // final do de cima


            makeTurn(4200+700, 4300+700, 1.2, line, i);    
            makeTurn(4300+700, 4750+700, -1.2, line, i);    
            makeTurn(4750+700, 4850+700, 0.2, line, i);
            makeTurn(5250+700, 5450+700, 1.0, line, i);      
            makeTurn(5800+700, 6000+700, 1.0, line, i);
            makeTurn(6300+700, 6500+700, -0.7, line, i);
            makeTurn(6750+700, 6900+700, 0.5, line, i);
            makeTurn(6900+700, 7050+700, -0.6, line, i);
            makeTurn(7050+700, 7200+700, 0.5, line, i);
            
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
        this.tamMaxPista = 8050;
        this.lap = 4;
        int segurança = 1000;
        Cenario linhaa = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 1622000);
        Cenario linhab = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 3232000);
        Cenario linhac = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 4842000);
        Cenario linhad = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 6452000);
        cenarios.add(linhaa);
        cenarios.add(linhab);
        cenarios.add(linhac);
        cenarios.add(linhad);
        posFinal = 6452200;
        setPosInicial();
        for(int i = 0; i < (tamMaxPista * lap) + segurança; i++) {
            Line line = new Line();
            line.z = i * segL;
            makeTurn(400, 600, 0.6, line, i);
            makeTurn(600, 750, -0.5, line, i);
            makeTurn(750, 900, 0.5, line, i);
            makeTurn(1200, 1350, 0.5, line, i);
            makeTurn(1750, 1850, -0.3, line, i);
            makeTurn(1950, 2100, 0.6, line, i);
            makeTurn(2300, 2450, 0.6, line, i);
            makeTurn(2650, 2800, 0.8, line, i);
            makeTurn(3550, 3950, -1.2, line, i);
            makeTurn(4350, 4950, 0.6, line, i);
            makeTurn(5150, 5300, 1.0, line, i);
            makeTurn(5800, 5900, 0.4, line, i);
            makeTurn(5900, 6000, -0.5, line, i);
            makeTurn(6200, 6300, -0.4, line, i);
            makeTurn(6300, 6400, 0.4, line, i);
            makeTurn(6600, 6750, 1.0, line, i);
            makeTurn(6850, 6950, 0.5, line, i);
            makeTurn(7100, 7200, -0.5, line, i);
            makeTurn(7350, 7500, -0.8, line, i);
            makeTurn(7700, 7850, 0.7, line, i);
            
            lines.add(line);
            if(i == tamMaxPista * (count + 1))
                count++;
        }
    }
    private void pista3(){
        this.tamMaxPista = 12100;
        this.lap = 3;
        int segurança = 1000;
        Cenario linhaa = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 2432000);
        Cenario linhab = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 4852000);
        Cenario linhac = new Cenario(EnviromentVariables.SPRITE_SEMAFORO7, 7272000);
        cenarios.add(linhaa);
        cenarios.add(linhab);
        cenarios.add(linhac);
        posFinal = 7272200;
        setPosInicial();
        for(int i = 0; i < (tamMaxPista * lap) + segurança; i++) {
            Line line = new Line();
            line.z = i * segL;
            //0 a 700 Reta
            makeTurn(800, 1100, -0.8, line, i);
            //1100 a 1700 Reta
            makeTurn(1700, 2300, -0.1, line, i);
            makeTurn(2300, 2700, -0.3, line, i);
            //reta 2700 a 2800
            makeTurn(2800, 3100, -1.0, line, i);
            //3100 a 3500 Reta
            makeTurn(3500, 3800, -0.8, line, i);
            //3800 a 4200 Reta
            makeTurn(4200, 4600, 0.9, line, i);
            //4600 a 5200 Reta
            makeTurn(5200, 5600, 1.0, line, i);
            //5600 a 6200 Reta
            makeTurn(6200, 6400, 1.0, line, i);
            makeTurn(6400, 6800, 0.3, line, i);
            makeTurn(6800, 7100, 1.1, line, i);
            makeTurn(7100, 7200, -0.2, line, i);
            //7200 a 7600 Reta
            makeTurn(7600, 8100, -1.1, line, i);
            //8100 a 8400 Reta
            makeTurn(8400, 8550, 0.5, line, i);
            makeTurn(8550, 8850, -0.5, line, i);
            makeTurn(8850, 9000, 0.5, line, i);
            //9000 a 9300 Reta
            makeTurn(9300, 9600, -0.9, line, i);
            //9600 a 10500 Reta
            makeTurn(10500, 10600, -0.2, line, i);
            makeTurn(10600, 10900, 0.7, line, i);
            //10900 a 11000 Reta
            makeTurn(11000, 11400, -1.1, line, i);
            //Reta 11400 a 12100
            
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

    public int getPosFinal() {
        return this.posFinal;
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

    public List<Npc> getNpcs(){
        return this.npcs;
    }
}