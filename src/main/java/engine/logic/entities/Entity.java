package engine.logic.entities;
import engine.core.Position;

/**
 * the {@code Entity} class is used for the creation
 * of in-game objects, for example players, items, etc.
 */
public class Entity {
    private final Position position;

    public Entity(float x, float y, float z) {
        position = new Position(x, y, z);
    }

    public void moveBy(float xInc, float yInc, float zInc) {
        this.position.increaseX(xInc);
        this.position.increaseY(yInc);
        this.position.increaseZ(zInc);
    }

    public void moveTo(float x, float y, float z) {
        this.position.setX(x);
        this.position.setY(y);
        this.position.setZ(z);
    }

    public Position getPosition() {
        return this.position;
    }
}


