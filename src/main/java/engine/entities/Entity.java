package engine.entities;

public class Entity {
    private float x;
    private float y;
    private float z;
    
    public Entity() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    
    public void increaseX(float inc) {
        this.x += inc;
    }
    
    public void increaseY(float inc) {
        this.y += inc;
    }
    
    public void increaseZ(float inc) {
        this.z += inc;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getZ() {
        return this.z;
    }
}


