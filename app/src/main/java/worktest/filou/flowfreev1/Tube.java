package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by filou on 09/02/16.
 */
public class Tube implements Parcelable {
    private int color;
    private boolean isComplete = false;
    private Position[] posDelimiters;
    private ArrayList<TubePart>[] currentPath;
    private int indexCurrentDelimiter = -1;

    public static final Parcelable.Creator<Tube> CREATOR = new Parcelable.Creator<Tube>() {
        @Override
        public Tube createFromParcel(Parcel source) {
            return new Tube(source);
        }

        @Override
        public Tube[] newArray(int size) {
            return new Tube[size];
        }
    };

    public Tube(int org_i0, int org_j0,int org_i1, int org_j1, int color) {
        this.color = color;
        posDelimiters = new Position[2];
        posDelimiters[0] = new Position(org_i0, org_j0);
        posDelimiters[1] = new Position(org_i1, org_j1);
        currentPath = new ArrayList[2];
        for (int i = 0; i < currentPath.length; i++)
            currentPath[i] = new ArrayList<>();
    }

    public void reset() {
        indexCurrentDelimiter = -1;
        isComplete = false;
        currentPath =  new ArrayList[2];
        for (int i = 0; i < currentPath.length; i++)
            currentPath[i] = new ArrayList<>();
    }

    public Tube(Parcel in) {
        color = in.readInt();
        isComplete = in.readInt() > 0 ? true : false;
        posDelimiters = in.createTypedArray(Position.CREATOR);
        currentPath = new ArrayList[2];
        in.readTypedList(currentPath[0], TubePart.CREATOR);
        in.readTypedList(currentPath[1], TubePart.CREATOR);
        indexCurrentDelimiter = in.readInt();
    }

    public void chooseADelimiter(int i, int j) {
        for (int k=0; k < 2; k++) {
            if (posDelimiters[k].getI() == i && posDelimiters[k].getJ() == j) {
                indexCurrentDelimiter = k;
                break;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(color);
        dest.writeInt(isComplete? 1 : -1);
        dest.writeTypedArray(posDelimiters, 0);
        dest.writeTypedList(currentPath[0]);
        dest.writeTypedList(currentPath[1]);
        dest.writeInt(indexCurrentDelimiter);
    }

    public TubePart getTubePart(int index) {
        return currentPath[indexCurrentDelimiter].get(index);
    }

    public int getLastIndex() {
        return currentPath[indexCurrentDelimiter].size() - 1;
    }

    public void addTubePart(TubePart tubePart) {
        currentPath[indexCurrentDelimiter].add(tubePart);
    }
    public boolean isTail(int i, int j) {
        TubePart tubePart = currentPath[indexCurrentDelimiter].get(currentPath[indexCurrentDelimiter].size() - 1);
        return i == tubePart.getI() && j == tubePart.getJ();
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

    public TubePart generateNext(int i, int j) {
        int lastIndex  = currentPath[indexCurrentDelimiter].size() - 1;
        TubePart next = new TubePart( i, j, color);

        return next;
    }

    public void undo() {
        if (indexCurrentDelimiter == -1)
            return;

        currentPath[indexCurrentDelimiter].remove(getLastIndex());

        if (getLastIndex() < 0 )
            return;

        currentPath[indexCurrentDelimiter].get(getLastIndex()).setGraphicalTubNext(null);
    }

    public boolean isEmpty() {
        return currentPath[indexCurrentDelimiter].isEmpty();
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
