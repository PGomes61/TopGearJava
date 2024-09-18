import javax.swing.SwingUtilities;
import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                try {
                    Sounds mainSong = new Sounds();
                    Sounds menuArrows1 = new Sounds();
                    Sounds menuArrows2 = new Sounds();
                    Sounds menuArrows3 = new Sounds();
                    Sounds menuConfirm = new Sounds();
                    GameLoop pista1 = new GameLoop();
                    Janela janela = new Janela();
                    Menu menu = new Menu(janela, pista1, mainSong, menuArrows1, menuArrows2, menuArrows3, menuConfirm);
                    janela.add(menu.getCont(), BorderLayout.CENTER);
                    menu.getMenu().requestFocusInWindow();
                    janela.setVisible(true); // Certifique-se de que a janela é visível
                    
                    // Som em uma thread separada
                    new Thread(() -> {
                        try {
                            menuArrows1.setClip("menu_change_option");
                            menuArrows2.setClip("menu_change_option");
                            menuArrows3.setClip("menu_change_option");
                            menuConfirm.setClip("menu_select_option");
                            mainSong.setClip("outracoisa");
                            mainSong.play();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    
                } catch (Exception ex) {
                    ex.printStackTrace(); // Para capturar qualquer outra exceção não tratada
                }
            }
        });
    }
}