package pl.nikowis.textures;

/**
 * Base terrain texture.
 * Created by Nikodem on 12/26/2016.
 */
public class TerrainTexture {

    private int textureID;

    public TerrainTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getTextureID() {
        return textureID;
    }
}
