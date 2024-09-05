public class Curva {
    public void addCurva(Line line, int value, int valueStart, int valueEnd, double curve, int flagTurn, int tamPista, int count) {
        if(value > valueStart + tamPista * count && value < valueEnd + tamPista * count) {
            line.setCurve(curve);
            line.setFlagTurn(flagTurn);
        }
    }
}
