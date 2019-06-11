package com.udacity.gamedev.donkeykong;

import com.badlogic.gdx.Game;

public class DonkeyGame extends Game {
    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }

    public void showDonkeyScreen() {
        setScreen(new DonkeyScreen());
    }
}
