package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filou on 12/02/16.
 */
public class GridWrapper implements Parcelable {
    private AbsGridElement[] flatGrid;
    private int wLevel, hLevel;

    public static final Parcelable.Creator<GridWrapper> CREATOR = new Parcelable.Creator<GridWrapper>() {
        @Override
        public GridWrapper createFromParcel(Parcel source) {
            return new GridWrapper(source);
        }

        @Override
        public GridWrapper[] newArray(int size) {
            return new GridWrapper[size];
        }
    };

    public GridWrapper(int wLevel, int hLevel, AbsGridElement[][] grid) {
        this.wLevel = wLevel;
        this.hLevel = hLevel;
        flatGrid = new AbsGridElement[wLevel * hLevel];

        for (int i = 0; i < wLevel; i++) {
            for (int j = 0; j < hLevel; j++) {
                flatGrid[i*wLevel + j ] = grid[i][j];
            }

        }
    }

    public GridWrapper(Parcel in) {
        wLevel = in.readInt();
        hLevel = in.readInt();
        flatGrid = new AbsGridElement[wLevel * hLevel];
        flatGrid = (AbsGridElement[]) in.readParcelableArray(AbsGridElement.class.getClassLoader());
    }

    public AbsGridElement[][] getGrid() {
        AbsGridElement[][] grid = new AbsGridElement[wLevel][hLevel];
        for (int i = 0; i < wLevel; i++) {
            for (int j = 0; j < hLevel; j++) {
                grid[i][j] = flatGrid[i*wLevel + j ];
            }
        }

        return grid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(flatGrid, 0);
    }
}
