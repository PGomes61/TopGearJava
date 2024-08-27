public class Npc extends Carro{

    private int pos = 0;
    private int xTela;
    private int yTela;


    public Npc(String path1, String path2, String path3, double aceleracao, double peso, double tracao, double velocidade){
        super(path1, path2, path3, aceleracao, peso, tracao, velocidade);
    }

    public int getPos(){
        return pos;
    }  

    public int getX(){
        return xTela;
    }

    public int getY(){
        return yTela;
    }

    public void setPos(int pos){
        this.pos = pos;
    }    
}