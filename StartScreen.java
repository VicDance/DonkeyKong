package com.udacity.gamedev.donkeykong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.udacity.gamedev.donkeykong.util.Constants;

public class StartScreen extends InputAdapter implements Screen {

    DonkeyGame donkeyGame;

    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font;

    public StartScreen(DonkeyGame donkeyGame){
        this.donkeyGame = donkeyGame;
    }

    @Override
    public void show() {

        renderer = new ShapeRenderer();

        batch = new SpriteBatch();

        viewport = new FitViewport(Constants.START_WORLD_SIZE, Constants.START_WORLD_SIZE);

        Gdx.input.setInputProcessor(this);// con esta funcion llamo a touchDown()

        font = new BitmapFont();

        font.getData().setScale(Constants.START_LABEL_SCALE);

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {

        viewport.apply();

        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        //dibujar el boton
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Constants.START_COLOR);

        renderer.circle(Constants.BUTTON_START_CENTER.x, Constants.BUTTON_START_CENTER.y, Constants.START_BUBBLE_RADIUS);

        renderer.end();


        batch.setProjectionMatrix(viewport.getCamera().combined);

        //dibujar letras
        batch.begin();

        final GlyphLayout easyLayout = new GlyphLayout(font, Constants.START_LABEL);

        font.draw(batch, Constants.START_LABEL, Constants.START_CENTER.x, (Constants.START_CENTER.y - 120) + easyLayout.height / 2, 0, Align.center, false);
        font.draw(batch, Constants.DONKEY_KONG_LABEL, Constants.START_CENTER.x, (Constants.START_CENTER.y + 130) + easyLayout.height/2, 0, Align.center, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(Constants.BUTTON_START_CENTER) < Constants.START_BUBBLE_RADIUS) {
            donkeyGame.showDonkeyScreen();
        }

        return true;
    }
}
