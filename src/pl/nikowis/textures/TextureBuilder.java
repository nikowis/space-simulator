package pl.nikowis.textures;

import pl.nikowis.renderEngine.Loader;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class TextureBuilder {

    private boolean clampEdges = false;
    private boolean mipmap = false;
    private boolean anisotropic = true;
    private boolean nearest = false;

    private String filePath;

    protected TextureBuilder(String filePath){
        this.filePath = filePath;
    }

    public EnvironmentMapTexture create(){
        TextureData textureData = Loader.decodeTextureFile(filePath);
        int textureId = Loader.loadTextureToOpenGL(textureData, this);
        return new EnvironmentMapTexture(textureId, textureData.getWidth());
    }

    public TextureBuilder clampEdges(){
        this.clampEdges = true;
        return this;
    }

    public TextureBuilder normalMipMap(){
        this.mipmap = true;
        this.anisotropic = false;
        return this;
    }

    public TextureBuilder nearestFiltering(){
        this.mipmap = false;
        this.anisotropic = false;
        this.nearest = true;
        return this;
    }

    public TextureBuilder anisotropic(){
        this.mipmap = true;
        this.anisotropic = true;
        return this;
    }

    public boolean isClampEdges() {
        return clampEdges;
    }

    public boolean isMipmap() {
        return mipmap;
    }

    public boolean isAnisotropic() {
        return anisotropic;
    }

    public boolean isNearest() {
        return nearest;
    }

}
