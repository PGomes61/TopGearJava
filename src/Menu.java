import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class Menu extends JPanel{
    CardLayout cl = new CardLayout();
    private JFrame frame;
    private GameLoop painelCorrida;
    private JPanel menuPrincipal = new JPanel();
    private JPanel pistas = new JPanel();
    private JPanel options = new JPanel();
    private JPanel dificuldades = new JPanel();
    private JPanel pause = new JPanel();
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
    private JPanel pause1 = new JPanel();
    private JPanel pause2 = new JPanel();
    private boolean multiplayer = false;
    private boolean multiplayerAux = false;
    private Sounds mainSong;
    private Sounds menuArrows1;
    private Sounds menuArrows2;
    private Sounds menuArrows3;
    private Sounds menuConfirm;

    public Menu(JFrame frame, GameLoop painelCorrida){
        this.frame = frame;
        this.painelCorrida = painelCorrida;
        this.mainSong = new Sounds();
        this.menuArrows1 = new Sounds();
        this.menuArrows2 = new Sounds();
        this.menuArrows3 = new Sounds();
        this.menuConfirm = new Sounds();

        new Thread(() -> {
                        try {
                            menuArrows1.setClip("menu_change_option");
                            menuArrows2.setClip("menu_change_option");
                            menuArrows3.setClip("menu_change_option");
                            menuConfirm.setClip("menu_select_option");
                            mainSong.setClip("game_soundtrack");
                            mainSong.setVolume(0.55f);
                            mainSong.play();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
        menuP1 = createMenuPanel(EnviromentVariables.MENU_P1);
        menuP2 = createMenuPanel(EnviromentVariables.MENU_P2);
        menuP3 = createMenuPanel(EnviromentVariables.MENU_P3);
        controles = createMenuPanel("src/Menus/Controles.png");
        dificuldade1 = createMenuPanel(EnviromentVariables.DIFICULDADE_1);
        dificuldade2 = createMenuPanel(EnviromentVariables.DIFICULDADE_2);
        dificuldade3 = createMenuPanel(EnviromentVariables.DIFICULDADE_3);
        opt11 = createMenuPanel(EnviromentVariables.OPT11);
        opt12 = createMenuPanel(EnviromentVariables.OPT12);
        opt13 = createMenuPanel(EnviromentVariables.OPT13);
        opt14 = createMenuPanel(EnviromentVariables.OPT14);
        opt15 = createMenuPanel(EnviromentVariables.OPT15);
        opt21 = createMenuPanel(EnviromentVariables.OPT21);
        opt22 = createMenuPanel(EnviromentVariables.OPT22);
        opt23 = createMenuPanel(EnviromentVariables.OPT23);
        opt24 = createMenuPanel(EnviromentVariables.OPT24);
        opt25 = createMenuPanel(EnviromentVariables.OPT25);
        pista1 = createMenuPanel(EnviromentVariables.PISTA1);
        pista2 = createMenuPanel(EnviromentVariables.PISTA2);
        pista3 = createMenuPanel(EnviromentVariables.PISTA3);
        carro1 = createMenuPanel(EnviromentVariables.CARRO1);
        carro2 = createMenuPanel(EnviromentVariables.CARRO2);
        carro3 = createMenuPanel(EnviromentVariables.CARRO3);
        carro4 = createMenuPanel(EnviromentVariables.CARRO4);
        carro5 = createMenuPanel(EnviromentVariables.CARRO5);
        carro6 = createMenuPanel(EnviromentVariables.CARRO6);
        pause1 = createMenuPanel(EnviromentVariables.PAUSE1);
        pause2 = createMenuPanel(EnviromentVariables.PAUSE2);
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
        pause1.setFocusable(true);
        pause2.setFocusable(true);
    }
    
    public void setContainers(){
        menuPrincipal.setLayout(cl);
        options.setLayout(cl);
        dificuldades.setLayout(cl);
        pistas.setLayout(cl);
        carros.setLayout(cl);
        pause.setLayout(cl);
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
        pause.add(pause1, "pa1");
        pause.add(pause2, "pa2");

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(menuPrincipal, "2");
                    menuP2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    System.out.println("3");
                    cl.show(menuPrincipal, "3");
                    menuP3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    frame.remove(menuPrincipal);
                    frame.add(pistas);
                    cl.show(pistas, "p1");
                    painelCorrida.setFrame(frame);
                            //frame.add(carros);
                            //cl.show(carros, "c1");
                            //carro1.requestFocusInWindow();
                    pista1.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        menuP2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(menuPrincipal, "3");
                    menuP3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    System.out.println("1");
                    cl.show(menuPrincipal, "1");
                    menuP1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    System.out.println("2");
                    cl.show(menuPrincipal, "2");
                    menuP2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    System.out.println("Encerrando processo!");
                    System.exit(0);
                }
                
            }
        });

        opt11.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op5");
                    opt15.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op2");
                    opt12.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op1");
                    opt11.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op3");
                    opt13.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op2");
                    opt12.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op4");
                    opt14.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op3");
                    opt13.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op5");
                    opt15.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op4");
                    opt14.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op1");
                    opt11.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op10");
                    opt25.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op7");
                    opt22.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op6");
                    opt21.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op8");
                    opt23.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op7");
                    opt22.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op9");
                    opt24.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op8");
                    opt23.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op10");
                    opt25.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(options, "op9");
                    opt24.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    
                    cl.show(options, "op6");
                    opt21.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(dificuldades, "dif3");
                    dificuldade3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(dificuldades, "dif2");
                    dificuldade2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(dificuldades, "dif1");
                    dificuldade1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(dificuldades, "dif3");
                    dificuldade3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(dificuldades, "dif2");
                    dificuldade2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(dificuldades, "dif1");
                    dificuldade1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

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
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(pistas, "p3");
                    pista3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(pistas, "p2");
                    pista2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    frame.remove(pistas);
                    frame.add(carros);
                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                    painelCorrida.setPistaEscolhida(1);     
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        pista2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(pistas, "p1");
                    pista1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(pistas, "p3");
                    pista3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    frame.remove(pistas);
                    frame.add(carros);
                    cl.show(carros, "c1");
                    painelCorrida.setPistaEscolhida(2);
                    carro1.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        pista3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(pistas, "p2");
                    pista2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(pistas, "p1");
                    pista1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    frame.remove(pistas);
                    frame.add(carros);
                    cl.show(carros, "c1");
                    painelCorrida.setPistaEscolhida(3);
                    carro1.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        carro1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    
                    new Thread(() -> {
                        try {
                            mainSong.pause();
                            mainSong.reset();
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    painelCorrida.setCarroEscolhido(1);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                

            }
        });

        carro2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Thread(() -> {
                        try {
                            mainSong.pause();
                            mainSong.reset();
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    painelCorrida.setCarroEscolhido(2);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                
            }
        });

        carro3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows2.reset();
                            menuArrows2.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Thread(() -> {
                        try {
                            mainSong.pause();
                            mainSong.reset();
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    painelCorrida.setCarroEscolhido(3);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                
            }
        });

        carro4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    
                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Thread(() -> {
                        try {
                            mainSong.pause();
                            mainSong.reset();
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    painelCorrida.setCarroEscolhido(4);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                
            }
        });

        carro5.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c6");
                    carro6.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c1");
                    carro1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows3.reset();
                            menuArrows3.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    cl.show(carros, "c3");
                    carro3.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Thread(() -> {
                        try {
                            mainSong.pause();
                            mainSong.reset();
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    painelCorrida.setCarroEscolhido(5);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                
            }
        });

        carro6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    
                    cl.show(carros, "c5");
                    carro5.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c2");
                    carro2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    new Thread(() -> {
                        try {
                            menuArrows1.reset();
                            menuArrows1.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                    cl.show(carros, "c4");
                    carro4.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new Thread(() -> {
                        try {
                            mainSong.pause();
                            mainSong.reset();
                            menuConfirm.reset();
                            menuConfirm.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                
                    painelCorrida.setCarroEscolhido(6);
                    frame.remove(carros);
                    frame.add(painelCorrida);
                    painelCorrida.setFrame(frame);
                    painelCorrida.startThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                
            }
        });

        painelCorrida.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    frame.remove(painelCorrida);
                    frame.add(pause);
                    cl.show(pause, "pa1");
                    pause1.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        pause1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    frame.remove(pause);
                    frame.add(painelCorrida);
                    painelCorrida.resumeThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    frame.remove(pause);
                    frame.add(painelCorrida);
                    painelCorrida.resumeThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(pause, "pa2");
                    pause2.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(pause, "pa2");
                    pause2.requestFocusInWindow();
                }
            }
        });

        pause2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    frame.remove(pause);
                    frame.add(painelCorrida);
                    painelCorrida.resumeThread();
                    painelCorrida.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new Thread(() -> {
                        try {
                            mainSong.reset();
                            mainSong.play();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    frame.remove(pause);
                    frame.add(menuPrincipal);
                    cl.show(menuPrincipal, "p1");
                    menuP1.requestFocusInWindow();
                    frame.revalidate();
                    frame.repaint();
                    painelCorrida.resumeThread();
                    painelCorrida.stopThread();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    cl.show(pause, "pa1");
                    pause1.requestFocusInWindow();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    cl.show(pause, "pa1");
                    pause1.requestFocusInWindow();
                }
            }
        });
    }
}