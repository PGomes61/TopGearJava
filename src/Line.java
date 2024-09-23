public class Line {
    private double camD = 0.84; // Camera Depth
    private int height = 768;
    private int roadW = 2800;
    private int width = 1024;

    double x, y, z;   // 3D center of line
    double X, Y, W;   // Screen coordinates
    double scale, curve;
    //double elevation;
    int flagTurn;

    public Line() {
        this.curve = x = y = z = 0;
        this.flagTurn = 0;
        //this.elevation = 0;
    }

    public void project(double camX, int camY, int camZ) {
        scale = camD / (z - camZ);
        X = (1 + scale * (x - camX)) * width / 2;
        Y = (1 - scale * (y - camY /*+ elevation*/)) * height / 2;
        W = scale * roadW * width/2;
    }
}