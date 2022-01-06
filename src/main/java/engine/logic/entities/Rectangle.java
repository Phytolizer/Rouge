package engine.logic.entities;
import engine.core.Size;

public class Rectangle extends Entity {
    private final Size size;

    public Rectangle(float width, float height, float xPos, float yPos, float zPos) {
        super(xPos, yPos, zPos);
        this.size = new Size(width, height);
    }
}
