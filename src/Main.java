import javax.swing.SwingUtilities;
import java.awt.*;

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                try {
                    GameLoop pista1 = new GameLoop();
                    Janela janela = new Janela();
                    Menu menu = new Menu(janela, pista1);
                    janela.add(menu.getCont(), BorderLayout.CENTER);
                    menu.getMenu().requestFocusInWindow();
                    janela.setVisible(true); // Certifique-se de que a janela é visível
                    
                } catch (Exception ex) {
                    ex.printStackTrace(); // Para capturar qualquer outra exceção não tratada
                }
            }
        });
    }
}