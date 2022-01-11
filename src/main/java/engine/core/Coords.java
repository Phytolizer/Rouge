package engine.core;

public class Coords {
    public static float aspectRatio;

    public static void setAspectRatio(float anAspectRatio) {
        aspectRatio = anAspectRatio;
    }

    /**
     * The {@code Position} class is used to implement
     * 3-dimensional position coordinates, all the values
     * are in floats instead of doubles to reduce overhead
     */
    public static class Position implements Cloneable {
        public static int length = 3;

        private float xPos;
        private float yPos;
        private float zPos;

        public static void setAspectRatio(float anAspectRatio) {
            aspectRatio = anAspectRatio;
        }

        public Position(float x, float y, float z) {
            this.xPos = x;
            this.yPos = y * aspectRatio;
            this.zPos = z;
        }

        public float getX() {
            return xPos;
        }

        public float getY() {
            return yPos;
        }

        public float getZ() {
            return zPos;
        }

        public void setX(float x) {
            this.xPos = x;
        }

        public void setY(float y) {
            this.yPos = y * aspectRatio;
        }

        public void setZ(float z) {
            this.zPos = z;
        }

        public void increaseX(float inc) {
            this.xPos += inc;
        }

        public void increaseY(float inc) {
            this.yPos += inc * aspectRatio;
        }

        public void increaseZ(float inc) {
            this.zPos += inc;
        }

        public Object clone() {
            try {
                return super.clone();
            } catch(CloneNotSupportedException e) {
                return new Position(0f, 0f, 0f);
            }
        }
    }

    public static class Size implements Cloneable {
        private float width;
        private float height;

        public Size(float aWidth, float aHeight) {
            this.width = aWidth;
            this.height = aHeight * aspectRatio;
        }

        public void setWidth(float aWidth) {
            this.width = aWidth;
        }

        public void setHeight(float aHeight) {
            this.height = aHeight * aspectRatio;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public Object clone() {
            try {
                return super.clone();
            } catch(CloneNotSupportedException e) {
                return new Size(0f, 0f);
            }
        }
    }
}
