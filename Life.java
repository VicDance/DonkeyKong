package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Life {

    private Level level;
    private Vector2 position;
    private boolean active;

    public Life(Level level, Vector2 position){
        this.level = level;
        this.position = position;

        this.active = true;
    }

    public Level getLevel() {
        return level;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = Assets.instance.lifeAssets.life;

        Utils.drawTextureRegion(batch, region, position);
    }

    public void update(float delta){}
}
