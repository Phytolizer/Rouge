package engine.entities;
import engine.Artist;
import engine.core.Coords;
import engine.internal.Rendering;

public class Rectangle extends Entity {
    private final Coords.Size size;
    private final RectangleGLData glData;
    private final Artist artist;
    private Rendering.Texture texture;

    public Rectangle(Coords.Position aPosition, Coords.Size aSize, Artist aArtist) {
        super(aPosition);
        this.size = (Coords.Size) aSize.clone();
        this.artist = aArtist;
        this.glData = new RectangleGLData(this.position, this.size);

    }
    public Rectangle(float width, float height, float xPos, float yPos, float zPos, Artist aArtist) {
        super(xPos, yPos, zPos);
        this.size = new Coords.Size(width, height);
        this.artist = aArtist;
        this.glData = new RectangleGLData(this.position, this.size);
    }


    public Coords.Size getSize() {
        return size;
    }

    public float[] getVBO() {
        return glData.getVBO();
    }

    public int[] getEBO() {
        return glData.getEBO();
    }

    public Rendering.Texture getTexture() {
        return this.texture;
    }

    public void setTexture(String imageSrc) {
        this.texture = new Rendering.Texture(imageSrc);
    }

    public void moveBy(float xInc, float yInc, float zInc) {
        this.position.increaseX(xInc);
        this.position.increaseY(yInc);
        this.position.increaseZ(zInc);
        glData.setVertexData(this.position, this.size);
    }


    public void draw() {
        artist.drawRectangle(this);
    }
}
