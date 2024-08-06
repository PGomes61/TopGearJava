import javax.swing.*;
public class Janela extends JFrame{
    private int largura = 1024;
    private int altura = 768;

    public Janela(){
        setTitle("JOGO");
        setSize(largura, altura);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
        setVisible(true);
    }
}
