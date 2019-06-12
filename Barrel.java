package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums.*;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Barrel {

    public static final String TAG = Barrel.class.toString();

    private Direction direction;
    private final Level level;

    public boolean active;

    private Vector2 position;

    private Rectangle barrelBounds;
    private Rectangle wallBounds;

    ShapeRenderer shapeRenderer;

    public Barrel(Level level, Vector2 position, Direction direction) {
        this.level = level;
        this.position = position;
        this.direction = direction;

        this.active = true;

        shapeRenderer = new ShapeRenderer();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBarrelBounds() {
        return barrelBounds;
    }

    public void update(float delta) {

        if(direction == Direction.RIGHT) {
            position.x += delta * Constants.BARREL_MOVE_SPEED;
        }else{
            position.x -= delta * Constants.BARREL_MOVE_SPEED;
        }

        float worldWidth = level.getViewport().getWorldWidth();

        float horizontalPosition = level.getViewport().getCamera().position.x;

        if (position.x < horizontalPosition - worldWidth / 2 || position.x > horizontalPosition + worldWidth / 2) {

            active = false;
        }

        //for (int i = 0; i < level.getBarrels().size; i++) {

        barrelBounds = new Rectangle(
                position.x + Constants.BARREL_HEIGHT,
                position.y + Constants.BARREL_HEIGHT,
                Constants.BARREL_HEIGHT,
                Constants.BARREL_HEIGHT
        );
        //}

        for (int i = 0; i < level.getWalls().size; i++) {
            wallBounds = new Rectangle(
                    level.getWalls().get(i).left,
                    level.getWalls().get(i).bottom,
                    level.getWalls().get(i).width,
                    level.getWalls().get(i).height + 20
            );

            if (barrelBounds.overlaps(wallBounds)) {
                //Gdx.app.log(TAG, "choca");
                position.y = position.y - 120;
                if (direction == Direction.RIGHT) {
                    direction = Direction.LEFT;
                }else if(direction == Direction.LEFT){
                    direction = Direction.RIGHT;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = Assets.instance.barrelAssets.barrel;

        Utils.drawTextureRegion(batch, region, position, Constants.BARREL_CENTER);
    }
}
