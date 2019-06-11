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

    public DonkeyKong(Level level) {

        //this.floor = floor;
        this.level = level;
        position = new Vector2(Constants.KONG_EYE_POSITION);
        //velocity = new Vector2();
        direction = Direction.STANDING;
        startTimeBarrel = TimeUtils.nanoTime();
        startTimeDonkey = TimeUtils.nanoTime();
        lifes = Constants.KONG_LIFES;
    }

    public Vector2 getPosition() {
        return position;
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

        Utils.drawTextureRegion(batch, region, Constants.KONG_EYE_POSITION.x, Constants.KONG_EYE_POSITION.y-3);
    }

    public void update(float delta) {

        if (direction == Direction.RIGHT) {
            Vector2 barrelPosition;
            //System.out.println(elapsedTime);
            barrelPosition = new Vector2(
                    position.x - Constants.KONG_OFFSET.x + 40,
                    position.y - Constants.KONG_OFFSET.y - 17
            );

            if (elapsedTime > 2000000000) {

                level.spawnBarrel(barrelPosition, direction);

                startTimeBarrel = TimeUtils.nanoTime();
            }
        }
    }
}
