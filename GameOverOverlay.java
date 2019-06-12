package com.udacity.gamedev.donkeykong.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.donkeykong.entities.DonkeyKong;
import com.udacity.gamedev.donkeykong.entities.Floor;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums;
import com.udacity.gamedev.donkeykong.util.Utils;


public class GameOverOverlay {

    public final Viewport viewport;
    final BitmapFont font;
    Array<DonkeyKong> kongs;
    long startTime;

    public GameOverOverlay() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
    }

    public void init() {
        startTime = TimeUtils.nanoTime();

        kongs = new Array<DonkeyKong>(Constants.ENEMY_COUNT);

        for (int i = 0; i < Constants.ENEMY_COUNT; i++) {

            Floor fakeFloor = new Floor(
                    MathUtils.random(viewport.getWorldWidth()),
                    MathUtils.random(-Constants.ENEMY_CENTER.y/2, viewport.getWorldHeight()
                    ), 0, 0);

            DonkeyKong kong = new DonkeyKong(fakeFloor);

            kongs.add(kong);
        }

    }

    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float timeElapsed = Utils.secondsSince(startTime);
        int enemiesToShow = (int) (Constants.ENEMY_COUNT * (timeElapsed / Constants.LEVEL_END_DURATION));

        /*for (int i = 0; i < enemiesToShow; i++){
            DonkeyKong kong = kongs.get(i);
            kong.update(0);
            kong.render(batch);
            kong.setDirection(Enums.Direction.STANDING);
        }*/

        font.draw(batch, Constants.GAME_OVER_MESSAGE, viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2.5f, 0, Align.center, false);

        batch.end();
    }
}
