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
        drawMiniMap(g2);

        }
        
        npcs.get(0).npcOffset();
        npcs.get(1).npcOffset();
        npcs.get(2).npcOffset();   
        npcs.get(3).npcOffset();
        npcs.get(4).npcOffset();
        g2.drawImage(player1.getImagem().getImage(), ((frame.getWidth() - player1.getImagem().getIconWidth()) / 2) - 25, frame.getHeight() - player1.getImagem().getIconHeight() - 200, (int) (player1.getImagem().getIconWidth() * 4), (int) (player1.getImagem().getIconHeight() * 2.5), null);
    }


    int[] cx = {106, 105, 104, 104, 103, 103, 102, 101, 100, 99, 98, 92, 93, 94, 95, 96, 97, 98, 87, 88, 89, 90, 91, 92, 87, 87, 87, 86, 85, 86, 84, 85, 84, 83, 83, 82, 82, 82, 81, 82, 81, 81, 80, 78, 79, 80, 77, 78, 77, 76, 75, 74, 74, 73, 72, 71, 71, 71, 71, 71, 71, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 69, 68, 68, 67, 67, 67, 67, 67, 67, 67, 66, 66, 65, 64, 64, 63, 63, 63, 63, 63, 62, 62, 62, 61, 61, 61, 60, 61, 60, 60, 60, 60, 60, 59, 59, 59, 59, 58, 58, 57, 58, 57, 57, 56, 53, 54, 55, 56, 50, 51, 52, 53, 50, 50, 50, 49, 49, 48, 48, 47, 45, 46, 47, 43, 44, 45, 42, 43, 42, 42, 41, 40, 41, 39, 40, 39, 38, 38, 38, 37, 37, 36, 37, 36, 36, 35, 34, 35, 33, 34, 33, 32, 31, 30, 29, 28, 28, 27, 26, 25, 24, 23, 23, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 23, 23, 23, 23, 24, 25, 25, 26, 27, 28, 29, 29, 30, 31, 32, 33, 33, 33, 33, 34, 34, 34, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40, 40, 41, 41, 42, 42, 42, 43, 43, 44, 44, 44, 45, 45, 45, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 45, 45, 45, 44, 44, 44, 44, 44, 44, 43, 42, 42, 41, 41, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 41, 41, 42, 42, 43, 44, 45, 45, 46, 47, 47, 48, 48, 49, 49, 49, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 173, 173, 174, 174, 175, 175, 175, 176, 177, 178, 178, 179, 180, 180, 181, 182, 183, 183, 184, 185, 185, 186, 187, 187, 187, 187, 188, 188, 189, 189, 190, 190, 191, 191, 192, 192, 193, 194, 194, 195, 196, 196, 197, 197, 197, 198, 198, 199, 199, 200, 201, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 221, 222, 223, 223, 223, 224, 224, 224, 225, 226, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 252, 253, 254, 255, 256, 256, 257, 257, 258, 258, 259, 259, 260, 260, 260, 260, 261, 261, 262, 262, 263, 263, 264, 265, 265, 266, 267, 267, 267, 267, 267, 267, 267, 268, 268, 268, 269, 269, 269, 269, 270, 270, 270, 270, 270, 270, 271, 271, 271, 271, 272, 272, 272, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 273, 273, 273, 273, 273, 273, 273, 272, 272, 271, 271, 271, 271, 271, 270, 270, 270, 270, 270, 270, 270, 269, 269, 268, 268, 267, 266, 265, 266, 264, 265, 264, 263, 263, 262, 263, 262, 262, 262, 261, 261, 260, 259, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 209, 208, 208, 207, 207, 206, 206, 205, 206, 205, 205, 204, 203, 204, 202, 203, 201, 202, 201, 200, 199, 199, 198, 197, 196, 189, 190, 191, 192, 193, 194, 195, 196, 189, 188, 187, 186, 185, 184, 183, 182, 182, 181, 180, 180, 179, 178, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 178, 178, 178, 179, 179, 180, 181, 182, 183, 184, 185, 186, 187, 187, 188, 189, 190, 191, 192, 193, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 193, 193, 192, 192, 191, 191, 191, 190, 189, 188, 188, 187, 186, 185, 186, 185, 184, 184, 184, 180, 181, 182, 183, 184, 175, 176, 177, 178, 179, 180, 175, 174, 173, 172, 171, 171, 170, 170, 169, 168, 164, 165, 166, 167, 168, 159, 160, 161, 162, 163, 164, 157, 158, 159, 157, 156, 155, 155, 154, 153, 154, 152, 153, 152, 152, 152, 151, 150, 151, 149, 150, 149, 148, 147, 146, 146, 145, 144, 143, 142, 143, 141, 142, 141, 140, 139, 139, 138, 137, 136, 136, 135, 134, 133, 134, 133, 132, 132, 132, 131, 132, 131, 131, 130, 130, 129, 129, 128, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 106};
    int[] cy = {13, 13, 14, 14, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17, 18, 18, 19, 19, 19, 19, 19, 19, 19, 20, 20, 20, 20, 20, 20, 20, 21, 22, 23, 23, 24, 25, 26, 26, 27, 27, 28, 28, 29, 29, 29, 30, 31, 31, 32, 33, 34, 35, 36, 36, 37, 38, 39, 40, 41, 41, 42, 43, 43, 44, 44, 45, 45, 46, 47, 47, 48, 48, 49, 50, 50, 51, 51, 52, 52, 52, 53, 53, 53, 54, 54, 54, 54, 54, 54, 54, 55, 55, 55, 56, 56, 56, 56, 57, 57, 57, 57, 57, 57, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 59, 59, 60, 60, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 62, 62, 62, 62, 62, 62, 63, 63, 63, 64, 64, 64, 64, 64, 64, 65, 65, 65, 65, 65, 65, 65, 66, 67, 68, 69, 69, 70, 71, 72, 73, 74, 74, 75, 76, 77, 78, 79, 79, 80, 81, 82, 83, 84, 85, 86, 86, 87, 88, 89, 90, 91, 91, 91, 91, 92, 92, 93, 93, 94, 95, 96, 96, 97, 98, 99, 100, 100, 101, 102, 103, 104, 104, 104, 104, 104, 104, 104, 105, 105, 105, 105, 105, 105, 106, 106, 107, 107, 108, 108, 108, 108, 108, 108, 108, 109, 109, 109, 109, 109, 109, 109, 109, 110, 110, 110, 111, 111, 111, 111, 112, 113, 114, 115, 116, 117, 117, 118, 119, 120, 121, 122, 123, 124, 124, 125, 126, 127, 128, 128, 128, 129, 129, 129, 129, 130, 130, 130, 131, 131, 131, 131, 131, 131, 132, 133, 133, 134, 134, 135, 135, 135, 136, 137, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 161, 162, 163, 164, 165, 165, 166, 167, 168, 168, 169, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 169, 168, 167, 167, 166, 165, 165, 164, 164, 164, 164, 164, 164, 164, 163, 163, 163, 163, 163, 163, 163, 163, 163, 162, 162, 161, 161, 161, 161, 161, 161, 161, 161, 161, 161, 160, 160, 160, 160, 160, 160, 160, 159, 158, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 148, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 138, 137, 137, 137, 137, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 135, 135, 134, 134, 134, 133, 133, 133, 133, 133, 133, 132, 132, 132, 132, 132, 132, 132, 132, 131, 131, 130, 129, 129, 128, 127, 127, 127, 126, 127, 126, 126, 126, 126, 126, 125, 125, 125, 125, 124, 124, 123, 124, 123, 123, 123, 123, 123, 123, 122, 122, 122, 121, 121, 120, 121, 120, 120, 120, 119, 118, 117, 116, 115, 114, 113, 112, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 102, 101, 100, 99, 98, 97, 96, 96, 95, 95, 94, 93, 93, 93, 93, 92, 92, 91, 92, 90, 91, 90, 89, 89, 88, 88, 87, 86, 86, 86, 86, 86, 86, 85, 85, 85, 85, 85, 85, 85, 84, 84, 83, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 83, 83, 84, 84, 85, 85, 85, 85, 85, 85, 86, 86, 86, 86, 86, 86, 86, 86, 87, 88, 88, 88, 88, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 88, 88, 88, 88, 88, 88, 88, 88, 87, 86, 86, 85, 84, 83, 79, 80, 81, 82, 83, 73, 74, 75, 76, 77, 78, 79, 73, 72, 71, 70, 69, 68, 67, 67, 66, 65, 64, 63, 62, 61, 60, 59, 59, 58, 57, 56, 55, 54, 53, 52, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 27, 26, 25, 24, 23, 22, 21, 21, 20, 19, 18, 18, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 15, 15, 15, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17, 18, 19, 20, 20, 21, 22, 23, 23, 23, 23, 23, 23, 22, 21, 21, 20, 19, 18, 18, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 15, 15, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13};

    

    void drawQuad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
        int[] xPoints = {x1 - w1, x2 - w2, x2 + w2, x1 + w1};
        int[] yPoints = {y1, y2, y2, y1};
        g.setColor(c);
        g.fillPolygon(xPoints, yPoints, 4);
    }

    public Dimension getPreferredSize() {
        return new Dimension(D_W, D_H);
    }


    
    public void drawMiniMap(Graphics g) {
        // Definir o tamanho do minimapa e sua posição
        int miniMapWidth = 200;
        int miniMapHeight = 200;
        int miniMapX = 10;  // Posição no canto superior esquerdo
        int miniMapY = 10;  // Posição no canto superior esquerdo
        
        int posicaoPlayerX = cx[(int)pos/1000];
        int posicaoPlayerY = cy[(int)pos/1000];
        

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
        for(int i=0 ; i<cx.length; i++){
            g.fillOval((int)cx[i]+20, (int)cy[i]+20, 4, 4);  // Tamanho do ponto do jogador

        }
        g.setColor(Color.BLUE);  // Cor do jogador

        g.fillOval(posicaoPlayerX+17, posicaoPlayerY+17, 10, 10);  // Tamanho do ponto do jogador

        // Desenhar os NPCs no minimapa
        g.setColor(Color.RED);  // Cor dos NPCs
        for (Npc npc : npcs) {
            double npcOffset = npc.getOffset(); // Deslocamento lateral do NPC
            int npcPos = npc.getPos(); // Posição ao longo da pista
            int npcMiniMapX = miniMapX + (int) ((npcPos / roadWidth + 1) * (miniMapWidth / 2));
            int npcMiniMapY = miniMapY + (int) ((npcPos / trackLength) * miniMapHeight);
            g.fillOval(cx[(int)npcPos/100]+20 - 2, cy[(int)npcPos/100]+20 - 2, 4, 4);  // Tamanho do ponto dos NPCs
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