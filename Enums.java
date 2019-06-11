package com.udacity.gamedev.donkeykong.util;


public class Enums {

    public enum Direction {
        LEFT, RIGHT, STANDING
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING
    }

    public enum WalkState {
        NOT_WALKING,
        WALKING
    }

    public enum ShotState{
        SHOOTING,
        NOT_SHOOTING
    }
}
