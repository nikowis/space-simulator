package pl.nikowis.shaders.particles;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class ParticleTexture {

    private int textureId;

    private int numberOfRows;

    public ParticleTexture(int textureId, int numberOfRows) {
        this.textureId = textureId;
        this.numberOfRows = numberOfRows;
    }

    public int getTextureId() {
        return textureId;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
