package engine.logic.entities;
import engine.Drawer;
import engine.core.Position;
import engine.core.Size;

public class Rectangle extends Entity {
    private final Size size;
    private final Drawer drawer;

    public Rectangle(Position aPosition, Size aSize, Drawer aDrawer) {
        super(aPosition);
        this.size = (Size) aSize.clone();
        this.drawer = aDrawer;

    }
    public Rectangle(float width, float height, float xPos, float yPos, float zPos, Drawer aDrawer) {
        super(xPos, yPos, zPos);
        this.size = new Size(width, height);
        this.drawer = aDrawer;
    }

    public void moveBy(float xInc, float yInc, float zInc) {
        this.position.increaseX(xInc);
        this.position.increaseY(yInc);
        this.position.increaseZ(zInc);
    }

    public Size getSize() {
        return size;
    }

    public void draw() {
        drawer.drawRectangle(this);
    }
}
