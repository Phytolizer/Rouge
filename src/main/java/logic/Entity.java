package logic;

public class Entity {
    private float x;
    private float y;

    public Entity() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public void increaseX(float inc) {
        this.x += inc;
    }

    public void increaseY(float inc) {
        this.y += inc;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
