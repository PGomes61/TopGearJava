import javax.swing.SwingUtilities;
import java.awt.*;
public class Main{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                EnviromentVariables env = new EnviromentVariables();
                PainelCorrida pista1 = new PainelCorrida();
                Janela janela = new Janela();
                Menu menu = new Menu(janela, pista1);
                janela.add(menu.getCont(), BorderLayout.CENTER);
                menu.getMenu().requestFocusInWindow();
            }
        });
    }
}