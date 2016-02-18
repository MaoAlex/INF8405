package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

//Wrapper pour rendre des objets parcelable
public class HashIntIntWrapper implements Parcelable {

    private int [] keys;
    private int [] values;


    public static final Parcelable.Creator<HashIntIntWrapper> CREATOR = new Parcelable.Creator<HashIntIntWrapper>() {
        @Override
        public HashIntIntWrapper createFromParcel(Parcel source) {
            return new HashIntIntWrapper(source);
        }

        @Override
        public HashIntIntWrapper[] newArray(int size) {
            return new HashIntIntWrapper[size];
        }
    };

    public HashIntIntWrapper(Parcel in) {
        keys = in.createIntArray();
        values = in.createIntArray();
    }

    public void fillMap(Map<Integer, Integer> map) {
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
    }

    public HashIntIntWrapper(Map<Integer, Integer> map) {
        keys = new int[map.size()];
        values = new int[map.size()];

        int comp = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
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
        dest.writeIntArray(values);
    }
}
