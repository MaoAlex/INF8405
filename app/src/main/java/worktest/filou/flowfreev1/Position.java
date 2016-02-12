package worktest.filou.flowfreev1;

/**
 * Created by filou on 10/02/16.
 */
public class Position {
    int i, j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position position = (Position)o;
            return position.getI() == i && position.getJ() == j;
        }

        return false;
    }
}
