package engine.logic.entities;
import engine.core.Position;

/**
 * the {@code Entity} class is used for the creation
 * of in-game objects, for example players, items, etc.
 */
public class Entity {
    public final Position position;

    public Entity() {
        position = new Position(0f, 0f, 0f);
    }
}


