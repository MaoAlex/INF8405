package worktest.filou.flowfreev1;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//repésente un niveau de jeu
public class Level implements Parcelable {
    
    public static final Parcelable.Creator<Level> CREATOR = new Parcelable.Creator<Level>() {
        @Override
        public Level createFromParcel(Parcel source) {
            return new Level(source);
        }

        @Override
        public Level[] newArray(int size) {
            return new Level[size];
        }
    };

    private int width;
    private int id;
    private int height;
    private boolean unlocked;
    private ArrayList<Delimiter> listOfDelimiters = new ArrayList<Delimiter>();

    public int getWidth() {
        return width;
    }

    public Delimiter getDelimiter(int index) {
        return listOfDelimiters.get(index);
    }
    public ArrayList<Delimiter> getDelimiters() {
        return listOfDelimiters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public boolean isUnlocked() {
        return unlocked;
    }
    public void unlocked() {
        this.unlocked = true;
    }//deverouillage du niveau

    public Level(int id, int width, int height, boolean unlocked) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.unlocked = unlocked;
    }

    public Level(Parcel in) {
        width = in.readInt();
        id = in.readInt();
        height = in.readInt();
        unlocked = (in.readInt() > 0) ? true : false ;
        in.readTypedList(listOfDelimiters, Delimiter.CREATOR);
    }

    public void addDelimiter(Delimiter delimiter) {
        listOfDelimiters.add(delimiter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(id);
        dest.writeInt(height);
        dest.writeInt(unlocked ? 1 : -1);
        dest.writeTypedList(listOfDelimiters);

    }
}
