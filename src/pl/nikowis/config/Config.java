package pl.nikowis.config;

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
     * Tree map outline step.
     */
    public static final int TREE_OUTLINE_STEP = 7;

    /**
     * If multi jump should be allowed.
     */
    public static final boolean ALLOW_MULTI_JUMP = true;


}
