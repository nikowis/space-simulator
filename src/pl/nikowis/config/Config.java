package pl.nikowis.config;

import org.lwjgl.util.vector.Vector3f;

/**
 * Class containing global variables of the program.
 * Created by Nikodem on 12/30/2016.
 */
public class Config {

    /**
     * Field of view ( view width )
     */
    public static final float FOV = 70;

    /**
     * Near plane rendering distance.
     */
    public static final float NEAR_PLANE = 0.1f;

    /**
     * Far plane rendering distance.
     */
    public static final float FAR_PLANE = 1500;

    /**
     * Terrain size.
     */
    public static final float TERRAIN_SIZE = 800;

    /**
     * Terrain vertex count.
     */
    public static final int TERRAIN_VERTEX_COUNT = 150;

    /**
     * Window width.
     */
    public static final int WINDOW_WIDTH = 1800;

    /**
     * Window height.
     */
    public static final int WINDOW_HEIGHT = 930;

    /**
     * Game FPS cap.
     */
    public static final int FPS_CAP = 120;

    public static final String WINDOW_TITLE = "Space";

    public static final Vector3f BCKGRND = new Vector3f(0.01f,  0.01f, 0.05f);

}
