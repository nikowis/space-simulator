package pl.nikowis.shaders.screen;


public class ScreenTile {

    private float x, z, y;
    private float rotX, rotY, rotZ;
    private float size;

    public ScreenTile(float x, float y, float z , float rotX, float rotY, float rotZ, float size) {
        this.x = x;
        this.z = z;
        this.y = y;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.size = size;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public float getY() {
        return y;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getSize() {
        return size;
    }


}
