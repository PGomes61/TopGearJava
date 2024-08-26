import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Menu extends JPanel{
    CardLayout cl = new CardLayout();
    private JFrame frame;
    private PainelCorrida painelCorrida;
    private JPanel menuPrincipal = new JPanel();
    private JPanel pistas = new JPanel();
    private JPanel options = new JPanel();
    private JPanel dificuldades = new JPanel();
    private JPanel carros = new JPanel();
    private JPanel menuP1 = new JPanel();
    private JPanel menuP2 = new JPanel();
    private JPanel menuP3 = new JPanel();
    private JPanel controles = new JPanel();
    private JPanel dificuldade1 = new JPanel();
    private JPanel dificuldade2 = new JPanel();
    private JPanel dificuldade3 = new JPanel();
    private JPanel opt11 = new JPanel();
    private JPanel opt12 = new JPanel();
    private JPanel opt13 = new JPanel();
    private JPanel opt14 = new JPanel();
    private JPanel opt15 = new JPanel();
    private JPanel opt21 = new JPanel();
    private JPanel opt22 = new JPanel();
    private JPanel opt23 = new JPanel();
    private JPanel opt24 = new JPanel();
    private JPanel opt25 = new JPanel();
    private JPanel pista1 = new JPanel();
    private JPanel pista2 = new JPanel();
    private JPanel pista3 = new JPanel();
    private JPanel carro1 = new JPanel();
    private JPanel carro2 = new JPanel();
    private JPanel carro3 = new JPanel();
    private JPanel carro4 = new JPanel();
    private JPanel carro5 = new JPanel();
    private JPanel carro6 = new JPanel();
    private boolean multiplayer = false;
    private boolean multiplayerAux = false;

    public Menu(JFrame frame, PainelCorrida painelCorrida){
        this.frame = frame;
        this.painelCorrida = painelCorrida;
        setImages();
        setContainers();
        setKey();
    }

    public JPanel getCont(){
        return menuPrincipal;
    }
   
    public JPanel getMenu(){
        return menuP1;
    }
    
    private void setImages(){
        //Colocado imagens nos JPANELS!!!!
        menuP1 = createMenuPanel("src/Menus/MenuPrincipal1.png");
        menuP2 = createMenuPanel("src/Menus/MenuPrincipal2.png");
        menuP3 = createMenuPanel("src/Menus/MenuPrincipal3.png");
        controles = createMenuPanel("src/Menus/Controles.png");
        dificuldade1 = createMenuPanel("src/Menus/Dificuldade.png");
        dificuldade2 = createMenuPanel("src/Menus/Dificuldade2.png");
        dificuldade3 = createMenuPanel("src/Menus/Dificuldade3.png");
        opt11 = createMenuPanel("src/Menus/Opt1 1.png");
        opt12 = createMenuPanel("src/Menus/Opt1 2.png");
        opt13 = createMenuPanel("src/Menus/Opt1 3.png");
        opt14 = createMenuPanel("src/Menus/Opt1 4.png");
        opt15 = createMenuPanel("src/Menus/Opt1 5.png");
        opt21 = createMenuPanel("src/Menus/Opt2 1.png");
        opt22 = createMenuPanel("src/Menus/Opt2 2.png");
        opt23 = createMenuPanel("src/Menus/Opt2 3.png");
        opt24 = createMenuPanel("src/Menus/Opt2 4.png");
        opt25 = createMenuPanel("src/Menus/Opt2 5.png");
        pista1 = createMenuPanel("src/Menus/Pista1.png");
        pista2 = createMenuPanel("src/Menus/Pista2.png");
        pista3 = createMenuPanel("src/Menus/Pista3.png");
        carro1 = createMenuPanel("src/Menus/Carro1.png");
        carro2 = createMenuPanel("src/Menus/Carro2.png");
        carro3 = createMenuPanel("src/Menus/Carro3.png");
        carro4 = createMenuPanel("src/Menus/Carro4.png");
        carro5 = createMenuPanel("src/Menus/Carro5.png");
        carro6 = createMenuPanel("src/Menus/Carro6.png");
        menuP1.setFocusable(true);
        menuP2.setFocusable(true);
        menuP3.setFocusable(true);
        controles.setFocusable(true);
        dificuldade1.setFocusable(true);
        dificuldade2.setFocusable(true);
        dificuldade3.setFocusable(true);
        opt11.setFocusable(true);
        opt12.setFocusable(true);
        opt13.setFocusable(true);
        opt14.setFocusable(true);
        opt15.setFocusable(true);
        opt21.setFocusable(true);
        opt22.setFocusable(true);
        opt23.setFocusable(true);
        opt24.setFocusable(true);
        opt25.setFocusable(true);
        pista1.setFocusable(true);
        pista2.setFocusable(true);
        pista3.setFocusable(true);
    }
    
    public void setContainers(){
        menuPrincipal.setLayout(cl);
        options.setLayout(cl);
        dificuldades.setLayout(cl);
        pistas.setLayout(cl);
        carros.setLayout(cl);
        menuPrincipal.add(menuP1, "1");
        menuPrincipal.add(menuP2, "2");
        menuPrincipal.add(menuP3, "3");
        pistas.add(pista1, "p1");
        pistas.add(pista2, "p2");
        pistas.add(pista3, "p3");
        dificuldades.add(dificuldade1, "dif1");
        dificuldades.add(dificuldade2, "dif2");
        dificuldades.add(dificuldade3, "dif3");
        options.add(opt11, "op1");
        options.add(opt12, "op2");
        options.add(opt13, "op3");
        options.add(opt14, "op4");
        options.add(opt15, "op5");
        options.add(opt21, "op6");
        options.add(opt22, "op7");
        options.add(opt23, "op8");
        options.add(opt24, "op9");
        options.add(opt25, "op10");
        carros.add(carro1, "c1");
        carros.add(carro2, "c2");
        carros.add(carro3, "c3");
        carros.add(carro4, "c4");
        carros.add(carro5, "c5");
        carros.add(carro6, "c6");
    }
    
    private JPanel createMenuPanel(String imagePath) {
        Resize panel = new Resize(imagePath);
        panel.setLayout(new BorderLayout());
        return panel;
    }
    
    private void setKey(){
        menuP1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(menuPrincipal, "2");
                    menuP2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    System.out.println("3");
                    cl.show(menuPrincipal, "3");
                    menuP3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.remove(menuPrincipal);
                        //frame.add(pistas);
                        //cl.show(pistas, "p1");
                    painelCorrida.setCarroEscolhido(1);
                    frame.add(painelCorrida);
                            //frame.add(carros);
                            //cl.show(carros, "c1");
                            //carro1.requestFocusInWindow();
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                        //pista1.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        menuP2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(menuPrincipal, "3");
                    menuP3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if(multiplayer){
                        frame.add(options);
                        cl.show(options, "op6");
                        opt21.requestFocusInWindow();
                    }
                    else{
                        frame.add(options);
                        cl.show(options, "op1");
                        opt11.requestFocusInWindow();
                    }
                }
            }
        });

        menuP3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("1");
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    System.out.println("2");
                    cl.show(menuPrincipal, "2");
                    menuP2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    System.out.println("Encerrando processo!");
                    System.exit(0);
                }
                
            }
        });

        opt11.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op5");
                    opt15.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op2");
                    opt12.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    cl.show(options, "op6");
                    opt21.requestFocusInWindow();
                    multiplayerAux = true;

                }
            }
        });

        opt12.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op1");
                    opt11.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op3");
                    opt13.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.add(dificuldades);
                    cl.show(dificuldades, "dif1");
                    dificuldades.revalidate();
                    dificuldades.repaint();
                    dificuldade1.requestFocusInWindow();
                }
            }
        });

        opt13.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op2");
                    opt12.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op4");
                    opt14.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.add(controles);
                    controles.revalidate();
                    controles.repaint();
                    controles.requestFocusInWindow();
                }
            }
        });

        opt14.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op3");
                    opt13.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op5");
                    opt15.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    multiplayer = false;
                    multiplayerAux = false;
                    frame.add(menuPrincipal);
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                    frame.remove(options);
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("Salvando");
                }
            }
        });

        opt15.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op4");
                    opt14.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op1");
                    opt11.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    multiplayerAux = multiplayer;
                    frame.add(menuPrincipal);
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                    frame.remove(options);
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("Cancelando");
                }
            }
        });

        opt21.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op10");
                    opt25.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op7");
                    opt22.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    cl.show(options, "op1");
                    opt11.requestFocusInWindow();
                    multiplayerAux = false;
                }
            }
        });

        opt22.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op6");
                    opt21.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op8");
                    opt23.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.add(dificuldades);
                    dificuldades.revalidate();
                    dificuldades.repaint();
                    cl.show(dificuldades, "dif1");
                    dificuldade1.requestFocusInWindow();
                }
            }
        });

        opt23.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op7");
                    opt22.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op9");
                    opt24.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.add(controles);
                    controles.revalidate();
                    controles.repaint();
                    controles.requestFocusInWindow();
                }
            }
        });

        opt24.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op8");
                    opt23.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op10");
                    opt25.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    multiplayer = true;
                    multiplayerAux = true;
                    frame.add(menuPrincipal);
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                    frame.remove(options);
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("Salvando");
                }
            }
        });

        opt25.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(options, "op9");
                    opt24.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(options, "op6");
                    opt21.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    multiplayerAux = multiplayer;
                    frame.add(menuPrincipal);
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                    frame.remove(options);
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("Cancelando");
                }
            }
        });

        dificuldade1.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(dificuldades, "dif3");
                    dificuldade3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(dificuldades, "dif2");
                    dificuldade2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(multiplayerAux){
                        cl.show(options, "op7");
                        frame.remove(dificuldades);
                        options.revalidate();
                        options.repaint();
                        opt22.requestFocusInWindow();
                    }  
                    else{
                        cl.show(options, "op2");
                        frame.remove(dificuldades);
                        options.revalidate();
                        options.repaint();
                        opt12.requestFocusInWindow();
                    }
                }
            }
        });

        dificuldade2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(dificuldades, "dif1");
                    dificuldade1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(dificuldades, "dif3");
                    dificuldade3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(multiplayerAux){
                        cl.show(options, "op7");
                        frame.remove(dificuldades);
                        options.revalidate();
                        options.repaint();
                        opt22.requestFocusInWindow();
                    }
                    else{
                        cl.show(options, "op2");
                        frame.remove(dificuldades);
                        options.revalidate();
                        options.repaint();
                        opt12.requestFocusInWindow();
                    }
                }
            }
        });

        dificuldade3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(dificuldades, "dif2");
                    dificuldade2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(dificuldades, "dif1");
                    dificuldade1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(multiplayerAux){
                        cl.show(options, "op7");
                        frame.remove(dificuldades);
                        options.revalidate();
                        options.repaint();
                        opt22.requestFocusInWindow();
                    }
                    else{
                        cl.show(options, "op2");
                        frame.remove(dificuldades);
                        options.revalidate();
                        options.repaint();
                        opt12.requestFocusInWindow();
                    }
                }
            }
        });

        controles.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                    if(multiplayerAux){
                        cl.show(options, "op8");
                        options.revalidate();
                        options.repaint();
                        opt23.requestFocusInWindow();
                        frame.remove(controles);
                    }
                    else{
                        cl.show(options, "op3");
                        options.revalidate();
                        options.repaint();
                        opt13.requestFocusInWindow();
                        frame.remove(controles);
                    }
            }
        });

        pista1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(pistas, "p3");
                    pista3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(pistas, "p2");
                    pista2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.remove(pistas);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        pista2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(pistas, "p1");
                    pista1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(pistas, "p3");
                    pista3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.remove(pistas);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        pista3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(pistas, "p2");
                    pista2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(pistas, "p1");
                    pista1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.remove(pistas);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    painelCorrida.setCarroEscolhido(1);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    painelCorrida.setCarroEscolhido(2);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    painelCorrida.setCarroEscolhido(3);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    painelCorrida.setCarroEscolhido(4);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro5.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    painelCorrida.setCarroEscolhido(5);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    painelCorrida.setCarroEscolhido(6);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocus(true);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }
}
