package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


//Contient des liste de niveaux classé par taille
public class LevelsBySize implements Parcelable {
    public static final Parcelable.Creator<LevelsBySize> CREATOR = new Parcelable.Creator<LevelsBySize>() {
        @Override
        public LevelsBySize createFromParcel(Parcel source) {
            return new LevelsBySize(source);
        }

        @Override
        public LevelsBySize[] newArray(int size) {
            return new LevelsBySize[size];
        }
    };

    private ArrayList<Levels> lisOfLevels = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(lisOfLevels);
    }

    public void addLevels(Levels levels) {
        lisOfLevels.add(levels);
    }

    public LevelsBySize() {
    }
    public LevelsBySize(Parcel in) {
        in.readTypedList(lisOfLevels, Levels.CREATOR);
    }

    public Levels getLevels(int index) {
        return lisOfLevels.get(index);
    }
    public Levels getLastElement() {return lisOfLevels.get(lisOfLevels.size()-1);}
    public List<Levels> getList() {
        return lisOfLevels;
    }
}
