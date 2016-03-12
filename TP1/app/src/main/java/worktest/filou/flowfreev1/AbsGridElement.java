package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

//représente une case
public abstract class AbsGridElement implements Parcelable {
   private int i, j;
    private float centerX, centerY;
    //permet d'accéder au tubepar d'une certaine coukeur
    protected LinkedHashMap<Integer,Integer> colorsToIndexTubParts = new LinkedHashMap<>();
    //pile de couleur
    protected LinkedList<Integer> colorsInOrder = new LinkedList<>();

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public AbsGridElement(int i, int j, float centerX, float centerY) {
        this.i = i;
        this.j = j;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void reset() {
        colorsToIndexTubParts = new LinkedHashMap<>();
        colorsInOrder = new LinkedList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AbsGridElement(Parcel in) {
        i = in.readInt();
        j = in.readInt();
        IntListWrapper intListWrapper = in.readParcelable(IntListWrapper.class.getClassLoader());
        colorsInOrder = intListWrapper.getColorList();
        HashIntIntWrapper hashIntIntWrapper = in.readParcelable(HashIntIntWrapper.class.getClassLoader());
        colorsToIndexTubParts = new LinkedHashMap<>();
        hashIntIntWrapper.fillMap(colorsToIndexTubParts);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(i);
        dest.writeInt(j);
        IntListWrapper intListWrapper = new IntListWrapper(colorsInOrder);
        dest.writeParcelable(intListWrapper, 0);
        HashIntIntWrapper hashIntIntWrapper = new HashIntIntWrapper(colorsToIndexTubParts);
        dest.writeParcelable(hashIntIntWrapper, 0);
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCurrentColor(int color) {
        colorsInOrder.add(color);
    }

    public float getCenterY() {
        return centerY;
    }

    public void addIndexTubePart(int color, int indexTubePart) {
        colorsToIndexTubParts.put(color, indexTubePart);
    }

    public void removeColor(int color) {
        colorsToIndexTubParts.remove(color);
    }

    public int getIndexTubePart(int color) {
        return colorsToIndexTubParts.containsKey(color)? colorsToIndexTubParts.get(color) : -1;
    }

    public boolean isEmpty() {
        return colorsToIndexTubParts.isEmpty();
    }

    public int nbTubPart() {
        return colorsToIndexTubParts.size();
    }

    public abstract int getColor();

    public abstract boolean isColored();

    public void undo() {
        int oldColor = colorsInOrder.pollLast();
        colorsToIndexTubParts.remove(oldColor);
    }

    //dessine tous les éléments
    public void draw(Canvas canvas, HashMap<Integer, Tube> colorToTubes) {
        for (Map.Entry<Integer, Integer> colorAndIndex: colorsToIndexTubParts.entrySet()) {
            colorToTubes.get(colorAndIndex.getKey()).getTubePart(colorAndIndex.getValue()).draw(canvas);
        }
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    //recalcule les tailles
    public void updateSize(DrawState drawState, AbsGridElement[][] grid, HashMap<Integer, Tube> colorToTubes) {
        for (Map.Entry<Integer, Integer> entry : colorsToIndexTubParts.entrySet()) {
            int color = entry.getKey();
            int indexCurrentTubPart = entry.getValue();
            Tube tube = colorToTubes.get(color);
            TubePart tubePart = tube.getTubePart(indexCurrentTubPart);

            if (indexCurrentTubPart - 1 >= 0 ) {
                //recalcule le trait pour joindre la case précédente
                TubePart tubePartPrevious = tube.getTubePart(indexCurrentTubPart - 1);
                tubePart.setGraphicalTubPrevious(drawState.createTubToBorder(tubePart.getI(), tubePart.getJ(),
                        tubePartPrevious.getI(), tubePartPrevious.getJ(), color, grid));
            }


            if (indexCurrentTubPart  < tube.getLastIndex()) {
                //recalcule le trait pour joindre la case suivante
                TubePart tubePartNext = tube.getTubePart(indexCurrentTubPart + 1);
                tubePart.setGraphicalTubNext(drawState.createTubToBorder(tubePart.getI(), tubePart.getJ(),
                        tubePartNext.getI(), tubePartNext.getJ(), color, grid));
            }
        }
    }
}
