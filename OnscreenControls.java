package com.udacity.gamedev.donkeykong.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.donkeykong.entities.Peach;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Utils;


public class OnscreenControls extends InputAdapter {

    public static final String TAG = OnscreenControls.class.getName();

    public final Viewport viewport;
    public Peach peach;
    private Vector2 moveLeftCenter;
    private Vector2 moveRightCenter;
    private Vector2 moveDownCenter;
    private Vector2 moveUpCenter;
    private Vector2 shootCenter;
    private Vector2 jumpCenter;
    private int moveLeftPointer;
    private int moveRightPointer;
    private int moveDownPointer;
    private int moveUpPointer;
    private int jumpPointer;

    public void setPeach(Peach peach) {
        this.peach = peach;
    }

    public OnscreenControls() {
        this.viewport = new ExtendViewport(
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE);


        moveLeftCenter = new Vector2();
        moveRightCenter = new Vector2();
        moveDownCenter = new Vector2();
        moveUpCenter = new Vector2();
        shootCenter = new Vector2();
        jumpCenter = new Vector2();

        recalculateButtonPositions();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(shootCenter) < Constants.BUTTON_RADIUS) {
            peach.shooting();
        } else if (viewportPosition.dst(jumpCenter) < Constants.BUTTON_RADIUS) {
            jumpPointer = pointer;
            peach.setJumpButtonPressed(true);
        } else if (viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {
            moveLeftPointer = pointer;
            peach.setLeftButtonPressed(true);
        } else if (viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {
            moveRightPointer = pointer;
            peach.setRightButtonPressed(true);
        }else if(viewportPosition.dst(moveUpCenter)< Constants.BUTTON_RADIUS){
            moveUpPointer = pointer;
            peach.setUpButtonPressed(true);
        }else if(viewportPosition.dst(moveDownCenter)< Constants.BUTTON_RADIUS){
            moveDownPointer = pointer;
            peach.setDownButtonPressed(true);
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (pointer == moveLeftPointer && viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {

            peach.setLeftButtonPressed(false);
            peach.setRightButtonPressed(true);
            moveLeftPointer = 0;
            moveRightPointer = pointer;
        }

        if (pointer == moveRightPointer && viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {

            peach.setRightButtonPressed(false);
            peach.setLeftButtonPressed(true);
            moveRightPointer = 0;
            moveLeftPointer = pointer;
        }

        return super.touchDragged(screenX, screenY, pointer);
    }

    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        if (!Gdx.input.isTouched(jumpPointer)) {
            peach.setJumpButtonPressed(false);
            jumpPointer = 0;
        }

        if (!Gdx.input.isTouched(moveLeftPointer)) {
            peach.setLeftButtonPressed(false);
            moveLeftPointer = 0;
        }

        if (!Gdx.input.isTouched(moveRightPointer)) {
            peach.setRightButtonPressed(false);
            moveRightPointer = 0;
        }

        if (!Gdx.input.isTouched(moveUpPointer)) {
            peach.setUpButtonPressed(false);
            moveUpPointer = 0;
        }

        if (!Gdx.input.isTouched(moveDownPointer)) {
            peach.setDownButtonPressed(false);
            moveDownPointer = 0;
        }

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.buttonLeft,
                moveLeftCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.buttonRight,
                moveRightCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.buttonUp,
                moveUpCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.buttonDown,
                moveDownCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.buttonShoot,
                shootCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.buttonJump,
                jumpCenter,
                Constants.BUTTON_CENTER
        );

        batch.end();
    }

    public void recalculateButtonPositions() {
        moveLeftCenter.set(Constants.BUTTON_RADIUS + 5, Constants.BUTTON_RADIUS + 25);
        moveRightCenter.set(Constants.BUTTON_RADIUS + 40, Constants.BUTTON_RADIUS + 25);
        moveUpCenter.set(Constants.BUTTON_RADIUS + 23, Constants.BUTTON_RADIUS + 50);
        moveDownCenter.set(Constants.BUTTON_RADIUS + 23, Constants.BUTTON_RADIUS);
        shootCenter.set(viewport.getWorldWidth() - Constants.BUTTON_RADIUS, Constants.BUTTON_RADIUS);
        jumpCenter.set(viewport.getWorldWidth() - Constants.BUTTON_RADIUS * 3, Constants.BUTTON_RADIUS);
    }
}
