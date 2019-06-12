package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums.*;
import com.udacity.gamedev.donkeykong.util.Utils;

public class DonkeyKong {

    private Floor floor;
    private Level level;

    private Vector2 position;
    //private Vector2 velocity;

    private Direction direction;

    long startTimeBarrel;
    long startTimeDonkey;
    float elapsedTime;
    int elapsedTimeSeconds;

    private int lifes;

    public DonkeyKong(Level level, Floor floor) {

        this.floor = floor;
        this.level = level;
        position = new Vector2(floor.left, floor.top + Constants.KONG_CENTER.y);
        //velocity = new Vector2();
        direction = Direction.STANDING;
        startTimeBarrel = TimeUtils.nanoTime();
        startTimeDonkey = TimeUtils.nanoTime();
        lifes = Constants.KONG_LIFES;
    }

    public DonkeyKong(Floor floor){
        this.floor = floor;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = Assets.instance.donkeyAssets.standing;

        elapsedTime = TimeUtils.timeSinceNanos(startTimeBarrel);
        elapsedTimeSeconds = (int) Utils.secondsSince(startTimeDonkey);

        if (elapsedTimeSeconds % 2 == 0) {
            //System.out.println(elapsedTime);
            region = Assets.instance.donkeyAssets.standingRight;

            direction = Direction.RIGHT;
        }else{
            direction = Direction.STANDING;
        }

        Utils.drawTextureRegion(batch, region, position.x, position.y - 27);
    }

    public void update(float delta) {

        if (direction == Direction.RIGHT) {
            Vector2 barrelPosition;
            //System.out.println(elapsedTime);
            barrelPosition = new Vector2(
                    position.x + 30,
                    position.y - 25
            );

            if (elapsedTime > 2000000000) {

                level.spawnBarrel(barrelPosition, direction);

                startTimeBarrel = TimeUtils.nanoTime();
            }
        }
    }
}
