import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImagePanelExample extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public ImagePanelExample() {
        //setTitle("Image Panel Example");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800, 600);
        
        //cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        ImageGridPanel imageGridPanel = new ImageGridPanel();
        imageGridPanel.setImagePanels(loadImagePanels());
        
        // Adicione os painéis ao cardPanel
        cardPanel.add(imageGridPanel, "ImageGrid");
        
        // Adicione o cardPanel ao frame
        add(cardPanel, BorderLayout.CENTER);
        
        // Adiciona navegação simples entre cards (botões)
        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.next(cardPanel));
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private List<JPanel> loadImagePanels() {
        List<JPanel> imagePanels = new ArrayList<>();
        
        // Cria e adiciona seus painéis de imagem aqui
        imagePanels.add(new ScaledImagePanel("src/Menus/MenuPrincipal1.png"));
        imagePanels.add(new ScaledImagePanel("src/Menus/MenuPrincipal2.png"));
        // Continue adicionando seus painéis de imagem...
        
        return imagePanels;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImagePanelExample());
    }
}

class ImageGridPanel extends JPanel {
    private List<JPanel> imagePanels = new ArrayList<>();

    public ImageGridPanel() {
        setLayout(new GridBagLayout());
    }

    public void setImagePanels(List<JPanel> imagePanels) {
        this.imagePanels = imagePanels;
        revalidate();
        repaint();
        arrangePanels();
    }

    private void arrangePanels() {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2); // Margens entre painéis
        
        int numPanels = imagePanels.size();
        int rows = (int) Math.sqrt(numPanels);
        int cols = (numPanels / rows) + ((numPanels % rows) > 0 ? 1 : 0);

        for (int i = 0; i < numPanels; i++) {
            gbc.gridx = i % cols;
            gbc.gridy = i / cols;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            add(imagePanels.get(i), gbc);
        }

        revalidate();
        repaint();
    }
}

class ScaledImagePanel extends JPanel {
    private Image image;

    public ScaledImagePanel(String imagePath) {
        try {
            this.image = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        int width = getWidth();
        int height = getHeight();
        g.drawImage(image, 0, 0, width, height, this);
    }
}
