package worktest.filou.flowfreev1;

import android.graphics.Paint;
import android.graphics.Path;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by filou on 08/02/16.
 */
public class DrawState {
    private int current_x, current_y;
    private InternalDrawState internalState = InternalDrawState.DRAWOFF;
    private Deque<Integer> colorHistory = new LinkedList<>();
    private int strokeW;
    private int x_offset, y_offset;

    public void setStrokeW(int x_offset,int y_offset) {
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        strokeW = Math.min(x_offset, y_offset);
        strokeW /= 4;
    }

    public int getX_offset() {
        return x_offset;
    }

    public void setX_offset(int x_offset) {
        this.x_offset = x_offset;
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

    public void setCurrentColor(int currentColor) {
        colorHistory.add(currentColor);
    }

    public int getCurrentColor() {
        return colorHistory.getLast();
    }

    public boolean validMove(int i, int j) {
        //check if the position has changed
        boolean isValid = i != current_x || j != current_y;
        if (!isValid)
            return false;

        //ensure we move in the neighborhood (with 1 as radius)
        int absX = Math.abs(current_x - i), absY = Math.abs(current_y - j);
        isValid = absX  <= 1 && absY <= 1;
        //ensure that only one of them is equals to 1
        isValid = isValid && (absX == 0 || absX - absY != 0);
        return isValid;
    }

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

    public GraphicalTubPart createTubBetweenCases(int org_i, int org_j,
                                                   int dest_i, int dest_j,
                                                   int color, AbsGridElement[][] grid) {
        Path drawPath = new Path();
        Paint paint = setupPaint(color, false);
        drawPath.moveTo(grid[org_i][org_j].getCenterX(), grid[org_i][org_j].getCenterY());
        drawPath.lineTo(grid[dest_i][dest_j].getCenterX(), grid[dest_i][dest_j].getCenterY());

        return new GraphicalTubPart(paint, drawPath);
    }

    public GraphicalTubPart createTubToPos(int org_i, int org_j,
                                                   float dest_x, float dest_y,
                                                   int color, AbsGridElement[][] grid) {
        Path drawPath = new Path();
        Paint paint = setupPaint(color, false);
        drawPath.moveTo(grid[org_i][org_j].getCenterX(), grid[org_i][org_j].getCenterY());
        drawPath.lineTo(dest_x, dest_y);

        return new GraphicalTubPart(paint, drawPath);
    }

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

    public GraphicalTubPart[] createTubParts(int org_i, int org_j,
                                              int dest_i, int dest_j,
                                              int color, AbsGridElement[][] grid) {
        GraphicalTubPart[] fromBothSide = new GraphicalTubPart[2];
        fromBothSide[0] = createTubToBorder(org_i, org_j, dest_i, dest_j, color, grid);
        fromBothSide[1] = createTubToBorder(dest_i, dest_j, org_i, org_j, color, grid);

        return fromBothSide;
    }

    public boolean isColorFriendly(int i, int j, AbsGridElement[][] grid) {
        int i0 = getCurrent_x(), j0 = getCurrent_y();
        if (!grid[i0][j0].isColored())
            return true;
        int colorOrg = grid[i0][j0].getColor();
        if (grid[i][j].getTubePart(colorOrg) == null)
            return true;
        else if (grid[i][j].getTubePart(colorOrg).getTube().equals(grid[i0][j0].getTubePart(colorOrg).getTube())) {
            Position position = grid[i][j].getTubePart(colorOrg).getTube().getEnd();
            if (position.getI() == i && position.getJ() == j) {
                grid[i][j].getTubePart(colorOrg).getTube().setIsComplete(true);
                internalState = InternalDrawState.DRAWOFF;
                return true;
            } else
                return false;
        } else
            return false;
    }
}
