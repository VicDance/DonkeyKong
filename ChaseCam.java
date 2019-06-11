package com.udacity.gamedev.donkeykong.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;

public class ChaseCam {

    private Camera camera;

    public ChaseCam(Camera camera) {

        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void update() { }
}
