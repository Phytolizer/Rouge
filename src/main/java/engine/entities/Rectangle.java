package engine.entities;
import engine.Artist;
import engine.Window;
import engine.core.Coords;
import engine.internal.Rendering;

public class Rectangle extends Entity {
    private final Coords.Size size;
    private final RectangleGLData glData;
    private final Artist artist;

    /*
        changes here!
                    |
                    v
     */

    private float mouseX;
    private float mouseY;
    private float left;
    private float bottom;
    private float right;
    private float top;


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

        this.bottom = (this.position.getY()-(this.size.getHeight() / 2));
        this.left = (this.position.getX()-(this.size.getWidth() / 2));
        this.right = (this.size.getWidth() + left);
        this.top = (this.size.getHeight() + bottom);
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

    public float getBottom() {
        return this.bottom;
    }

    public float getLeft() {
        return this.left;
    }

    public float getRight() {
        return this.right;
    }

    public float getTop() {
        return this.top;
    }


    public boolean isHovered(Window containerWindow) {
        /*
        IDEA:
            This method is meant to take in the hover-zone, and the window it's in...
            It's supposed to convert the values of the mouse's position on screen to values relative
            to the grid inside the window.
            This is not necessary for collision, as values of entities within the window are all using
            the same units.
            It's only important for mouse hover events

            TODO:
                change 640 to whatever the window width is and divide it by 2...
                ...the value 0.5625 comes from 16/9,
                so dividing the aspect ratio is important.
                Remember to make those values relative rather than hardcoded to that ratio!

            ISSUES:
                mouseY is still being funky with it's positions, but mouseX seems to work fine no
                matter the position of the Rectangle.
         */

        Coords.Position cursorPosition = containerWindow.getCursorPos();
        mouseX = (cursorPosition.getX()-640) * 0.0032345f;
        mouseY = (- 0.5625f * (cursorPosition.getY()-640)) * 0.0032345f;

        return (mouseX > this.left) && (mouseY < this.top) && (mouseX < this.right) && (mouseY > this.bottom);
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

        this.bottom = (this.position.getY()-(this.size.getHeight() / 2));
        this.left = (this.position.getX()-(this.size.getWidth() / 2));
        this.right = (this.size.getWidth() + left);
        this.top = (this.size.getHeight() + bottom);
    }


    public void draw() {
        artist.drawRectangle(this);
    }
}
