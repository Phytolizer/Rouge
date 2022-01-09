package engine.logic.entities;
import engine.core.Position;

/**
 * the {@code Entity} class is used for the creation
 * of in-game objects, for example players, items, etc.
 */
public class Entity {
    protected final Position position;

    public Entity(Position aPosition) {
        this.position = (Position) aPosition.clone();
    }

    public Entity(float x, float y, float z) {
        this.position = new Position(x, y, z);
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


