package com.udacity.gamedev.donkeykong;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.udacity.gamedev.donkeykong.overlays.GameOverOverlay;
import com.udacity.gamedev.donkeykong.overlays.OnscreenControls;
import com.udacity.gamedev.donkeykong.overlays.PeachHud;
import com.udacity.gamedev.donkeykong.overlays.VictoryOverlay;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.ChaseCam;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.LevelLoader;
import com.udacity.gamedev.donkeykong.util.Utils;

public class DonkeyScreen extends ScreenAdapter {

    private OnscreenControls onscreenControls;
    private Level level;
    private SpriteBatch batch;
    private ExtendViewport gameplayViewport;
    private ChaseCam chaseCam;
    private Music backgroundMusic;
    private int contNivel = 0;
    private PeachHud peachHud;
    private long levelEndOverlayStartTime;
    private GameOverOverlay gameOverOverlay;
    private Music gameOverMusic;
    private int contNivelVida = 0;
    private VictoryOverlay victoryOverlay;

    public DonkeyScreen(){
    }

    @Override
    public void show() {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        gameplayViewport = new ExtendViewport(Constants.WORLD_SIZE*2, Constants.WORLD_SIZE*2);

        peachHud = new PeachHud();

        gameOverOverlay = new GameOverOverlay();

        victoryOverlay = new VictoryOverlay();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("raw/DonkeyKong64Music.mp3"));
        backgroundMusic.play();

        //onscreenControls = new OnscreenControls();

        /*if (onMobile()) {
            Gdx.input.setInputProcessor(onscreenControls);
        }*/

        startNewLevel();
    }

    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }

    @Override
    public void resize(int width, int height) {
        gameplayViewport.update(width, height, true);
        peachHud.viewport.update(width, height, true);
        gameOverOverlay.viewport.update(width, height, true);
        victoryOverlay.viewport.update(width, height, true);
        //chaseCam.setCamera(level.getViewport().getCamera());

        //onscreenControls.viewport.update(width, height, true);
        //onscreenControls.recalculateButtonPositions();
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

        //onscreenControls.render(batch);

        /*if (onMobile()) {
            onscreenControls.render(batch);
        }*/

        peachHud.render(batch, level.getPeach().getLives());

        renderLevelEndOverlays(batch);
    }

    private void renderLevelEndOverlays(SpriteBatch batch){

        if (level.isGameOver()) {

            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                gameOverOverlay.init();
            }
            gameOverOverlay.render(batch);
            backgroundMusic.stop();
            gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("raw/gameover.mp3"));
            gameOverMusic.play();

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelFailed();
            }
        }else if(level.isNextLevel()){
            levelComplete();
        }else if(level.getPeach().isLifeLevel()){
            getMoreLifes();
        }else if(level.getPeach().isBackToLevel()){
            backToLevel();
        }
    }

    private void levelComplete(){
        contNivel++;

        if(contNivel > 2){
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                victoryOverlay.init();
            }
            victoryOverlay.render(batch);
        }else{
            startNewLevel();

            System.out.println("nivel:" + contNivel);
        }
    }

    private void backToLevel(){
        startNewLevel();
    }

    private void getMoreLifes(){
        level = LevelLoader.load(Constants.LIFE_LEVELS[contNivelVida], gameplayViewport);

        chaseCam = new ChaseCam(gameplayViewport.getCamera(), level.getPeach());
    }

    private void startNewLevel(){

        level = LevelLoader.load(Constants.LEVELS[contNivel], gameplayViewport);

        chaseCam = new ChaseCam(gameplayViewport.getCamera(), level.getPeach());

        //onscreenControls.setPeach(level.getPeach());

        if(!backgroundMusic.isPlaying()){
            backgroundMusic.play();
        }
    }

    public void levelFailed() {

        gameOverMusic.stop();
        startNewLevel();
    }
}
