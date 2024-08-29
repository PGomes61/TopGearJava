public class Npc extends Carro{

    private int pos = 0;
    private double xTela = 500;
    private double yTela = 500;
    private double zTela;


    public Npc(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade, double xTela, double Ytela){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
        this.xTela = xTela;
        this.yTela = yTela;
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

    public double[] project(double camX, int camY, int camZ, double camD, int width, int height, int roadW) {
        double scale = camD / (zTela - camZ);
        double screenX = (1 + scale * (xTela - camX)) * width / 2;
        double screenY = (1 - scale * (yTela - camY)) * height / 2;
        return new double[]{screenX, screenY, scale};
    }
}