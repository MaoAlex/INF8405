package worktest.filou.flowfreev1;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filou on 05/02/16.
 */
public class Delimiter implements Parcelable {
    public static final Parcelable.Creator<Delimiter> CREATOR = new Parcelable.Creator<Delimiter>() {

        @Override
        public Delimiter createFromParcel(Parcel source) {
            return new Delimiter(source);
        }

        @Override
        public Delimiter[] newArray(int size) {
            return new Delimiter[size];
        }
    };

    private int i;
    private int j;
    private String color;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(i);
        dest.writeInt(j);
        dest.writeString(color);
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getColor() {
        return color;
    }

    public int getColorCode () {
        return Color.parseColor(color);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Delimiter(String color, int i, int j) {
        this.color = color;
        this.j = j;
        this.i = i;
    }

    public Delimiter(Parcel in) {
        i = in.readInt();
        j = in.readInt();
        color = in.readString();
    }
}
