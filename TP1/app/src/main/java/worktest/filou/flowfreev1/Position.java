package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

//Englobe une position (i,j)
public class Position implements Parcelable {
    int i, j;

    public static final Parcelable.Creator<Position> CREATOR = new Parcelable.Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel source) {
            return new Position(source);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Position(Parcel in) {
        i = in.readInt();
        j = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(i);
        dest.writeInt(j);
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
