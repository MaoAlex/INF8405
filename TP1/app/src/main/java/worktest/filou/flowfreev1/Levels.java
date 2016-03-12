package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//liste de niveaux
public class Levels implements Parcelable {
    public static final Parcelable.Creator<Levels> CREATOR = new Parcelable.Creator<Levels>() {
        @Override
        public Levels createFromParcel(Parcel source) {
            return new Levels(source);
        }

        @Override
        public Levels[] newArray(int size) {
            return new Levels[size];
        }
    };
    private ArrayList<Level> levels = new ArrayList<Level>();

    public Levels() {
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public Level getLevel(int index) {
        return levels.get(index);
    }

    public ArrayList<Level> getList() {
        return levels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(levels);
    }

    public Levels(Parcel in) {
        in.readTypedList(levels, Level.CREATOR);
    }
}
