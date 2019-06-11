package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums.*;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Fire {

    private final Direction direction;

    public boolean active;

    private Vector2 position;

    private Level level;

    public Vector2 getPosition() {
        return position;
    }

    public Fire(Vector2 position, Direction direction, Level level) {
        this.position = position;
        this.direction = direction;
        this.level = level;

        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(float delta) {
        switch (direction) {
            case LEFT:
                position.x -= delta * Constants.ATTACK_MOVE_SPEED;
                break;
            case RIGHT:
                position.x += delta * Constants.ATTACK_MOVE_SPEED;
                break;
        }

        float worldWidth = level.getViewport().getWorldWidth();

        float horizontalPosition = level.getViewport().getCamera().position.x;

        if (position.x < horizontalPosition - worldWidth / 2 || position.x > horizontalPosition + worldWidth / 2) {

            active = false;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.fireAssets.attackRight;

        Utils.drawTextureRegion(batch, region, position, Constants.FIRE_CENTER);
    }
}
