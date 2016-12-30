package pl.nikowis.terrains;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.config.Config;
import pl.nikowis.models.RawModel;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.textures.TerrainTexture;
import pl.nikowis.textures.TerrainTexturePack;

/**
 * Terrain master class containing all information about the terrain.
 * Created by Nikodem on 12/25/2016.
 */
public class Terrain {

    private float x;
    private float z;
    private RawModel model;
    private TerrainTexturePack terrainTexturePack;
    private TerrainTexture blendMap;
    private float shineDamper = 1;
    private float reflectivity = 0;
    private Vector3f defaultColour;

    /**
     * Constructor.
     * @param gridX x coordinate ( 0 center of the screen )
     * @param gridZ z coordinate ( 0 center of the screen )
     * @param loader loader
     * @param terrainTexturePack texture pack
     * @param blendMap blend map
     * @param defaultColour default color to use when rendering without texturing
     */
    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack terrainTexturePack, TerrainTexture blendMap, Vector3f defaultColour) {
        this.terrainTexturePack = terrainTexturePack;
        this.blendMap = blendMap;
        this.x = gridX * Config.TERRAIN_SIZE;
        this.z = gridZ * Config.TERRAIN_SIZE;
        this.model = generateTerrain(loader);
        this.defaultColour = defaultColour;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public TerrainTexturePack getTerrainTexturePack() {
        return terrainTexturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    private RawModel generateTerrain(Loader loader) {
        int count = Config.TERRAIN_VERTEX_COUNT * Config.TERRAIN_VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (Config.TERRAIN_VERTEX_COUNT - 1) * (Config.TERRAIN_VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < Config.TERRAIN_VERTEX_COUNT; i++) {
            for (int j = 0; j < Config.TERRAIN_VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) Config.TERRAIN_VERTEX_COUNT - 1) * Config.TERRAIN_SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) Config.TERRAIN_VERTEX_COUNT - 1) * Config.TERRAIN_SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = (float) j / ((float) Config.TERRAIN_VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) Config.TERRAIN_VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < Config.TERRAIN_VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < Config.TERRAIN_VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * Config.TERRAIN_VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * Config.TERRAIN_VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public Vector3f getDefaultColour() {
        return defaultColour;
    }

    public void setDefaultColour(Vector3f defaultColour) {
        this.defaultColour = defaultColour;
    }
}
