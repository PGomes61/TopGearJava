public class Npc extends Carro{

    private int pos = 0;
    private double xTela;
    private double yTela;


    public Npc(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
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

    public void setPos(int pos){
        this.pos = pos;
    }    

    public void setX(double x){
        this.xTela = x;
    }

    public double getPosicaoRelativaNaPista(){
        return 1;
    }
}