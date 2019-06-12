package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums.*;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Enemy {

    private Direction direction;
    private Level level;
    private Vector2 position;
    private boolean active;
    private long startTime;
    private final float bobOffset;

    public Enemy(Level level, Direction direction, Vector2 position) {
        this.direction = direction;
        this.position = position;
        this.level = level;

        this.active = true;

        startTime = TimeUtils.nanoTime();
        bobOffset = MathUtils.random();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public void update(float delta) {
        switch (direction) {
            case LEFT:
                position.x -= Constants.ENEMY_MOVEMENT_SPEED * delta;
                break;
            case RIGHT:
                position.x += Constants.ENEMY_MOVEMENT_SPEED * delta;
        }

        float worldWidth = level.getViewport().getWorldWidth();

        float horizontalPosition = level.getViewport().getCamera().position.x;

        if (position.x < horizontalPosition - worldWidth / 2 || position.x > horizontalPosition + worldWidth / 2) {

            active = false;
        }

        final float elapsedTime = Utils.secondsSince(startTime);
        final float bobMultiplier = 1 + MathUtils.sin(MathUtils.PI2 * (bobOffset + elapsedTime / Constants.ENEMY_BOB_PERIOD));
        /*for(int i = 0, j = 0; i < level.getOilBarrels().size && j < level.getEnemies().size; i++, j++) {
            level.getEnemies().get(j).position.y = level.getOilBarrels().get(j).getPosition().y + Constants.ENEMY_CENTER.y +  Constants.ENEMY_BOB_AMPLITUDE * bobMultiplier;
        }*/


    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.enemyAssets.enemy_left;

        direction = Direction.LEFT;

        Utils.drawTextureRegion(batch, region, position, Constants.OIL_BARREL_CENTER);
    }
}
