package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filou on 07/02/16.
 */
public class GridElement extends AbsGridElement {

    @Override
    public boolean isColored() {
        return !colorsInOrder.isEmpty();
    }

    public GridElement(int i, int j, float centerX, float centerY) {
        super(i, j, centerX, centerY);
    }

    public GridElement(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<GridElement> CREATOR = new Parcelable.Creator<GridElement>() {
        @Override
        public GridElement createFromParcel(Parcel source) {
            return new GridElement(source);
        }

        @Override
        public GridElement[] newArray(int size) {
            return new GridElement[size];
        }
    };

    @Override
    public int getColor() {
        return colorsInOrder.getFirst();
    }
}
