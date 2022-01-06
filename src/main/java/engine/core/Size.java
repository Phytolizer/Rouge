package engine.core;

public class Size {
    private float width;
    private float height;

    public Size(float aWidth, float aHeight) {
        this.width = aWidth;
        this.height = aHeight;
    }

    public void setWidth(float aWidth) {
        this.width = aWidth;
    }

    public void setHeight(float aHeight) {
        this.height = aHeight;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
