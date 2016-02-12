package worktest.filou.flowfreev1;

import java.util.Arrays;

/**
 * Created by filou on 09/02/16.
 */
public class Tube {
    private int color;
    private boolean isComplete = false;
    private TubePart[] tubeParts;
    private Position[] posDelimiters;
    private int indexCurrentDelimiter;

    public Tube(int org_i0, int org_j0,int org_i1, int org_j1, int color) {
        this.color = color;
        posDelimiters = new Position[2];
        posDelimiters[0] = new Position(org_i0, org_j0);
        posDelimiters[1] = new Position(org_i1, org_j1);
        tubeParts = new TubePart[2];
        tubeParts[0] = new TubePart(org_i0, org_j0, color, null, this);
        tubeParts[1] = new TubePart(org_i1, org_j1, color, null, this);
        indexCurrentDelimiter = -1;
    }

    public void chooseADelimiter(int i, int j) {
        for (int k=0; k < 2; k++) {
            if (posDelimiters[k].getI() == i && posDelimiters[k].getJ() == j) {
                indexCurrentDelimiter = k;
                break;
            }
        }
    }

    public Position[] getPosDelimiters() {
        return posDelimiters;
    }

    public void resetDelimiter() {
        indexCurrentDelimiter = -1;
    }

    public boolean hasStart() {
        return indexCurrentDelimiter != -1;
    }

    public Position getEnd() {
        return posDelimiters[(indexCurrentDelimiter + 1) % 2];
    }

    public Position getStart() {
        return posDelimiters[indexCurrentDelimiter];
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public int getColor() {
        return color;
    }

    public TubePart generateNext(TubePart tubePart, int i, int j) {
        TubePart next = new TubePart( i, j, color, tubePart, this);
        tubePart.setNext(next);

        return next;
    }

    public void removeTubePart(TubePart tubePart) {
        TubePart currentp = tubePart.getPrevious();
        TubePart currentn = tubePart.getNext();
        currentp.setNext(null);
        currentn.setPrevious(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tube) {
            Tube tube = (Tube)o;
            boolean eq = Arrays.equals(tube.getPosDelimiters(), posDelimiters);
            eq = eq && tube.getColor() == color;
            return eq;
        } else {
            return false;
        }
    }
}
