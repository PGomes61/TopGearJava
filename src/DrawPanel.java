import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.BasicStroke;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
    public double bgOffset = -512;
    public double mgOffset = -512;
    public double fgOffset = -512;
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
        sortCenario(cenarios);

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

    private void sortNpc(List<Npc> npcs){
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
        Cenario linha = new Cenario(EnviromentVariables.SPRITE_LINHACHEGADA, 180000);
        // for(int i = 0; i < 10000; i++){
        //     Cenario arvores = new Cenario(EnviromentVariables.SPRITE_ARVORE, 9000 + i * 250);
        //     cenarios.add(arvores);
        // }
        cenarios.add(setaE);
        cenarios.add(setaD);
        cenarios.add(linha);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawValues(g);
        drawMiniMap(g);
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
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_L.getImage(), (int) bgOffset, 257, this);
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_L.getImage(), (int) bgOffset + getWidth(), 257, this);

            //Desenha a camada de meio (velocidade intermediária)
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_M.getImage(), (int) mgOffset, 294, this);
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_M.getImage(), (int) mgOffset + getWidth(), 294, this);

            // Desenha a camada de primeiro plano (move mais rápido)
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_P.getImage(), (int) fgOffset, 367, this);
            g2d.drawImage(EnviromentVariables.SPRITE_PARALAX_P.getImage(), (int) fgOffset + getWidth(), 367, this);

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
            if (cenario.getPos() - 1500 > pos) {
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
                if (cenarioHeight > 26 || cenarioWidth > 26) {
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

                // Desenhar o NPC com o novo tamanho
                if ((npcHeight > 11 || npcWidth > 11) && (npcHeight < 200 || npcWidth < 200)) {
                    g2.drawImage(npc.getImagem().getImage(), (int) npcX, npcY, npcWidth, npcHeight, null);
                }
            }
        }
        
        npcs.get(0).npcOffset();
        npcs.get(1).npcOffset();
        npcs.get(2).npcOffset();   
        npcs.get(3).npcOffset();
        npcs.get(4).npcOffset();
        drawMiniMap(g2);
        drawParabola(g2);
        drawpista(g2);
        drawTrack(g2);
        desenharPista(g2);
        g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth()) / 2) - 25, frame.getHeight() - player1.getImagem().getIconHeight() - 200, (int) (player1.getImagem().getIconWidth() * 4), (int) (player1.getImagem().getIconHeight() * 2.5), null);
    }

    private void desenharPista(Graphics2D g2d) {
        // Define as propriedades da pista
        int larguraPista = 20;
        int xInicial = 50;
        int yInicial = 50;
        int comprimentoSegmento = 5;

        // Desenha a pista segmentada
        for (int i = 0; i < 800; i += comprimentoSegmento) {
            int x1 = xInicial + i;
            int y1 = yInicial + (int) (Math.sin(i * 0.01) * 50); // Efeito de onda

            int x2 = x1 + comprimentoSegmento;
            int y2 = y1 + (int) (Math.sin((i + comprimentoSegmento) * 0.01) * 50); // Efeito de onda

            // Desenha um segmento da pista
            g2d.fillRect(x1, y1, comprimentoSegmento, larguraPista);
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

    private void drawParabola(Graphics2D g2d) {
        // Defina a cor e a espessura da linha
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(6));
    
        // Defina a escala e o deslocamento
        int scale = 5; // Ajuste a escala para adequar à sua tela
        int offsetX = width / 2; // Deslocamento no eixo X para centralizar
        int offsetY = 100 / 2; // Deslocamento no eixo Y para centralizar
    
        // Itera sobre a largura da tela para desenhar a parabola
        for (int x = -width / 2; x < width / 2; x++) {
            int x1 = x + offsetX;
            int y1 = (int) (Math.pow(x / scale, 2) * scale) + offsetY;
            int x2 = x + 1 + offsetX;
            int y2 = (int) (Math.pow((x + 1) / scale, 2) * scale) + offsetY;
    
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawTrack(Graphics2D g2d) {
        // Defina a cor e a espessura da linha
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(6));
    
        // Defina a escala e o deslocamento
        int scale = 10; // Ajuste a escala para adequar à sua tela
        int offsetX = 20; // Deslocamento no eixo X para centralizar
        int offsetY = 15; // Deslocamento no eixo Y para centralizar
    
        // Array de pontos que definem a pista (baseado na imagem fornecida)
        int[] xPoints = {
            50,  100, 150,  200,  250,  300,  350,  370,  350,  300, 250, 200, 150, 100,  50,  30
        };
        int[] yPoints = {
            200, 180, 160,  150,  160,  180,  200,  220,  240,  260, 280, 260, 240, 220,  210, 190
        };
    
        // Aplicando escala e deslocamento nos pontos
        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = xPoints[i] * scale + offsetX;
            yPoints[i] = yPoints[i] * scale + offsetY;
        }
    
        // Desenhar a pista conectando os pontos
        for (int i = 0; i < xPoints.length - 1; i++) {
            g2d.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
        }
    
        // Fechar o loop da pista conectando o último ponto ao primeiro
        g2d.drawLine(10, yPoints[yPoints.length - 1], xPoints[0], yPoints[0]);
    }
    

    private void drawpista(Graphics g) {
        g.setColor(Color.RED);
        
        // Coordenadas iniciais (ponto de partida)
        int x = 100; // Posição inicial em X
        int y = 100; // Posição inicial em Y
        int segL = 10; // Tamanho de cada segmento
    
        int[] xPoints = new int[tamMaxPista * lap];
        int[] yPoints = new int[tamMaxPista * lap];
        
        double angle = 0; // Ângulo inicial da pista
    
        // Loop para calcular os pontos da pista com base em pista1()
        for (int i = 0; i < this.tamMaxPista * lap; i++) {
            Line line = new Line();
            line.z = i * segL;
            
            // Adiciona a curva da pista
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
            
            // Calcula as novas coordenadas com base no ângulo
            angle += line.curve * 0.05; // Ajustar intensidade da curva
            x += (int) (Math.cos(angle) * segL);
            y += (int) (Math.sin(angle) * segL);
    
            // Armazena os pontos para desenhar
            xPoints[i] = x;
            yPoints[i] = y;
        }
    
        // Desenhar o contorno da pista
        g.drawPolyline(xPoints, yPoints, 10 * lap);
    }
    
    
    public void drawMiniMap(Graphics g) {
        // Definir o tamanho do minimapa e sua posição
        int miniMapWidth = 200;
        int miniMapHeight = 200;
        int miniMapX = 10;  // Posição no canto superior esquerdo
        int miniMapY = 10;  // Posição no canto superior esquerdo
        
        int posicaoYDeAcordoComFuncao = miniMapY;

        // Desenhar fundo do minimapa
        g.setColor(Color.DARK_GRAY);
        g.fillRect(miniMapX, miniMapY, miniMapWidth, miniMapHeight);
    
        // Definir dimensões reais da pista
        double roadWidth = 1.0; // Largura da pista (ajuste conforme necessário)
        double trackLength = tamMaxPista * lap * segL; // Comprimento total da pista
    
        // Escalas para o minimapa
        double scaleX = miniMapWidth / (2 * roadWidth);  
        double scaleY = miniMapHeight / trackLength; 
    
        // Desenhar o jogador no minimapa
        g.setColor(Color.GREEN);  // Cor do jogador
        int playerMiniMapX = miniMapX + (int) ((pos / roadWidth + 1) * (miniMapWidth / 2));
        double y = Math.cos(playerMiniMapX/100000);
        System.out.println("oi"+playerMiniMapX/100000);
        System.out.println(y);
        g.fillOval(playerMiniMapX/100000 - 2, (int)(y*2)+20, 4, 4);  // Tamanho do ponto do jogador
        // Desenhar os NPCs no minimapa
        g.setColor(Color.RED);  // Cor dos NPCs
        for (Npc npc : npcs) {
            double npcOffset = npc.getOffset(); // Deslocamento lateral do NPC
            int npcPos = npc.getPos(); // Posição ao longo da pista
            int npcMiniMapX = miniMapX + (int) ((npcPos / roadWidth + 1) * (miniMapWidth / 2));
            int npcMiniMapY = miniMapY + (int) ((npcPos / trackLength) * miniMapHeight);
            g.fillOval(npcMiniMapX/100000 - 2, posicaoYDeAcordoComFuncao - 2, 4, 4);  // Tamanho do ponto dos NPCs
        }
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
                line.curve = 0;
            }
        }
    }

    private void pista1(){
        this.tamMaxPista = 8000;
        this.lap = 4;
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
        this.tamMaxPista = 8050;
        this.lap = 4;
        for(int i = 0; i < tamMaxPista * lap; i++) {
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