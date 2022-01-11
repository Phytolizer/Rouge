package engine.entities;
import engine.core.Coords;

/**
 * the {@code Entity} class is used for the creation
 * of in-game objects, for example players, items, etc.
 */
public class Entity {
    protected final Coords.Position position;

    public Entity(Coords.Position aPosition) {
        this.position = (Coords.Position) aPosition.clone();
    }

    public Entity(float x, float y, float z) {
        this.position = new Coords.Position(x, y, z);
    }

    public void moveTo(float x, float y, float z) {
        this.position.setX(x);
        this.position.setY(y);
        this.position.setZ(z);
    }

    public Coords.Position getPosition() {
        return this.position;
    }
}


