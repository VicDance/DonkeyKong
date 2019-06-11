package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Door {

    private Vector2 position;
    private Level level;

    public Door(Vector2 position, Level level) {
        this.position = position;
        this.level = level;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render(SpriteBatch batch){

        final TextureRegion region = Assets.instance.doorAssets.door;

        Utils.drawTextureRegion(batch, region, position, Constants.EXIT_DOOR_CENTER);
    }

    public void update(float delta) { }
}
