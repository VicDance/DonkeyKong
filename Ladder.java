package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Ladder {

    private float width, height;
    private Vector2 position;

    public Ladder(float width, float height, Vector2 position){

        this.width = width;
        this.height = height;
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.ladderAssets.ladder;

        Utils.drawTextureRegion(batch, region, position);
    }
}
