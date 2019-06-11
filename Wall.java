package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.udacity.gamedev.donkeykong.util.Assets;

public class Wall {

    public final float top;
    public final float bottom;
    public final float left;
    public final float right;
    public float width, height;

    public Wall(float left, float top, float width, float height){

        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    public void render(SpriteBatch batch) {
        width = right - left;
        height = top - bottom;

        Assets.instance.wallAssets.wallNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }

    public void update(float delta){
    }
}
