package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

//Wrapper pour rendre des objets parcelable
public class IntListWrapper implements Parcelable {

    private int[] colors;
    private LinkedList<Integer> list;

    public static final Parcelable.Creator<IntListWrapper> CREATOR = new Parcelable.Creator<IntListWrapper>() {
        @Override
        public IntListWrapper createFromParcel(Parcel source) {
            return new IntListWrapper(source);
        }

        @Override
        public IntListWrapper[] newArray(int size) {
            return new IntListWrapper[size];
        }
    };

    public IntListWrapper(LinkedList<Integer> colorsList) {
        list = colorsList;
        colors = new int[colorsList.size()];
        int compteur = 0;
        for (Integer color: colorsList
             ) {
            colors[compteur++] = color;
        }
    }

    public IntListWrapper(Parcel in) {
        colors = in.createIntArray();
        list = new LinkedList<>();
        for (int color: colors
             ) {
            list.add(color);
        }
    }

    public LinkedList<Integer> getColorList() {
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(colors);
    }
}
