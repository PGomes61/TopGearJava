import java.util.Random;
public class Npc extends Carro{

    private Random random = new Random();
    private int pos = 0;
    private double offset = 0;
    private double xTela = 500;
    private double yTela = 500;
    private double zTela;
    private int width, height;

    public Npc(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
        this.xTela = xTela;
        this.yTela = yTela;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getPos(){
        return pos;
    }  

    public double getX(){
        return xTela;
    }

    public double getY(){
        return yTela;
    }

    public double getZ(){
        return zTela;
    }

    public void setPos(int pos){
        this.pos = pos;
    }    

    public void setX(double x){
        this.xTela = x;
    }

    public void setY(double y){
        this.yTela = y;
    }

    public double getPosicaoRelativaNaPista(){
        return 1;
    }

        public void npcOffset() {
        // Define um valor muito pequeno para controlar o incremento ou decremento
        double aux = 0.001 * (random.nextDouble() - 0.5);  // Varia entre -0.0005 e 0.0005
    
        // Verifica se a soma do offset atual com aux está dentro do intervalo permitido
        offset += aux;
        
        // Limita o valor de offset para permanecer no intervalo de -1 a 1
        if (offset > 1) {
            offset = 1;
        } else if (offset < -1) {
            offset = -1;
        }
    }

    public double getOffset(){
        return this.offset;
    }

    public void setOffset(double offset){
        this.offset = offset;
    }

    public double[] project(double camX, int camY, int camZ, double camD, int width, int height, int roadW) {
        double scale = camD / (zTela - camZ);
        double screenX = (1 + scale * (xTela - camX)) * width / 2;
        double screenY = (1 - scale * (yTela - camY)) * height / 2;
        return new double[]{screenX, screenY, scale};
    }
}