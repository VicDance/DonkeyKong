package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Utils;
import com.udacity.gamedev.donkeykong.util.Enums.*;

public class OilBarrel {

    private Vector2 position;
    private Level level;
    private long startTimeEnemy;
    private long elapsedTime;

    public OilBarrel(Vector2 position, Level level) {

        this.position = position;
        this.level = level;

        startTimeEnemy = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = Assets.instance.oilBarrelAssets.oilBarrel;

        elapsedTime = TimeUtils.timeSinceNanos(startTimeEnemy);

        Utils.drawTextureRegion(batch, region, position);
    }

    public void update(float delta) {

        Vector2 enemyPosition = new Vector2(
                position.x + 30,
                position.y
        );

        if (elapsedTime > 2000000000) {

            //for(int i = 0, j = 0; i < level.getOilBarrels().size && j < level.getEnemies().size; i++, j++) {
            level.spawnEnemy(enemyPosition, Direction.LEFT);
            startTimeEnemy = TimeUtils.nanoTime();
            //}

        }
    }
}
