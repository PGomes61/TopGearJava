        import java.awt.*;
    import javax.swing.ImageIcon;


    public class Map{
        private DrawPanel drawPanel;
        private Graphics g;
        private int mapChoice=2;

        private static Color npcColor = Color.RED;
        private static Color playerColor = Color.BLUE;

        int[] cx;
        int[] cy;

        private int miniMapWidth = 300;
        private int miniMapHeight = 200;
        private int miniMapX = 10;  // Posição no canto superior esquerdo
        private int miniMapY = 10;  // Posição no canto superior esquerdo

        private int posFinal1 =  6412200/4;

        private Image minimapImage;  // Variável para armazenar a imagem do minimapa

        private float speedAdjust = 0.0006269299f;





        public Map(DrawPanel drawPanel,int choice) {
            this.drawPanel = drawPanel;
            this.mapChoice = choice;
            ImageIcon icon;
            switch (choice) {
                case 1:
                    icon = new ImageIcon("/home/luan/projetos/TopGearJava/src/Menus/map1.png");
                    break;
                case 2:
                    icon = new ImageIcon("/home/luan/projetos/TopGearJava/src/Menus/map2.jpg");
                    this.miniMapWidth = 350;
                    this.miniMapHeight = 225;
                    //this.speedAdjust = 0.0007160348f;
                    break;
                default:
                    icon = new ImageIcon("/home/luan/projetos/TopGearJava/src/Menus/map2.png");
                    break;
            }
            this.minimapImage = icon.getImage().getScaledInstance(miniMapWidth, miniMapHeight, Image.SCALE_SMOOTH);
            this.cx = maps[mapChoice-1][0];
            this.cy = maps[mapChoice-1][1];
        }

  
        int[] cx1 = {149, 148, 147, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100, 99, 98, 97, 96, 95, 94, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 49, 49, 49, 48, 48, 47, 47, 46, 45, 45, 44, 43, 42, 42, 41, 41, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 41, 41, 42, 42, 43, 44, 44, 44, 44, 44, 44, 45, 45, 45, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 45, 45, 45, 44, 44, 44, 43, 43, 42, 42, 42, 41, 41, 40, 40, 39, 39, 38, 38, 37, 37, 36, 36, 35, 35, 34, 34, 34, 33, 33, 33, 33, 32, 31, 30, 29, 29, 28, 27, 26, 25, 25, 24, 23, 23, 23, 23, 23, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 23, 24, 25, 26, 27, 28, 28, 29, 30, 31, 32, 33, 34, 33, 35, 34, 35, 36, 36, 37, 36, 37, 37, 38, 38, 38, 39, 40, 39, 41, 40, 41, 42, 42, 43, 42, 45, 44, 43, 47, 46, 45, 47, 48, 48, 49, 49, 50, 50, 50, 53, 52, 51, 50, 56, 55, 54, 53, 56, 57, 57, 58, 57, 58, 58, 59, 59, 59, 59, 60, 60, 60, 60, 60, 61, 60, 61, 61, 61, 62, 62, 62, 63, 63, 63, 63, 63, 64, 64, 65, 66, 66, 67, 67, 67, 67, 67, 67, 67, 68, 68, 69, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 71, 71, 71, 71, 71, 71, 72, 73, 74, 74, 75, 76, 77, 78, 77, 80, 79, 78, 80, 81, 81, 82, 81, 82, 82, 82, 83, 83, 84, 85, 84, 86, 85, 86, 87, 87, 87, 92, 91, 90, 89, 88, 87, 98, 97, 96, 95, 94, 93, 92, 98, 99, 100, 101, 102, 103, 103, 104, 104, 105, 106, 106, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 128, 129, 129, 130, 130, 131, 131, 132, 131, 132, 132, 132, 133, 134, 133, 134, 135, 136, 136, 137, 138, 139, 139, 140, 141, 142, 141, 143, 142, 143, 144, 145, 146, 146, 147, 148, 149, 150, 149, 151, 150, 151, 152, 152, 152, 153, 152, 154, 153, 154, 155, 155, 156, 157, 159, 158, 157, 164, 163, 162, 161, 160, 159, 168, 167, 166, 165, 164, 168, 169, 170, 170, 171, 171, 172, 173, 174, 175, 180, 179, 178, 177, 176, 175, 184, 183, 182, 181, 180, 184, 184, 184, 185, 186, 185, 186, 187, 188, 188, 189, 190, 191, 191, 191, 192, 192, 193, 193, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 193, 192, 191, 190, 189, 188, 187, 187, 186, 185, 184, 183, 182, 181, 180, 179, 179, 178, 178, 178, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 178, 179, 180, 180, 181, 182, 182, 183, 184, 185, 186, 187, 188, 189, 196, 195, 194, 193, 192, 191, 190, 189, 196, 197, 198, 199, 199, 200, 201, 202, 201, 203, 202, 204, 203, 204, 205, 205, 206, 205, 206, 206, 207, 207, 208, 208, 209, 229, 228, 227, 226, 225, 224, 223, 222, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 210, 209, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 259, 258, 257, 256, 255, 254, 253, 252, 251, 250, 259, 260, 261, 261, 262, 262, 262, 263, 262, 263, 263, 264, 265, 264, 266, 265, 266, 267, 268, 268, 269, 269, 270, 270, 270, 270, 270, 270, 270, 271, 271, 271, 271, 271, 272, 272, 273, 273, 273, 273, 273, 273, 273, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 272, 272, 272, 271, 271, 271, 271, 270, 270, 270, 270, 270, 270, 269, 269, 269, 269, 268, 268, 268, 267, 267, 267, 267, 267, 267, 267, 266, 265, 265, 264, 263, 263, 262, 262, 261, 261, 260, 260, 260, 260, 259, 259, 258, 258, 257, 257, 256, 256, 255, 254, 253, 252, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 228, 227, 226, 226, 225, 224, 224, 224, 223, 223, 223, 222, 221, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 211, 210, 209, 208, 207, 206, 205, 204, 203, 202, 201, 201, 200, 199, 199, 198, 198, 197, 197, 197, 196, 196, 195, 194, 194, 193, 192, 192, 191, 191, 190, 190, 189, 189, 188, 188, 187, 187, 187, 187, 186, 185, 185, 184, 183, 183, 182, 181, 180, 180, 179, 178, 178, 177, 176, 175, 175, 175, 174, 174, 173, 173, 173, 172, 171, 170, 169, 168, 167, 166, 165, 164, 163, 162, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150};
        int[] cy1 = {170, 170, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 169, 168, 168, 167, 166, 165, 165, 164, 163, 162, 161, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 149, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 137, 136, 135, 135, 135, 134, 134, 133, 133, 132, 131, 131, 131, 131, 131, 131, 130, 130, 130, 129, 129, 129, 129, 128, 128, 128, 127, 126, 125, 124, 124, 123, 122, 121, 120, 119, 118, 117, 117, 116, 115, 114, 113, 112, 111, 111, 111, 111, 110, 110, 110, 109, 109, 109, 109, 109, 109, 109, 109, 108, 108, 108, 108, 108, 108, 108, 107, 107, 106, 106, 105, 105, 105, 105, 105, 105, 104, 104, 104, 104, 104, 104, 104, 103, 102, 101, 100, 100, 99, 98, 97, 96, 96, 95, 94, 93, 93, 92, 92, 91, 91, 91, 91, 90, 89, 88, 87, 86, 86, 85, 84, 83, 82, 81, 80, 79, 79, 78, 77, 76, 75, 74, 74, 73, 72, 71, 70, 69, 69, 68, 67, 66, 65, 65, 65, 65, 65, 65, 65, 64, 64, 64, 64, 64, 64, 63, 63, 63, 62, 62, 62, 62, 62, 62, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 60, 60, 59, 59, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 57, 57, 57, 57, 57, 57, 56, 56, 56, 56, 55, 55, 55, 54, 54, 54, 54, 54, 54, 54, 53, 53, 53, 52, 52, 52, 51, 51, 50, 50, 49, 48, 48, 47, 47, 46, 45, 45, 44, 44, 43, 43, 42, 41, 41, 40, 39, 38, 37, 36, 36, 35, 34, 33, 32, 31, 31, 30, 29, 29, 29, 28, 28, 27, 27, 26, 26, 25, 24, 23, 23, 22, 21, 20, 20, 20, 20, 20, 20, 20, 19, 19, 19, 19, 19, 19, 19, 18, 18, 17, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 15, 15, 15, 15, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 23, 23, 23, 23, 23, 22, 21, 20, 20, 19, 18, 17, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 24, 25, 26, 27, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 52, 53, 54, 55, 56, 57, 58, 59, 59, 60, 61, 62, 63, 64, 65, 66, 67, 67, 68, 69, 70, 71, 72, 73, 79, 78, 77, 76, 75, 74, 73, 83, 82, 81, 80, 79, 83, 84, 85, 86, 86, 87, 88, 88, 88, 88, 88, 88, 88, 88, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 88, 88, 88, 88, 87, 86, 86, 86, 86, 86, 86, 86, 86, 85, 85, 85, 85, 85, 85, 84, 84, 83, 83, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 83, 84, 84, 85, 85, 85, 85, 85, 85, 85, 86, 86, 86, 86, 86, 86, 87, 88, 88, 89, 89, 90, 91, 90, 92, 91, 92, 92, 93, 93, 93, 93, 94, 95, 95, 96, 96, 97, 98, 99, 100, 101, 102, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 112, 113, 114, 115, 116, 117, 118, 119, 120, 120, 120, 121, 120, 121, 121, 122, 122, 122, 123, 123, 123, 123, 123, 123, 124, 123, 124, 124, 125, 125, 125, 125, 126, 126, 126, 126, 126, 127, 126, 127, 127, 127, 128, 129, 129, 130, 131, 131, 132, 132, 132, 132, 132, 132, 132, 132, 133, 133, 133, 133, 133, 133, 134, 134, 134, 135, 135, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 137, 137, 137, 137, 138, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 158, 159, 160, 160, 160, 160, 160, 160, 160, 161, 161, 161, 161, 161, 161, 161, 161, 161, 161, 162, 162, 163, 163, 163, 163, 163, 163, 163, 163, 163, 164, 164, 164, 164, 164, 164, 164, 165, 165, 166, 167, 167, 168, 169, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170};


        int[] cx2 = {101, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 59, 58, 57, 57, 56, 55, 54, 54, 53, 52, 52, 51, 51, 50, 50, 49, 48, 47, 47, 46, 45, 44, 43, 43, 43, 43, 42, 42, 41, 41, 41, 41, 41, 40, 40, 40, 40, 40, 40, 40, 39, 39, 39, 39, 39, 38, 37, 36, 35, 35, 34, 33, 33, 32, 32, 32, 31, 31, 30, 30, 30, 30, 30, 29, 28, 28, 27, 27, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 27, 28, 28, 28, 28, 28, 29, 29, 29, 29, 29, 29, 30, 30, 30, 30, 30, 31, 32, 32, 32, 32, 32, 33, 33, 33, 33, 33, 33, 34, 35, 35, 35, 35, 35, 36, 36, 36, 36, 36, 36, 37, 37, 38, 39, 39, 39, 39, 39, 40, 40, 40, 40, 40, 41, 41, 42, 43, 43, 43, 43, 43, 43, 43, 44, 44, 45, 46, 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 48, 49, 49, 49, 49, 49, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 51, 51, 52, 53, 53, 53, 53, 53, 54, 54, 54, 54, 54, 54, 55, 56, 57, 58, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 70, 69, 68, 70, 71, 72, 71, 72, 72, 73, 74, 75, 75, 76, 77, 78, 79, 79, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 121, 122, 123, 124, 125, 125, 126, 127, 128, 130, 129, 128, 131, 130, 131, 132, 132, 132, 132, 132, 132, 133, 133, 134, 135, 135, 135, 135, 135, 135, 136, 136, 136, 136, 136, 137, 137, 137, 138, 139, 139, 139, 139, 139, 139, 139, 139, 139, 140, 140, 140, 140, 141, 141, 141, 141, 141, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 143, 143, 143, 143, 143, 144, 144, 144, 144, 144, 145, 145, 145, 145, 145, 145, 145, 146, 146, 146, 146, 146, 146, 146, 146, 146, 146, 147, 147, 147, 148, 148, 149, 149, 149, 149, 149, 149, 149, 149, 149, 149, 149, 150, 150, 150, 150, 151, 152, 153, 153, 154, 155, 156, 157, 161, 160, 159, 158, 157, 162, 161, 162, 163, 163, 163, 164, 165, 165, 166, 167, 172, 171, 170, 169, 168, 167, 172, 173, 174, 175, 176, 177, 178, 178, 178, 179, 180, 181, 182, 182, 182, 182, 182, 182, 182, 182, 182, 182, 183, 183, 183, 183, 183, 183, 184, 184, 184, 185, 186, 186, 186, 186, 186, 186, 186, 186, 186, 187, 187, 188, 189, 189, 189, 189, 189, 189, 190, 190, 190, 190, 190, 191, 191, 192, 193, 193, 193, 193, 193, 193, 194, 194, 195, 196, 196, 196, 196, 196, 196, 197, 197, 197, 197, 198, 199, 199, 200, 201, 202, 201, 202, 203, 204, 203, 204, 204, 205, 206, 207, 208, 209, 210, 210, 211, 212, 213, 214, 214, 214, 215, 216, 215, 217, 216, 217, 218, 219, 220, 219, 220, 220, 221, 224, 223, 222, 221, 230, 229, 228, 227, 226, 225, 224, 230, 231, 232, 231, 232, 232, 233, 234, 234, 235, 236, 238, 237, 236, 238, 239, 240, 240, 241, 242, 244, 243, 242, 244, 245, 246, 245, 246, 246, 247, 248, 249, 250, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 283, 282, 281, 280, 279, 278, 277, 276, 275, 274, 273, 272, 271, 270, 269, 268, 267, 266, 265, 264, 263, 262, 261, 260, 283, 284, 285, 286, 287, 288, 289, 289, 290, 291, 292, 291, 293, 292, 293, 294, 295, 294, 296, 295, 296, 296, 296, 296, 297, 298, 297, 298, 299, 300, 301, 301, 301, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 303, 303, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302, 301, 301, 300, 299, 299, 299, 299, 299, 298, 297, 297, 297, 296, 295, 294, 293, 293, 293, 293, 292, 292, 292, 292, 292, 292, 291, 291, 291, 291, 291, 290, 290, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 289, 290, 291, 292, 292, 293, 293, 293, 293, 293, 294, 295, 294, 295, 295, 295, 295, 295, 295, 295, 295, 295, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 296, 295, 295, 295, 295, 295, 295, 295, 295, 295, 294, 293, 293, 292, 292, 292, 292, 292, 291, 291, 291, 291, 291, 290, 289, 289, 288, 287, 287, 287, 286, 286, 285, 284, 283, 282, 282, 281, 280, 279, 278, 277, 276, 275, 274, 273, 272, 271, 270, 269, 268, 267, 266, 265, 264, 264, 263, 262, 261, 260, 259, 258, 257, 256, 256, 255, 254, 253, 252, 252, 251, 250, 250, 249, 248, 248, 247, 246, 246, 246, 245, 245, 244, 244, 243, 242, 242, 241, 240, 239, 239, 238, 237, 236, 235, 235, 235, 234, 234, 233, 233, 232, 232, 231, 230, 230, 229, 228, 227, 226, 226, 225, 224, 223, 222, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 212, 211, 210, 209, 208, 207, 207, 206, 206, 206, 205, 205, 204, 203, 203, 202, 202, 201, 201, 200, 200, 200, 199, 199, 198, 198, 198, 198, 198, 198, 198, 197, 197, 197, 197, 197, 197, 197, 196, 195, 195, 194, 194, 194, 194, 194, 194, 193, 192, 192, 191, 191, 191, 191, 190, 190, 189, 189, 189, 188, 188, 188, 188, 188, 187, 187, 186, 186, 186, 185, 184, 184, 183, 182, 181, 180, 179, 178, 177, 176, 175, 174, 173, 172, 171, 171, 170, 169, 168, 167, 166, 165, 164, 163, 162, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102};
        int[] cy2 = {185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 183, 182, 182, 182, 182, 182, 182, 182, 181, 181, 181, 181, 181, 181, 181, 180, 179, 178, 178, 177, 176, 175, 174, 173, 173, 173, 172, 172, 172, 172, 172, 171, 171, 170, 170, 170, 169, 168, 167, 167, 166, 165, 164, 163, 163, 162, 161, 160, 159, 159, 158, 158, 158, 157, 157, 157, 157, 157, 156, 156, 155, 154, 154, 153, 152, 152, 151, 150, 149, 148, 147, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 111, 110, 109, 109, 109, 108, 108, 107, 107, 106, 106, 105, 105, 104, 104, 104, 103, 103, 102, 101, 101, 101, 100, 100, 99, 99, 98, 98, 97, 97, 96, 95, 95, 95, 94, 94, 93, 93, 92, 91, 91, 90, 89, 89, 88, 87, 87, 87, 86, 86, 85, 85, 84, 84, 83, 82, 82, 81, 80, 80, 79, 78, 78, 77, 76, 75, 75, 74, 73, 73, 73, 72, 72, 71, 71, 70, 70, 69, 69, 68, 67, 67, 67, 66, 66, 65, 65, 64, 63, 62, 61, 60, 59, 58, 57, 57, 56, 55, 54, 53, 52, 51, 51, 50, 49, 49, 49, 48, 48, 47, 47, 46, 45, 45, 44, 43, 42, 41, 40, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29, 28, 27, 27, 26, 25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 25, 26, 27, 28, 28, 29, 30, 31, 31, 31, 31, 31, 31, 31, 32, 34, 33, 32, 34, 35, 36, 36, 37, 38, 39, 38, 40, 39, 40, 41, 42, 41, 42, 43, 44, 44, 45, 46, 47, 51, 50, 49, 48, 47, 52, 51, 52, 53, 53, 53, 54, 55, 55, 56, 57, 58, 59, 67, 66, 65, 64, 63, 62, 61, 60, 59, 68, 67, 68, 69, 69, 70, 69, 70, 71, 71, 72, 71, 72, 73, 73, 74, 73, 74, 75, 76, 77, 83, 82, 81, 80, 79, 78, 77, 83, 84, 85, 85, 86, 87, 88, 89, 95, 94, 93, 92, 91, 90, 89, 96, 95, 96, 97, 97, 97, 98, 99, 100, 101, 101, 101, 102, 103, 104, 104, 104, 104, 104, 104, 104, 104, 104, 105, 105, 105, 105, 106, 106, 106, 107, 107, 107, 107, 107, 107, 107, 107, 106, 105, 104, 103, 102, 101, 101, 100, 99, 98, 97, 96, 96, 95, 94, 93, 92, 91, 91, 90, 90, 89, 89, 88, 87, 87, 86, 85, 85, 84, 83, 82, 82, 81, 80, 79, 78, 77, 77, 76, 75, 75, 74, 73, 73, 72, 72, 71, 71, 70, 70, 69, 69, 68, 67, 67, 66, 65, 65, 64, 63, 63, 62, 61, 61, 60, 59, 59, 58, 58, 57, 57, 56, 56, 56, 55, 54, 53, 53, 52, 52, 52, 52, 52, 51, 51, 51, 51, 51, 50, 49, 48, 47, 46, 45, 45, 44, 43, 42, 42, 42, 42, 41, 41, 41, 41, 41, 41, 40, 39, 39, 39, 39, 39, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 37, 37, 37, 37, 37, 36, 35, 35, 34, 34, 34, 34, 34, 34, 33, 32, 32, 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29, 28, 27, 26, 26, 25, 25, 25, 25, 25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 25, 25, 26, 27, 27, 27, 27, 27, 27, 28, 28, 28, 28, 28, 29, 28, 29, 29, 30, 30, 30, 30, 31, 32, 33, 33, 34, 35, 36, 35, 36, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 82, 84, 83, 82, 84, 85, 85, 86, 87, 89, 88, 87, 89, 90, 91, 92, 92, 93, 94, 95, 96, 97, 96, 97, 98, 98, 100, 99, 98, 100, 101, 101, 102, 101, 102, 103, 103, 104, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 129, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 129, 130, 131, 132, 132, 133, 134, 133, 134, 134, 135, 135, 135, 136, 135, 137, 136, 137, 138, 139, 140, 141, 142, 157, 156, 155, 154, 153, 152, 151, 150, 149, 148, 147, 146, 145, 144, 143, 142, 157, 158, 159, 160, 161, 162, 163, 162, 163, 163, 164, 165, 165, 166, 167, 168, 167, 168, 169, 170, 169, 170, 170, 171, 172, 172, 173, 174, 174, 174, 174, 174, 174, 174, 174, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 175, 174, 174, 173, 173, 172, 172, 171, 171, 171, 170, 169, 168, 168, 168, 168, 168, 168, 167, 166, 166, 165, 165, 165, 165, 164, 164, 164, 164, 164, 164, 164, 163, 162, 161, 161, 160, 159, 158, 158, 158, 158, 157, 157, 157, 157, 157, 157, 156, 155, 155, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 155, 155, 155, 155, 155, 156, 157, 157, 157, 157, 157, 157, 158, 158, 158, 158, 158, 159, 160, 163, 162, 161, 160, 163, 164, 165, 166, 165, 167, 166, 167, 168, 169, 169, 170, 171, 173, 172, 171, 173, 174, 175, 175, 176, 177, 177, 177, 178, 178, 179, 179, 179, 179, 180, 179, 180, 180, 181, 181, 182, 182, 182, 182, 183, 183, 183, 183, 183, 183, 183, 183, 184, 184, 184, 184, 184, 184, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185, 185}; 
            
        int[] cx3 = {213, 212, 211, 210, 210, 209, 208, 208, 207, 206, 205, 204, 204, 203, 202, 201, 200, 199, 198, 198, 198, 198, 198, 198, 198, 197, 196, 196, 196, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 195, 196, 196, 196, 197, 198, 198, 198, 198, 198, 198, 198, 198, 199, 199, 199, 199, 199, 200, 200, 201, 201, 201, 201, 202, 202, 202, 202, 202, 202, 202, 202, 202, 202, 203, 203, 204, 205, 205, 206, 207, 208, 208, 209, 209, 210, 211, 211, 211, 212, 213, 213, 214, 214, 215, 216, 217, 217, 218, 219, 220, 221, 221, 222, 223, 223, 224, 224, 224, 225, 226, 226, 227, 227, 228, 228, 228, 229, 230, 231, 232, 232, 233, 234, 235, 235, 236, 237, 238, 238, 239, 240, 241, 242, 242, 243, 244, 244, 245, 245, 245, 246, 247, 247, 248, 248, 249, 249, 249, 250, 251, 251, 252, 253, 253, 254, 255, 256, 256, 256, 257, 257, 258, 258, 258, 258, 258, 258, 259, 259, 259, 259, 259, 260, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 262, 262, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 261, 260, 259, 259, 259, 259, 259, 259, 258, 258, 258, 258, 258, 257, 256, 255, 254, 253, 252, 252, 251, 250, 249, 248, 249, 248, 247, 248, 246, 247, 246, 246, 245, 244, 243, 244, 243, 242, 241, 241, 240, 241, 240, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 228, 227, 226, 225, 224, 223, 222, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 210, 209, 208, 207, 206, 205, 204, 203, 202, 201, 200, 199, 198, 197, 197, 196, 195, 194, 193, 192, 191, 190, 189, 188, 187, 186, 185, 184, 183, 182, 181, 180, 179, 178, 177, 176, 175, 174, 173, 172, 171, 170, 169, 168, 167, 166, 165, 164, 163, 162, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 112, 111, 112, 111, 110, 110, 109, 109, 109, 109, 109, 109, 108, 108, 108, 108, 108, 107, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 107, 107, 107, 108, 108, 108, 108, 108, 108, 108, 109, 109, 109, 110, 110, 111, 112, 112, 112, 112, 112, 112, 113, 113, 114, 115, 115, 116, 117, 118, 118, 119, 120, 120, 121, 121, 122, 123, 123, 123, 124, 125, 125, 126, 126, 127, 128, 128, 128, 129, 129, 130, 131, 131, 132, 133, 134, 135, 136, 136, 137, 138, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 183, 184, 185, 186, 187, 187, 188, 188, 189, 189, 190, 190, 190, 190, 190, 190, 190, 190, 190, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 190, 190, 190, 190, 189, 188, 188, 188, 187, 186, 185, 184, 183, 183, 183, 182, 183, 182, 181, 181, 180, 180, 180, 179, 180, 179, 179, 178, 177, 176, 176, 175, 174, 173, 173, 172, 171, 171, 170, 169, 168, 166, 167, 168, 166, 165, 164, 163, 164, 163, 163, 162, 161, 160, 159, 159, 158, 157, 156, 156, 156, 156, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 154, 153, 152, 152, 152, 152, 152, 152, 152, 151, 151, 151, 151, 151, 151, 151, 150, 149, 148, 147, 146, 145, 144, 143, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 116, 115, 114, 113, 112, 110, 111, 112, 110, 109, 108, 108, 107, 108, 107, 106, 106, 105, 105, 105, 104, 105, 104, 103, 102, 101, 100, 99, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 61, 62, 63, 64, 65, 66, 67, 68, 61, 60, 60, 59, 58, 57, 56, 55, 55, 54, 54, 54, 54, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 54, 54, 54, 54, 54, 54, 55, 55, 55, 56, 57, 57, 57, 57, 57, 58, 59, 59, 59, 60, 60, 60, 60, 60, 60, 61, 61, 61, 62, 62, 63, 64, 64, 64, 64, 64, 63, 62, 62, 61, 60, 59, 60, 59, 58, 58, 57, 56, 57, 56, 56, 55, 54, 53, 52, 51, 50, 49, 49, 48, 47, 46, 45, 44, 44, 43, 42, 40, 41, 42, 40, 39, 38, 37, 36, 35, 34, 33, 32, 32, 31, 30, 30, 29, 29, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 29, 30, 31, 32, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 314, 315, 315, 316, 316, 316, 317, 318, 318, 319, 320, 320, 321, 322, 323, 323, 324, 324, 325, 325, 326, 326, 327, 327, 328, 328, 328, 328, 329, 329, 329, 330, 331, 332, 332, 333, 334, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 336, 336, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 335, 334, 333, 333, 332, 332, 332, 332, 332, 332, 332, 332, 332, 332, 332, 332, 331, 330, 330, 330, 329, 329, 329, 328, 328, 328, 328, 328, 328, 328, 328, 328, 328, 329, 329, 328, 328, 328, 328, 328, 328, 328, 328, 327, 326, 326, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 325, 324, 323, 322, 322, 322, 322, 321, 321, 321, 321, 321, 321, 321, 321, 321, 321, 320, 320, 320, 320, 320, 319, 319, 319, 319, 318, 318, 318, 318, 318, 318, 318, 317, 316, 315, 315, 315, 315, 314, 314, 314, 314, 314, 314, 313, 313, 313, 313, 313, 312, 311, 311, 311, 311, 311, 311, 311, 311, 311, 311, 310, 310, 310, 310, 309, 308, 307, 307, 306, 305, 304, 303, 302, 301, 300, 299, 298, 297, 296, 295, 295, 294, 293, 292, 291, 291, 290, 289, 290, 288, 289, 288, 287, 286, 286, 285, 284, 282, 283, 284, 282, 281, 280, 280, 279, 278, 278, 277, 276, 276, 274, 275, 276, 274, 273, 273, 272, 271, 270, 266, 267, 268, 269, 270, 266, 265, 264, 263, 264, 263, 263, 262, 261, 261, 260, 259, 258, 258, 257, 256, 256, 255, 256, 255, 254, 254, 253, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 213};
        int[] cy3 = {21, 21, 22, 23, 23, 23, 24, 24, 24, 24, 24, 25, 25, 26, 27, 28, 29, 30, 31, 31, 32, 33, 34, 35, 35, 36, 37, 38, 38, 39, 40, 41, 42, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 65, 66, 67, 68, 69, 70, 71, 72, 73, 73, 74, 75, 76, 76, 77, 78, 78, 79, 79, 80, 81, 81, 82, 83, 83, 84, 84, 85, 85, 86, 86, 87, 87, 88, 89, 90, 91, 91, 92, 93, 93, 94, 94, 94, 94, 94, 94, 95, 96, 96, 97, 97, 97, 97, 97, 97, 97, 98, 98, 98, 98, 99, 100, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 102, 102, 102, 102, 103, 103, 104, 104, 104, 104, 104, 104, 104, 104, 105, 105, 105, 105, 105, 105, 106, 107, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 109, 109, 109, 109, 110, 110, 111, 111, 111, 111, 111, 111, 112, 112, 112, 113, 113, 114, 115, 116, 116, 116, 116, 116, 117, 117, 118, 118, 119, 119, 120, 120, 121, 122, 122, 123, 124, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 178, 179, 180, 180, 181, 182, 183, 183, 184, 184, 185, 185, 185, 186, 187, 188, 189, 190, 191, 191, 192, 193, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 195, 195, 195, 195, 196, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 198, 198, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 197, 196, 196, 195, 192, 193, 194, 195, 192, 191, 190, 191, 190, 190, 189, 188, 183, 184, 185, 186, 187, 188, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 174, 173, 173, 172, 171, 168, 169, 170, 171, 168, 168, 167, 167, 166, 165, 165, 164, 163, 161, 162, 163, 161, 160, 159, 159, 158, 158, 158, 158, 158, 158, 158, 159, 160, 160, 161, 161, 161, 161, 161, 161, 161, 162, 162, 162, 162, 163, 164, 164, 164, 164, 164, 164, 165, 165, 165, 165, 165, 165, 165, 165, 166, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 168, 168, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 167, 166, 166, 166, 166, 166, 166, 166, 166, 166, 166, 166, 166, 166, 166, 165, 164, 163, 162, 162, 162, 162, 161, 161, 160, 159, 160, 158, 159, 158, 157, 156, 155, 154, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 145, 144, 143, 144, 143, 142, 141, 140, 140, 139, 138, 137, 136, 135, 134, 135, 134, 134, 134, 133, 133, 132, 131, 132, 131, 131, 131, 131, 130, 129, 128, 128, 127, 127, 127, 127, 126, 126, 126, 125, 124, 123, 123, 123, 123, 123, 122, 122, 122, 122, 122, 122, 121, 120, 119, 118, 118, 117, 116, 115, 114, 114, 114, 113, 106, 107, 108, 109, 110, 111, 112, 113, 106, 105, 104, 103, 102, 101, 100, 99, 98, 97, 96, 95, 95, 94, 93, 89, 90, 91, 92, 93, 89, 88, 87, 86, 85, 86, 85, 85, 84, 83, 82, 81, 80, 79, 78, 77, 77, 76, 75, 75, 74, 74, 73, 72, 72, 71, 71, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 69, 68, 67, 67, 67, 67, 67, 67, 66, 66, 66, 66, 66, 66, 65, 65, 64, 63, 63, 63, 63, 63, 62, 61, 60, 59, 58, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 43, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 43, 43, 44, 45, 46, 47, 48, 48, 49, 50, 51, 51, 52, 53, 53, 54, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 175, 176, 176, 177, 178, 178, 179, 180, 180, 181, 182, 183, 183, 184, 185, 185, 186, 187, 187, 188, 189, 189, 190, 190, 190, 191, 192, 192, 193, 194, 194, 195, 196, 196, 197, 198, 198, 199, 200, 200, 201, 202, 202, 202, 202, 203, 203, 204, 204, 204, 204, 204, 204, 204, 204, 204, 204, 204, 205, 205, 205, 205, 205, 205, 206, 206, 207, 208, 208, 208, 208, 208, 208, 209, 210, 211, 212, 213, 214, 215, 215, 216, 217, 218, 219, 220, 221, 222, 222, 223, 224, 225, 226, 227, 228, 228, 228, 228, 229, 230, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 231, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 234, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 235, 234, 234, 234, 234, 234, 234, 234, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 232, 232, 232, 232, 232, 232, 231, 231, 231, 230, 229, 229, 229, 229, 229, 228, 228, 228, 228, 228, 228, 227, 226, 227, 226, 226, 226, 226, 225, 224, 223, 223, 222, 221, 220, 219, 220, 219, 219, 218, 217, 216, 215, 214, 213, 212, 211, 210, 209, 208, 207, 206, 205, 204, 203, 202, 201, 200, 199, 198, 197, 196, 195, 194, 193, 192, 191, 190, 189, 188, 187, 186, 185, 184, 183, 183, 182, 181, 180, 179, 178, 177, 176, 175, 174, 173, 172, 171, 170, 169, 168, 167, 166, 165, 164, 163, 162, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 148, 147, 146, 145, 144, 143, 143, 141, 142, 143, 141, 140, 139, 139, 138, 137, 136, 129, 130, 131, 132, 133, 134, 135, 136, 129, 128, 127, 126, 126, 125, 124, 124, 123, 122, 122, 121, 122, 119, 120, 121, 119, 119, 118, 118, 117, 116, 117, 113, 114, 115, 116, 113, 112, 111, 111, 110, 109, 108, 107, 106, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 96, 95, 94, 93, 92, 93, 92, 91, 91, 85, 86, 87, 88, 89, 90, 91, 85, 84, 83, 82, 81, 81, 80, 79, 79, 79, 78, 77, 78, 75, 76, 77, 75, 74, 73, 72, 71, 72, 71, 70, 70, 68, 69, 70, 68, 67, 66, 67, 66, 66, 65, 64, 62, 63, 64, 58, 59, 60, 61, 62, 58, 57, 56, 57, 56, 55, 54, 53, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 41, 40, 40, 39, 39, 39, 38, 38, 38, 38, 38, 38, 37, 36, 36, 35, 35, 35, 35, 35, 35, 34, 33, 33, 32, 32, 32, 31, 31, 31, 31, 31, 31, 31, 30, 30, 29, 28, 28, 28, 28, 28, 28, 28, 28, 27, 27, 27, 27, 27, 27, 26, 25, 25, 24, 24, 24, 24, 23, 23, 23, 23, 23, 23, 22, 22, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21};

        int[][][] maps = {{cx1,cy1}, {cx2,cy2}, {cx3,cy3}};





        public void drawMiniMap(Graphics g) {
            this.g = g;
            //System.out.println("aaaaaa"+this.mapChoice);
            //System.out.println(cx[0]);
            drawMiniMapp();

            drawPlayer();

            drawNpcs();

        }

        public void drawMiniMapp(){
            g.setColor(Color.DARK_GRAY);
            g.setColor(Color.GREEN);  // Cor do jogador
            g.drawImage(minimapImage, miniMapX+10, miniMapY+10, drawPanel);
            if(mapChoice == 3){
                for(int i=0 ; i<cx.length; i++){
                    g.fillOval((int)cx[i]+20, (int)cy[i]+20, 10, 10 ); 
                }
            }
        }

        public void drawPlayer(){
            int aa = (int)(drawPanel.getPos()*speedAdjust); //esse valor é a regra de 3 da posição final, posição do jogador, e tamanho do vetor
            int playerIndex = Math.floorMod(aa, cx.length);
            //System.out.println(cx.length);
            int posicaoPlayerX = cx[playerIndex];  
            int posicaoPlayerY = cy[playerIndex];

            g.setColor(playerColor);  // Cor do jogador
            g.fillOval(posicaoPlayerX+17, posicaoPlayerY+17, 15, 15);  // Tamanho do ponto do jogador
            
        }

        public void drawNpcs(){
            g.setColor(npcColor);

            for (Npc npc : drawPanel.getNpcs()) {
                int npcPos = npc.getPos(); // Posição ao longo da pista
                int npcIndex = npcPos/1600  % cx.length;
                int posicaoNpcX = cx[npcIndex];  
                int posicaoNpcY = cy[npcIndex];

                g.fillOval(posicaoNpcX+17, posicaoNpcY+17, 13, 13);  // Tamanho do ponto dos NPCs
            }
        }


    }
