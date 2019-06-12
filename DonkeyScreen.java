package com.udacity.gamedev.donkeykong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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

    public DonkeyScreen(){
    }

    @Override
    public void show() {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        gameplayViewport = new ExtendViewport(Constants.WORLD_SIZE*2, Constants.WORLD_SIZE*2);

        level = LevelLoader.load("Level1", gameplayViewport);
        //level = new Level(gameplayViewport);

        chaseCam = new ChaseCam(gameplayViewport.getCamera());

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("raw/DonkeyKong64Music.mp3"));
        backgroundMusic.play();

        //startNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        gameplayViewport.update(width, height, true);
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
    }

    private void startNewLevel(){

        int contNivel = 1;
        String levelName = "";

        //while(contNivel != 3){
            if(contNivel == 1){
                levelName = "Level" + contNivel;
                level = LevelLoader.load(levelName, gameplayViewport);

                chaseCam.setCamera(level.getViewport().getCamera());

                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

                for(int i = 0; i < level.getKong().size; i++){
                    if (level.getKong().get(i).getLifes() == 0){
                        contNivel++;
                    }
                }
            }/*else{
                levelName = "Level" + contNivel;

                level = LevelLoader.load(levelName, gameplayViewport);

                chaseCam.setCamera(level.getViewport().getCamera());

                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                //contNivel++;
            }*/
        //}
        //levelName = "LevelOne";
        //level = LevelLoader.load(levelName, gameplayViewport);

        //chaseCam.setCamera(level.getViewport().getCamera());
        //onscreenControls.gigaGal = level.getGigaGal();
        //resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //for(int i = 0; i < level.getKong().size; i++) {
            /*if (level.getKong().get(i).getLifes() == 0){
                levelName = "Level" + contNivel;
                level = LevelLoader.load(levelName, gameplayViewport);

                chaseCam.setCamera(level.getViewport().getCamera());
                //onscreenControls.gigaGal = level.getGigaGal();
                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }*//*else{
                levelName = "LevelOne";
                level = LevelLoader.load(levelName, gameplayViewport);

                chaseCam.setCamera(level.getViewport().getCamera());
                //onscreenControls.gigaGal = level.getGigaGal();
                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }*/
        //}
    }
}
