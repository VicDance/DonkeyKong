package com.udacity.gamedev.donkeykong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.udacity.gamedev.donkeykong.overlays.PeachHud;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.ChaseCam;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.LevelLoader;

public class DonkeyScreen extends ScreenAdapter {

    private Level level;

    private SpriteBatch batch;

    private ExtendViewport gameplayViewport;

    private ChaseCam chaseCam;

    private Music backgroundMusic;

    private int contNivel = 0;

    private PeachHud peachHud;

    public DonkeyScreen(){
    }

    @Override
    public void show() {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        gameplayViewport = new ExtendViewport(Constants.WORLD_SIZE*2, Constants.WORLD_SIZE*2);

        peachHud = new PeachHud();
        //level = LevelLoader.load("Level1", gameplayViewport);
        //level = new Level(gameplayViewport);

        //chaseCam = new ChaseCam(gameplayViewport.getCamera());

        /*backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("raw/DonkeyKong64Music.mp3"));
        backgroundMusic.play();*/

        startNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        gameplayViewport.update(width, height, true);
        peachHud.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render(float delta) {

        level.update(delta);
        chaseCam.update();
        gameplayViewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameplayViewport.getCamera().combined);
        batch.begin();
        level.render(batch);

        batch.end();

        peachHud.render(batch, level.getPeach().getLives());
    }

    private void cambiarNivel(){

        for(int i = 0; i < level.getKong().size; i++){

            if(level.getKong().get(i).getLifes() == 0){
                System.out.println("entra");
                contNivel++;
                startNewLevel();
            }
        }
    }

    private void startNewLevel(){

        level = LevelLoader.load(Constants.LEVELS[contNivel], gameplayViewport);

        chaseCam = new ChaseCam(gameplayViewport.getCamera());

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("raw/DonkeyKong64Music.mp3"));
        backgroundMusic.play();
    }
}
