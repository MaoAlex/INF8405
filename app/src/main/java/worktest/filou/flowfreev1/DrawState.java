package worktest.filou.flowfreev1;

import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedList;

//Gére les couleurs durant une partie
public class DrawState implements Parcelable {

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

    public DrawState(){
    }

    public DrawState(Parcel in) {
        current_x = in.readInt();
        current_y = in.readInt();
        String tmpState = in.readString();
        try {
            internalState = InternalDrawState.valueOf(in.readString());
        } catch (IllegalArgumentException x) {
            internalState = null;
        }
        IntListWrapper intListWrapper = in.readParcelable(IntListWrapper.class.getClassLoader());
        colorHistory = intListWrapper.getColorList();
        strokeW = in.readInt();
        x_offset = in.readInt();
        y_offset = in.readInt();

    }
    private int current_x, current_y;
    //Dit si on doit dessiner ou on
    private InternalDrawState internalState = InternalDrawState.DRAWOFF;
    //pile de couleurs
    private LinkedList<Integer> colorHistory = new LinkedList<>();
    //largeur du trait
    private int strokeW;
    private int x_offset, y_offset;

    public void setStrokeW(int x_offset,int y_offset) {
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        strokeW = Math.min(x_offset, y_offset);
        strokeW /= 4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_x);
        dest.writeInt(current_y);
        dest.writeString((internalState == null) ? "" : internalState.toString());
        IntListWrapper intListWrapper = new IntListWrapper(colorHistory);
        dest.writeParcelable(intListWrapper, 0);
        dest.writeInt(strokeW);
        dest.writeInt(x_offset);
        dest.writeInt(y_offset);
    }

    public int getX_offset() {
        return x_offset;
    }

    public void setX_offset(int x_offset) {
        this.x_offset = x_offset;
    }

    //utile en cas rotation d'écran
    public void updateStrikeSize() {
        setStrokeW(x_offset, y_offset);
    }

    public int getY_offset() {
        return y_offset;
    }

    public void setY_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public InternalDrawState getInternalState() {
        return internalState;
    }

    public void setInternalState(InternalDrawState internalState) {
        this.internalState = internalState;
    }

    //rajoute une couleur à l'historique
    public void setCurrentColor(int currentColor) {
        colorHistory.add(currentColor);
    }

    public int getCurrentColor() {
        return colorHistory.getLast();
    }

    public int pullCurrentColor() {return colorHistory.pollLast(); }

    //vérifie si un mouvement est valide
    public boolean validMove(int i, int j) {
        //vérifie qu'on se déplace
        boolean isValid = i != current_x || j != current_y;
        if (!isValid)
            return false;

        //on bouge dans un rayon de 1
        int absX = Math.abs(current_x - i), absY = Math.abs(current_y - j);
        isValid = absX  <= 1 && absY <= 1;
        //mais on ne peut se déplacer de un dans toutes les directions
        isValid = isValid && (absX == 0 || absX - absY != 0);
        return isValid;
    }

    //crée un couleur pour le trait
    public Paint setupPaint(int color, boolean filled) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeW);
        if (filled)
            paint.setStyle(Paint.Style.FILL);
        else
            paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        return paint;
    }

    public int getCurrent_x() {
        return current_x;
    }

    public void setCurrent_x(int current_x) {
        this.current_x = current_x;
    }

    public int getCurrent_y() {
        return current_y;
    }

    public void setCurrent_y(int current_y) {
        this.current_y = current_y;
    }

    public void changeDrawState(InternalDrawState internalState) {
        this.internalState = internalState;
    }

    //crée un tube qui lie 2 cases
    public GraphicalTubPart createTubBetweenCases(int org_i, int org_j,
                                                   int dest_i, int dest_j,
                                                   int color, AbsGridElement[][] grid) {
        Path drawPath = new Path();
        Paint paint = setupPaint(color, false);
        drawPath.moveTo(grid[org_i][org_j].getCenterX(), grid[org_i][org_j].getCenterY());
        drawPath.lineTo(grid[dest_i][dest_j].getCenterX(), grid[dest_i][dest_j].getCenterY());

        return new GraphicalTubPart(paint, drawPath);
    }

    //crée un tube jusqu'à la position courante
    public GraphicalTubPart createTubToPos(int org_i, int org_j,
                                                   float dest_x, float dest_y,
                                                   int color, AbsGridElement[][] grid) {
        Path drawPath = new Path();
        Paint paint = setupPaint(color, false);
        drawPath.moveTo(grid[org_i][org_j].getCenterX(), grid[org_i][org_j].getCenterY());
        drawPath.lineTo(dest_x, dest_y);

        return new GraphicalTubPart(paint, drawPath);
    }

    //crée un tube jusqu'à une bordure d'une autre  case
    public GraphicalTubPart createTubToBorder(int org_i, int org_j,
                                                   int dest_i, int dest_j,
                                               int color, AbsGridElement[][] grid) {
        Path drawPath = new Path();
        Paint paint = setupPaint(color, false);
        float mx, my;
        mx = grid[org_i][org_j].getCenterX();
        my = grid[org_i][org_j].getCenterY();
        drawPath.moveTo(mx, my);
        mx += grid[dest_i][dest_j].getCenterX();
        my += grid[dest_i][dest_j].getCenterY();
        mx /= 2;
        my /= 2;
        drawPath.lineTo(mx, my);

        return new GraphicalTubPart(paint, drawPath);
    }

    //Crée deux morceaux de tues reliant 2 cases adjacentes au niveau de la bordure commune
    public GraphicalTubPart[] createTubParts(int org_i, int org_j,
                                              int dest_i, int dest_j,
                                              int color, AbsGridElement[][] grid) {
        GraphicalTubPart[] fromBothSide = new GraphicalTubPart[2];
        fromBothSide[0] = createTubToBorder(org_i, org_j, dest_i, dest_j, color, grid);
        fromBothSide[1] = createTubToBorder(dest_i, dest_j, org_i, org_j, color, grid);

        return fromBothSide;
    }

    //vérifie qu'on peut aller sur la case (i,j) du point de vue des couleurs
    public boolean isColorFriendly(int i, int j,
                                   AbsGridElement[][] grid, HashMap<Integer,
                                    Tube> colorToTubes) {
        int i0 = getCurrent_x(), j0 = getCurrent_y();
        //la case est vide = ok
        if (!grid[i][j].isColored())
            return true;
        int colorOrg = getCurrentColor();
        int colorDest = grid[i][j].getColor();
        int index = grid[i][j].getIndexTubePart(colorOrg);
        if (colorDest != colorOrg && index == -1) {
            // la case ne posséde pas e tube de la couleur courante = ok
            return true;
        } else if (colorDest == colorOrg) {
            Position position = colorToTubes.get(colorOrg).getEnd();
            if (position.getI() == i && position.getJ() == j) {
                //on vient d'atteindre la fin du tube (l'autre délimiteur)
                colorToTubes.get(colorOrg).setIsComplete(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
