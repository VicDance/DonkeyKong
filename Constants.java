package com.udacity.gamedev.donkeykong.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    // World/Camera
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final float WORLD_SIZE = 160;
    public static final float KILL_PLANE = -200;
    public static final float GRAVITY = 10;
    public static final float CHASE_CAM_MOVE_SPEED = WORLD_SIZE;
    public static final String TEXTURE_ATLAS = "images/donkey.pack.atlas";

    //InitialScreen
    public static final Color START_COLOR = new Color(Color.WHITE);
    public static final int START_BUBBLE_RADIUS = 35;
    public static final float START_LABEL_SCALE = 1.5f;
    public static final float START_WORLD_SIZE = 480.0f;
    public static final Vector2 START_CENTER = new Vector2(START_WORLD_SIZE / 2, START_WORLD_SIZE / 2);
    public static final Vector2 BUTTON_START_CENTER = new Vector2(START_WORLD_SIZE / 2, (START_WORLD_SIZE / 2)-120);
    public static final String START_LABEL = "Start";
    public static final String DONKEY_KONG_LABEL = "DONKEY KONG";

    //Peach
    public static final String JUMPING_RIGHT = "peach-up-right";
    public static final String JUMPING_LEFT = "peach-up-left";
    public static final String WALKING_RIGHT = "peach-walk-right";
    public static final String WALKING_LEFT = "peach-walk-left";
    public static final String ATTACKING_RIGHT = "peach-attack-right";
    public static final String ATTACKING_LEFT = "peach-attack-left";
    public static final String DUCKING_RIGHT = "peach-down-right";
    public static final String DUCKING_LEFT = "peach-down-left";

    public static final int INITIAL_LIVES = 3;

    public static final float PEACH_STANCE_WIDTH = 10.0f;
    public static final float PEACH_EYE_HEIGHT = 16.0f;
    public static final float PEACH_HEIGHT = 23.0f;
    public static final Vector2 PEACH_EYE_POSITION = new Vector2(16, 24);

    public static final float PEACH_MOVE_SPEED = 100;
    public static final float MAX_JUMP_DURATION = 0.1f;
    public static final float JUMP_SPEED = 200;
    public static final float DUCK_LOOP_DURATION = 0.25f;

    public static final Vector2 VELOCITY  = new Vector2(200, 200);

    public static final Vector2 DEFAULT_SPAWN_LOCATION = new Vector2(100, 100);

    public static final Vector2 PEACH_CANNON_OFFSET = new Vector2(12, -7);

    //DonkeyKong
    public static final String KONG_STANDING = "kong-standing";
    public static final String KONG_WALKING_RIGHT = "kong-walk-right";
    public static final String KONG_WALKING_LEFT = "kong-walk-left";
    public static final Vector2 KONG_CENTER = new Vector2(14, 22);
    public static final float KONG_MOVEMENT_SPEED = 10;

    public static final float KONG_STANCE_WIDTH = 38.0f;
    public static final float KONG_EYE_HEIGHT = 32.0f;
    public static final float KONG_HEIGHT = 30.0f;
    public static final float KONG_WIDTH = 30.0f;
    public static final Vector2 KONG_EYE_POSITION = new Vector2(50, 280);

    public static final int KONG_LIFES = 10;

    public static final float KONG_FRAME_DURATION = 0.8f;

    public static final Vector2 KONG_OFFSET = new Vector2(12, -20);

    //Wall
    public static final String WALL_SPRITE = "wall";
    public static final int WALL_EDGE = 4;

    //Exit door
    public static final String EXIT_DOOR = "door";
    public static final Vector2 EXIT_DOOR_CENTER = new Vector2(31, 31);
    public static  final float EXIT_DOOR_WIDTH = 37;
    public static  final float EXIT_DOOR_HEIGHT = 52;

    //Barrel
    public static final String FRONT_BARREL = "front-barrel";
    public static final String BARREL = "barrel";
    public static final float BARREL_MOVE_SPEED = 75;
    public static final Vector2 BARREL_CENTER = new Vector2(3, 2);
    public static final float BARREL_HEIGHT = 5.0f;

    //Shoot
    public static final String ATTACK_LEFT = "attack-left";
    public static final String ATTACK_RIGHT = "attack-right";
    public static final float ATTACK_MOVE_SPEED = 100;
    public static final float FIRE_WIDTH = 24;
    public static final Vector2 FIRE_CENTER = new Vector2(3, 2);

    // Floor
    public static final String FLOOR_SPRITE = "floor";
    public static final int FLOOR_EDGE = 4;

    //Ladder
    public static final String LADDER_SPRITE = "ladder";
    public static final int LADDER_EDGE = 0;
    public static final float LADDER_HEIGHT = 120;
    public static final float LADDER_WIDTH = 40;
    public static final Vector2 LADDER_CENTER = new Vector2(31, 0);

    //OilBarrel
    public static final String OIL_BARREL_SPRITE = "oil";
    public static final Vector2 OIL_BARREL_CENTER = new Vector2(26, 0);

    // Enemy
    public static final String ENEMY_LEFT = "enemy-left";
    public static final String ENEMY_RIGHT = "enemy-right";
    public static final float ENEMY_MOVEMENT_SPEED = 40;
    public static final float ENEMY_BOB_PERIOD = 3.0f;
    public static final Vector2 ENEMY_CENTER = new Vector2(14, 22);
    public static final float ENEMY_COLLISION_RADIUS = 15;
    public static final float ENEMY_BOB_AMPLITUDE = 2;

    // Level Loading
    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_FILE_EXTENSION = "dt";
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "There was a problem loading the level.";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";
    public static final String LEVEL_IDENTIFIER_KEY = "itemIdentifier";
    public static final String LEVEL_ENEMY_KONG_TAG = "Kong";
    public static final String[] LEVELS = {"levels/Level1.dt", "levels/Level2.dt"};

    //HUD
    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 20;
}
