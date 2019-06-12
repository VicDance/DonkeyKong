package com.udacity.gamedev.donkeykong.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.udacity.gamedev.donkeykong.entities.Peach;

public class ChaseCam {

    private Camera camera;
    private Peach target;

    public ChaseCam(Camera camera, Peach target) {

        this.camera = camera;
        this.target = target;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void update() {
        camera.position.x = target.getPosition().x;
        camera.position.y = target.getPosition().y;
    }
}
