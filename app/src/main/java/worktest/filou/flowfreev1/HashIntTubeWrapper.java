package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

//Wrapper pour rendre des objets parcelable
public class HashIntTubeWrapper implements Parcelable {
    private int [] keys;
    private Tube [] values;


    public static final Parcelable.Creator<HashIntTubeWrapper> CREATOR = new Parcelable.Creator<HashIntTubeWrapper>() {
        @Override
        public HashIntTubeWrapper createFromParcel(Parcel source) {
            return new HashIntTubeWrapper(source);
        }

        @Override
        public HashIntTubeWrapper[] newArray(int size) {
            return new HashIntTubeWrapper[size];
        }
    };

    public HashIntTubeWrapper(Parcel in) {
        keys = in.createIntArray();
        values = in.createTypedArray(Tube.CREATOR);
    }

    public void fillMap(Map<Integer, Tube> map) {
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
    }

    public HashIntTubeWrapper(Map<Integer, Tube> map) {
        keys = new int[map.size()];
        values = new Tube[map.size()];

        int comp = 0;
        for (Map.Entry<Integer, Tube> entry : map.entrySet()) {
            keys[comp] = entry.getKey();
            values[comp++] = entry.getValue();
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(keys);
        dest.writeTypedArray(values, 0);
    }
}
