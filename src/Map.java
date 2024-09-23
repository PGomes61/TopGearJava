    import java.awt.*;
    import java.io.IOException;
    import java.io.File;
import java.security.spec.MGF1ParameterSpec;
import javax.swing.ImageIcon;


    public class Map{
        private DrawPanel drawPanel;
        private Graphics g;

        private static Color npcColor = Color.RED;
        private static Color playerColor = Color.BLUE;


        private int miniMapWidth = 300;
        private int miniMapHeight = 200;
        private int miniMapX = 10;  // Posição no canto superior esquerdo
        private int miniMapY = 10;  // Posição no canto superior esquerdo

        private int posFinal1 = 6412200/4;

        private Image minimapImage;  // Variável para armazenar a imagem do minimapa

        private int speedAdjust = 1600;

        public Map(DrawPanel drawPanel) {
            this.drawPanel = drawPanel;
            ImageIcon icon = new ImageIcon("/home/luan/projetos/TopGearJava/src/m1.png");
            this.minimapImage = icon.getImage().getScaledInstance(miniMapWidth, miniMapHeight, Image.SCALE_SMOOTH);
        }

        int[] cx = {149, 148, 147, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100, 99, 98, 97, 96, 95, 94, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 49, 49, 49, 48, 48, 47, 47, 46, 45, 45, 44, 43, 42, 42, 41, 41, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 41, 41, 42, 42, 43, 44, 44, 44, 44, 44, 44, 45, 45, 45, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 45, 45, 45, 44, 44, 44, 43, 43, 42, 42, 42, 41, 41, 40, 40, 39, 39, 38, 38, 37, 37, 36, 36, 35, 35, 34, 34, 34, 33, 33, 33, 33, 32, 31, 30, 29, 29, 28, 27, 26, 25, 25, 24, 23, 23, 23, 23, 23, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 23, 24, 25, 26, 27, 28, 28, 29, 30, 31, 32, 33, 34, 33, 35, 34, 35, 36, 36, 37, 36, 37, 37, 38, 38, 38, 39, 40, 39, 41, 40, 41, 42, 42, 43, 42, 45, 44, 43, 47, 46, 45, 47, 48, 48, 49, 49, 50, 50, 50, 53, 52, 51, 50, 56, 55, 54, 53, 56, 57, 57, 58, 57, 58, 58, 59, 59, 59, 59, 60, 60, 60, 60, 60, 61, 60, 61, 61, 61, 62, 62, 62, 63, 63, 63, 63, 63, 64, 64, 65, 66, 66, 67, 67, 67, 67, 67, 67, 67, 68, 68, 69, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 71, 71, 71, 71, 71, 71, 72, 73, 74, 74, 75, 76, 77, 78, 77, 80, 79, 78, 80, 81, 81, 82, 81, 82, 82, 82, 83, 83, 84, 85, 84, 86, 85, 86, 87, 87, 87, 92, 91, 90, 89, 88, 87, 98, 97, 96, 95, 94, 93, 92, 98, 99, 100, 101, 102, 103, 103, 104, 104, 105, 106, 106, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 128, 129, 129, 130, 130, 131, 131, 132, 131, 132, 132, 132, 133, 134, 133, 134, 135, 136, 136, 137, 138, 139, 139, 140, 141, 142, 141, 143, 142, 143, 144, 145, 146, 146, 147, 148, 149, 150, 149, 151, 150, 151, 152, 152, 152, 153, 152, 154, 153, 154, 155, 155, 156, 157, 159, 158, 157, 164, 163, 162, 161, 160, 159, 168, 167, 166, 165, 164, 168, 169, 170, 170, 171, 171, 172, 173, 174, 175, 180, 179, 178, 177, 176, 175, 184, 183, 182, 181, 180, 184, 184, 184, 185, 186, 185, 186, 187, 188, 188, 189, 190, 191, 191, 191, 192, 192, 193, 193, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 193, 192, 191, 190, 189, 188, 187, 187, 186, 185, 184, 183, 182, 181, 180, 179, 179, 178, 178, 178, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 178, 179, 180, 180, 181, 182, 182, 183, 184, 185, 186, 187, 188, 189, 196, 195, 194, 193, 192, 191, 190, 189, 196, 197, 198, 199, 199, 200, 201, 202, 201, 203, 202, 204, 203, 204, 205, 205, 206, 205, 206, 206, 207, 207, 208, 208, 209, 229, 228, 227, 226, 225, 224, 223, 222, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 210, 209, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 259, 258, 257, 256, 255, 254, 253, 252, 251, 250, 259, 260, 261, 261, 262, 262, 262, 263, 262, 263, 263, 264, 265, 264, 266, 265, 266, 267, 268, 268, 269, 269, 270, 270, 270, 270, 270, 270, 270, 271, 271, 271, 271, 271, 272, 272, 273, 273, 273, 273, 273, 273, 273, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 272, 272, 272, 271, 271, 271, 271, 270, 270, 270, 270, 270, 270, 269, 269, 269, 269, 268, 268, 268, 267, 267, 267, 267, 267, 267, 267, 266, 265, 265, 264, 263, 263, 262, 262, 261, 261, 260, 260, 260, 260, 259, 259, 258, 258, 257, 257, 256, 256, 255, 254, 253, 252, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 228, 227, 226, 226, 225, 224, 224, 224, 223, 223, 223, 222, 221, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 211, 210, 209, 208, 207, 206, 205, 204, 203, 202, 201, 201, 200, 199, 199, 198, 198, 197, 197, 197, 196, 196, 195, 194, 194, 193, 192, 192, 191, 191, 190, 190, 189, 189, 188, 188, 187, 187, 187, 187, 186, 185, 185, 184, 183, 183, 182, 181, 180, 180, 179, 178, 178, 177, 176, 175, 175, 175, 174, 174, 173, 173, 173, 172, 171, 170, 169, 168, 167, 166, 165, 164, 163, 162, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150};
        int[] cy = {170, 170, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 169, 168, 168, 167, 166, 165, 165, 164, 163, 162, 161, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 149, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 137, 136, 135, 135, 135, 134, 134, 133, 133, 132, 131, 131, 131, 131, 131, 131, 130, 130, 130, 129, 129, 129, 129, 128, 128, 128, 127, 126, 125, 124, 124, 123, 122, 121, 120, 119, 118, 117, 117, 116, 115, 114, 113, 112, 111, 111, 111, 111, 110, 110, 110, 109, 109, 109, 109, 109, 109, 109, 109, 108, 108, 108, 108, 108, 108, 108, 107, 107, 106, 106, 105, 105, 105, 105, 105, 105, 104, 104, 104, 104, 104, 104, 104, 103, 102, 101, 100, 100, 99, 98, 97, 96, 96, 95, 94, 93, 93, 92, 92, 91, 91, 91, 91, 90, 89, 88, 87, 86, 86, 85, 84, 83, 82, 81, 80, 79, 79, 78, 77, 76, 75, 74, 74, 73, 72, 71, 70, 69, 69, 68, 67, 66, 65, 65, 65, 65, 65, 65, 65, 64, 64, 64, 64, 64, 64, 63, 63, 63, 62, 62, 62, 62, 62, 62, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 60, 60, 59, 59, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 57, 57, 57, 57, 57, 57, 56, 56, 56, 56, 55, 55, 55, 54, 54, 54, 54, 54, 54, 54, 53, 53, 53, 52, 52, 52, 51, 51, 50, 50, 49, 48, 48, 47, 47, 46, 45, 45, 44, 44, 43, 43, 42, 41, 41, 40, 39, 38, 37, 36, 36, 35, 34, 33, 32, 31, 31, 30, 29, 29, 29, 28, 28, 27, 27, 26, 26, 25, 24, 23, 23, 22, 21, 20, 20, 20, 20, 20, 20, 20, 19, 19, 19, 19, 19, 19, 19, 18, 18, 17, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 15, 15, 15, 15, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 23, 23, 23, 23, 23, 22, 21, 20, 20, 19, 18, 17, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 24, 25, 26, 27, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 52, 53, 54, 55, 56, 57, 58, 59, 59, 60, 61, 62, 63, 64, 65, 66, 67, 67, 68, 69, 70, 71, 72, 73, 79, 78, 77, 76, 75, 74, 73, 83, 82, 81, 80, 79, 83, 84, 85, 86, 86, 87, 88, 88, 88, 88, 88, 88, 88, 88, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 88, 88, 88, 88, 87, 86, 86, 86, 86, 86, 86, 86, 86, 85, 85, 85, 85, 85, 85, 84, 84, 83, 83, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 83, 84, 84, 85, 85, 85, 85, 85, 85, 85, 86, 86, 86, 86, 86, 86, 87, 88, 88, 89, 89, 90, 91, 90, 92, 91, 92, 92, 93, 93, 93, 93, 94, 95, 95, 96, 96, 97, 98, 99, 100, 101, 102, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 112, 113, 114, 115, 116, 117, 118, 119, 120, 120, 120, 121, 120, 121, 121, 122, 122, 122, 123, 123, 123, 123, 123, 123, 124, 123, 124, 124, 125, 125, 125, 125, 126, 126, 126, 126, 126, 127, 126, 127, 127, 127, 128, 129, 129, 130, 131, 131, 132, 132, 132, 132, 132, 132, 132, 132, 133, 133, 133, 133, 133, 133, 134, 134, 134, 135, 135, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 137, 137, 137, 137, 138, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 158, 159, 160, 160, 160, 160, 160, 160, 160, 161, 161, 161, 161, 161, 161, 161, 161, 161, 161, 162, 162, 163, 163, 163, 163, 163, 163, 163, 163, 163, 164, 164, 164, 164, 164, 164, 164, 165, 165, 166, 167, 167, 168, 169, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170};


        int[] cx2 = {120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100, 99, 98, 97, 96, 95, 94, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 49, 49, 49, 48, 48, 47, 47, 46, 45, 45, 44, 43, 42, 42, 41, 41, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 41, 41, 42, 42, 43, 44, 44, 44, 44, 44, 44, 45, 45, 45, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 45, 45, 45, 44, 44, 44, 43, 43, 42, 42, 42, 41, 41, 40, 40, 39, 39, 38, 38, 37, 37, 36, 36, 35, 35, 34, 34, 34, 33, 33, 33, 33, 32, 31, 30, 29, 29, 28, 27, 26, 25, 25, 24, 23, 23, 23, 23, 23, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 23, 24, 25, 26, 27, 28, 28, 29, 30, 31, 32, 33, 34, 33, 35, 34, 35, 36, 36, 37, 36, 37, 37, 38, 38, 38, 39, 40, 39, 41, 40, 41, 42, 42, 43, 42, 45, 44, 43, 47, 46, 45, 47, 48, 48, 49, 49, 50, 50, 50, 53, 52, 51, 50, 56, 55, 54, 53, 56, 57, 57, 58, 57, 58, 58, 59, 59, 59, 59, 60, 60, 60, 60, 60, 61, 60, 61, 61, 61, 62, 62, 62, 63, 63, 63, 63, 63, 64, 64, 65, 66, 66, 67, 67, 67, 67, 67, 67, 67, 68, 68, 69, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 71, 71, 71, 71, 71, 71, 72, 73, 74, 74, 75, 76, 77, 78, 77, 80, 79, 78, 80, 81, 81, 82, 81, 82, 82, 82, 83, 83, 84, 85, 84, 86, 85, 86, 87, 87, 87, 92, 91, 90, 89, 88, 87, 98, 97, 96, 95, 94, 93, 92, 98, 99, 100, 101, 102, 103, 103, 104, 104, 105, 106, 106, 128, 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 128, 129, 129, 130, 130, 131, 131, 132, 131, 132, 132, 132, 133, 134, 133, 134, 135, 136, 136, 137, 138, 139, 139, 140, 141, 142, 141, 143, 142, 143, 144, 145, 146, 146, 147, 148, 149, 150, 149, 151, 150, 151, 152, 152, 152, 153, 152, 154, 153, 154, 155, 155, 156, 157, 159, 158, 157, 164, 163, 162, 161, 160, 159, 168, 167, 166, 165, 164, 168, 169, 170, 170, 171, 171, 172, 173, 174, 175, 180, 179, 178, 177, 176, 175, 184, 183, 182, 181, 180, 184, 184, 184, 185, 186, 185, 186, 187, 188, 188, 189, 190, 191, 191, 191, 192, 192, 193, 193, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 194, 193, 192, 191, 190, 189, 188, 187, 187, 186, 185, 184, 183, 182, 181, 180, 179, 179, 178, 178, 178, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 177, 178, 179, 180, 180, 181, 182, 182, 183, 184, 185, 186, 187, 188, 189, 196, 195, 194, 193, 192, 191, 190, 189, 196, 197, 198, 199, 199, 200, 201, 202, 201, 203, 202, 204, 203, 204, 205, 205, 206, 205, 206, 206, 207, 207, 208, 208, 209, 229, 228, 227, 226, 225, 224, 223, 222, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 210, 209, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 259, 258, 257, 256, 255, 254, 253, 252, 251, 250, 259, 260, 261, 261, 262, 262, 262, 263, 262, 263, 263, 264, 265, 264, 266, 265, 266, 267, 268, 268, 269, 269, 270, 270, 270, 270, 270, 270, 270, 271, 271, 271, 271, 271, 272, 272, 273, 273, 273, 273, 273, 273, 273, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 274, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 273, 272, 272, 272, 271, 271, 271, 271, 270, 270, 270, 270, 270, 270, 269, 269, 269, 269, 268, 268, 268, 267, 267, 267, 267, 267, 267, 267, 266, 265, 265, 264, 263, 263, 262, 262, 261, 261, 260, 260, 260, 260, 259, 259, 258, 258, 257, 257, 256, 256, 255, 254, 253, 252, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239, 239, 238, 237, 236, 235, 234, 233, 232, 231, 230, 229, 228, 227, 226, 226, 225, 224, 224, 224, 223, 223, 223, 222, 221, 221, 220, 219, 218, 217, 216, 215, 214, 213, 212, 211, 211, 210, 209, 208, 207, 206, 205, 204, 203, 202, 201, 201, 200, 199, 199, 198, 198, 197, 197, 197, 196, 196, 195, 194, 194, 193, 192, 192, 191, 191, 190, 190, 189, 189, 188, 188, 187, 187, 187, 187, 186, 185, 185, 184, 183, 183, 182, 181, 180, 180, 179, 178, 178, 177, 176, 175, 175, 175, 174, 174, 173, 173, 173, 172, 171, 170, 169, 168, 167, 166, 165, 164, 163, 162, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 148, 147, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 121};
        int[] cy2 = {171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 169, 168, 168, 167, 166, 165, 165, 164, 163, 162, 161, 161, 160, 159, 158, 157, 156, 155, 154, 153, 152, 151, 150, 149, 149, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 137, 136, 135, 135, 135, 134, 134, 133, 133, 132, 131, 131, 131, 131, 131, 131, 130, 130, 130, 129, 129, 129, 129, 128, 128, 128, 127, 126, 125, 124, 124, 123, 122, 121, 120, 119, 118, 117, 117, 116, 115, 114, 113, 112, 111, 111, 111, 111, 110, 110, 110, 109, 109, 109, 109, 109, 109, 109, 109, 108, 108, 108, 108, 108, 108, 108, 107, 107, 106, 106, 105, 105, 105, 105, 105, 105, 104, 104, 104, 104, 104, 104, 104, 103, 102, 101, 100, 100, 99, 98, 97, 96, 96, 95, 94, 93, 93, 92, 92, 91, 91, 91, 91, 90, 89, 88, 87, 86, 86, 85, 84, 83, 82, 81, 80, 79, 79, 78, 77, 76, 75, 74, 74, 73, 72, 71, 70, 69, 69, 68, 67, 66, 65, 65, 65, 65, 65, 65, 65, 64, 64, 64, 64, 64, 64, 63, 63, 63, 62, 62, 62, 62, 62, 62, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 60, 60, 59, 59, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 57, 57, 57, 57, 57, 57, 56, 56, 56, 56, 55, 55, 55, 54, 54, 54, 54, 54, 54, 54, 53, 53, 53, 52, 52, 52, 51, 51, 50, 50, 49, 48, 48, 47, 47, 46, 45, 45, 44, 44, 43, 43, 42, 41, 41, 40, 39, 38, 37, 36, 36, 35, 34, 33, 32, 31, 31, 30, 29, 29, 29, 28, 28, 27, 27, 26, 26, 25, 24, 23, 23, 22, 21, 20, 20, 20, 20, 20, 20, 20, 19, 19, 19, 19, 19, 19, 19, 18, 18, 17, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 15, 15, 15, 15, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 23, 23, 23, 23, 23, 22, 21, 20, 20, 19, 18, 17, 17, 17, 17, 17, 17, 16, 16, 16, 16, 16, 16, 16, 16, 15, 15, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 24, 25, 26, 27, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 52, 53, 54, 55, 56, 57, 58, 59, 59, 60, 61, 62, 63, 64, 65, 66, 67, 67, 68, 69, 70, 71, 72, 73, 79, 78, 77, 76, 75, 74, 73, 83, 82, 81, 80, 79, 83, 84, 85, 86, 86, 87, 88, 88, 88, 88, 88, 88, 88, 88, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 88, 88, 88, 88, 87, 86, 86, 86, 86, 86, 86, 86, 86, 85, 85, 85, 85, 85, 85, 84, 84, 83, 83, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 83, 84, 84, 85, 85, 85, 85, 85, 85, 85, 86, 86, 86, 86, 86, 86, 87, 88, 88, 89, 89, 90, 91, 90, 92, 91, 92, 92, 93, 93, 93, 93, 94, 95, 95, 96, 96, 97, 98, 99, 100, 101, 102, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 112, 113, 114, 115, 116, 117, 118, 119, 120, 120, 120, 121, 120, 121, 121, 122, 122, 122, 123, 123, 123, 123, 123, 123, 124, 123, 124, 124, 125, 125, 125, 125, 126, 126, 126, 126, 126, 127, 126, 127, 127, 127, 128, 129, 129, 130, 131, 131, 132, 132, 132, 132, 132, 132, 132, 132, 133, 133, 133, 133, 133, 133, 134, 134, 134, 135, 135, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 137, 137, 137, 137, 138, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 158, 159, 160, 160, 160, 160, 160, 160, 160, 161, 161, 161, 161, 161, 161, 161, 161, 161, 161, 162, 162, 163, 163, 163, 163, 163, 163, 163, 163, 163, 164, 164, 164, 164, 164, 164, 164, 165, 165, 166, 167, 167, 168, 169, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 170, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171, 171};

    
        public void drawMiniMap(Graphics g) {
            this.g = g;
            
            drawMiniMapp();

            drawPlayer();

            drawNpcs();

        }

        public void drawMiniMapp(){
            g.setColor(Color.DARK_GRAY);
            g.setColor(Color.GREEN);  // Cor do jogador
            g.drawImage(minimapImage, miniMapX+10, miniMapY+10, drawPanel);
            //for(int i=0 ; i<cx.length; i++){
            //    g.fillOval((int)cx[i]+20, (int)cy[i]+20, 10, 10 ); 
            //}
        }

        public void drawPlayer(){
            int aa = (int)(drawPanel.getPos()*0.0006269299); //esse valor é a regra de 3 da posição final, posição do jogador, e tamanho do vetor
            int playerIndex = Math.floorMod(aa, cx.length);
            System.out.println("..."+playerIndex);
            int posicaoPlayerX = cx[playerIndex];  
            int posicaoPlayerY = cy[playerIndex];

            g.setColor(playerColor);  // Cor do jogador
            g.fillOval(posicaoPlayerX+17, posicaoPlayerY+17, 15, 15);  // Tamanho do ponto do jogador
            
        }

        public void drawNpcs(){
            g.setColor(npcColor);

            for (Npc npc : drawPanel.getNpcs()) {
                int npcPos = npc.getPos(); // Posição ao longo da pista
                int npcIndex = npcPos/speedAdjust  % cx.length;
                int posicaoNpcX = cx[npcIndex   ];  
                int posicaoNpcY = cy[npcIndex];

                g.fillOval(posicaoNpcX+17, posicaoNpcY+17, 13, 13);  // Tamanho do ponto dos NPCs
            }
        }

    }
