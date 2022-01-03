package engine.core;

/**
 * The {@code Position} class is used to implement
 * 3-dimensional position coordinates, all the values
 * are in floats instead of doubles to reduce overhead
 */
public class Position {
    private float xPos;
    private float yPos;
    private float zPos;

    public Position(float x, float y, float z) {
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public float getZ() {
        return zPos;
    }

    public void setX(float x) {
        this.xPos = x;
    }

    public void setY(float y) {
        this.yPos = y;
    }

    public void setZ(float z) {
        this.zPos = z;
    }

    public void increaseX(float inc) {
        this.xPos += inc;
    }

    public void increaseY(float inc) {
        this.yPos += inc;
    }

    public void increaseZ(float inc) {
        this.zPos += inc;
    }
}
