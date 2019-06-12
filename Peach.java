package com.udacity.gamedev.donkeykong.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.util.Assets;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums.*;
import com.udacity.gamedev.donkeykong.util.Utils;

public class Peach {

    private Level level;

    private Vector2 spawnLocation;
    private Vector2 position;
    private Vector2 lastFramePosition;
    private Vector2 velocity;

    private Direction facing;
    private JumpState jumpState;
    private WalkState walkState;
    private ShotState shotState;

    private long walkStartTime;
    private long jumpStartTime;

    Rectangle kongBounds;
    Rectangle fireBounds;

    private boolean fire = false;

    private Music shotSound;
    private Music plafSound;
    private Music plopSound;
    private Sound kongSound;

    private int lives;

    private boolean lifeLevel;

    private boolean backToLevel;

    public float offset = 0;

    private boolean rightButtonPressed;
    private boolean leftButtonPressed;
    private boolean upButtonPressed;
    private boolean downButtonPressed;
    private boolean jumpButtonPressed;
    private boolean shootButtonPressed;

    public Peach(Vector2 spawnLocation, Level level) {

        this.spawnLocation = spawnLocation;
        this.level = level;

        this.position = new Vector2();
        this.lastFramePosition = new Vector2();
        this.velocity = new Vector2();

        init();
    }

    public Peach(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Direction getFacing() {
        return facing;
    }

    public int getLives() {
        return lives;
    }

    public boolean isLifeLevel() {
        return lifeLevel;
    }

    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
    }

    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
    }

    public void setUpButtonPressed(boolean upButtonPressed) {
        this.upButtonPressed = upButtonPressed;
    }

    public void setDownButtonPressed(boolean downButtonPressed) {
        this.downButtonPressed = downButtonPressed;
    }

    public void setJumpButtonPressed(boolean jumpButtonPressed) {
        this.jumpButtonPressed = jumpButtonPressed;
    }

    public void setShootButtonPressed(boolean shootButtonPressed) {
        this.shootButtonPressed = shootButtonPressed;
    }

    public boolean isBackToLevel() {
        return backToLevel;
    }

    public void init() {

        this.lives = Constants.INITIAL_LIVES;
        this.lifeLevel = false;
        this.backToLevel = false;
        respawn();
    }

    private void respawn() {

        position.set(spawnLocation);

        lastFramePosition.set(spawnLocation);

        jumpState = JumpState.FALLING;

        facing = Direction.RIGHT;

        walkState = WalkState.NOT_WALKING;

        shotState = ShotState.NOT_SHOOTING;

        velocity.setZero();
    }

    public void update(float delta, Array<Floor> floors) {

        lastFramePosition.set(position);

        velocity.y -= Constants.GRAVITY;

        position.mulAdd(velocity, delta);

        if (position.y < Constants.KILL_PLANE) {

            respawn();

            lives--;
        }

        if (jumpState != JumpState.JUMPING) {

            if (jumpState != JumpState.RECOILING) {

                jumpState = JumpState.FALLING;
            }

            for (Floor floor : floors) {

                if (landedOnPlatform(floor)) {

                    jumpState = JumpState.GROUNDED;

                    velocity.y = 0;
                    velocity.x = 0;

                    position.y = floor.top + Constants.PEACH_EYE_HEIGHT;
                }
            }
        }

        //collision
        Rectangle peachBounds = new Rectangle(
                position.x - Constants.PEACH_STANCE_WIDTH / 2,
                position.y - Constants.PEACH_EYE_HEIGHT,
                Constants.PEACH_STANCE_WIDTH,
                Constants.PEACH_HEIGHT);

        for (int i = 0; i < level.getFires().size; i++) {

            fireBounds = new Rectangle(
                    level.getFires().get(i).getPosition().x,
                    level.getFires().get(i).getPosition().y,
                    Constants.FIRE_WIDTH,
                    Constants.FIRE_WIDTH
            );

            fire = true;
        }

        for (int i = 0; i < level.getKong().size; i++) {

            kongBounds = new Rectangle(

                    level.getKong().get(i).getPosition().x + Constants.KONG_WIDTH,
                    level.getKong().get(i).getPosition().y + Constants.KONG_HEIGHT,
                    Constants.KONG_WIDTH,
                    Constants.KONG_HEIGHT
            );

            if (peachBounds.overlaps(kongBounds)) {
                //System.out.println("entra");
                if (position.x < level.getKong().get(i).getPosition().x) {
                    recoilFromEnemy(Direction.LEFT);
                    lives--;
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                    lives--;
                }
            }
        }


        for (int i = 0; i < level.getBarrels().size; i++) {

            if (peachBounds.overlaps(level.getBarrels().get(i).getBarrelBounds())) {

                if (position.x < level.getBarrels().get(i).getPosition().x) {
                    recoilFromEnemy(Direction.LEFT);
                    lives--;
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                    lives--;
                }

            }
        }

        for (int i = 0; i < level.getLadders().size; i++) {

            Rectangle ladderBounds = new Rectangle(
                    level.getLadders().get(i).getPosition().x /*+ Constants.LADDER_WIDTH*/,
                    level.getLadders().get(i).getPosition().y /*+ Constants.LADDER_HEIGHT*/,
                    Constants.LADDER_WIDTH,
                    Constants.LADDER_HEIGHT
            );

            if (peachBounds.overlaps(ladderBounds) && Gdx.input.isKeyPressed(Input.Keys.UP) || upButtonPressed) {
                position.y += delta + 30;
            }else if(peachBounds.overlaps(ladderBounds) && Gdx.input.isKeyPressed(Input.Keys.DOWN) || downButtonPressed){
                position.y -= delta + 20;
            }
        }

        for (int i = 0; i < level.getEnemies().size; i++) {

            Rectangle enemyBounds = new Rectangle(
                    level.getEnemies().get(i).getPosition().x - 10,
                    level.getEnemies().get(i).getPosition().y,
                    Constants.ENEMY_COLLISION_RADIUS,
                    Constants.ENEMY_COLLISION_RADIUS
            );

            if (peachBounds.overlaps(enemyBounds)) {
                if (position.x < level.getEnemies().get(i).getPosition().x) {
                    recoilFromEnemy(Direction.LEFT);
                    lives--;
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                    lives--;
                }
            }

            if (fire) {
                if (fireBounds.overlaps(enemyBounds)) {
                    plopSound = Gdx.audio.newMusic(Gdx.files.internal("raw/plop.mp3"));
                    plopSound.play();

                    level.getEnemies().get(i).setActive(false);
                }
            }
        }

        for (int i = 0, j = 0; i < level.getFires().size && j < level.getKong().size; i++, j++) {
            if (level.getFires().get(i).getPosition().dst(level.getKong().get(j).getPosition()) < 50) {

                level.getFires().get(i).setActive(false);

                level.getKong().get(j).setLifes(level.getKong().get(j).getLifes() - 1);
            }
        }

        for (int i = 0; i < level.getDoors().size; i++) {

            Rectangle doorBounds = new Rectangle(
                    level.getDoors().get(i).getPosition().x,
                    level.getDoors().get(i).getPosition().y,
                    Constants.EXIT_DOOR_WIDTH,
                    Constants.EXIT_DOOR_HEIGHT
            );

            if (peachBounds.overlaps(doorBounds)) {
                lifeLevel = true;
            }
        }

        for(int i = 0; i < level.getLives().size; i++){

            Rectangle lifeBounds = new Rectangle(
                    level.getLives().get(i).getPosition().x +  Constants.LIFE_WIDTH,
                    level.getLives().get(i).getPosition().y + +  Constants.LIFE_WIDTH,
                    Constants.LIFE_WIDTH,
                    Constants.LIFE_WIDTH
            );

            if(peachBounds.overlaps(lifeBounds)){
                lives++;
                level.getLives().get(i).setActive(false);
                backToLevel = true;
            }
        }

        if (jumpState != JumpState.RECOILING) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftButtonPressed) {
                moveLeft(delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightButtonPressed) {
                moveRight(delta);
            } else {
                walkState = WalkState.NOT_WALKING;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || jumpButtonPressed) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }

        //shot
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) || shootButtonPressed) {
            shooting();
        } else {
            endShooting();
        }
    }

    boolean landedOnPlatform(Floor floor) {

        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.PEACH_EYE_HEIGHT >= floor.top &&
                position.y - Constants.PEACH_EYE_HEIGHT < floor.top) {

            float leftFoot = position.x - Constants.PEACH_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.PEACH_STANCE_WIDTH / 2;

            leftFootIn = (floor.left < leftFoot && floor.right > leftFoot);
            rightFootIn = (floor.left < rightFoot && floor.right > rightFoot);

            straddle = (floor.left > leftFoot && floor.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }

    private void moveLeft(float delta) {

        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {

            walkStartTime = TimeUtils.nanoTime();
        }

        walkState = WalkState.WALKING;

        facing = Direction.LEFT;

        position.x -= delta * Constants.PEACH_MOVE_SPEED;
    }

    private void moveRight(float delta) {

        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {

            walkStartTime = TimeUtils.nanoTime();
        }

        walkState = WalkState.WALKING;

        facing = Direction.RIGHT;

        position.x += delta * Constants.PEACH_MOVE_SPEED;
    }

    private void startJump() {

        jumpState = JumpState.JUMPING;

        jumpStartTime = TimeUtils.nanoTime();

        continueJump();
    }

    private void continueJump() {

        if (jumpState == JumpState.JUMPING) {

            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);

            if (jumpDuration < Constants.MAX_JUMP_DURATION) {

                velocity.y = Constants.JUMP_SPEED;
            } else {

                endJump();
            }
        }
    }

    private void endJump() {

        if (jumpState == JumpState.JUMPING) {

            jumpState = JumpState.FALLING;
        }
    }

    public void shooting() {
        Vector2 firePosition;

        shotState = ShotState.SHOOTING;

        if (facing == Direction.RIGHT) {
            firePosition = new Vector2(
                    position.x + Constants.PEACH_CANNON_OFFSET.x,
                    position.y + Constants.PEACH_CANNON_OFFSET.y
            );
        } else {
            firePosition = new Vector2(
                    position.x - Constants.PEACH_CANNON_OFFSET.x,
                    position.y + Constants.PEACH_CANNON_OFFSET.y
            );
        }

        shotSound = Gdx.audio.newMusic(Gdx.files.internal("raw/disparoFuego.mp3"));
        shotSound.play();

        level.spawnFire(firePosition, facing);
    }

    private void endShooting() {

        if (shotState == ShotState.SHOOTING) {
            shotState = ShotState.NOT_SHOOTING;
        }
    }

    private void recoilFromEnemy(Direction direction) {

        plafSound = Gdx.audio.newMusic(Gdx.files.internal("raw/plaf.mp3"));
        plafSound.play();

        jumpState = JumpState.RECOILING;

        velocity.y = Constants.VELOCITY.y;

        if (direction == Direction.LEFT) {
            velocity.x = -Constants.VELOCITY.x;
        } else {
            velocity.x = Constants.VELOCITY.x;
        }
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = Assets.instance.peachAssets.walkingRight;

        if (facing == Direction.RIGHT && jumpState != JumpState.GROUNDED && shotState != ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.jumpingRight;
        } else if (facing == Direction.RIGHT && walkState == WalkState.NOT_WALKING && shotState != ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.walkingRight;
        } else if (facing == Direction.RIGHT && walkState == WalkState.WALKING && shotState != ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.walkingRight;
        } else if (facing == Direction.LEFT && jumpState != JumpState.GROUNDED && shotState != ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.jumpingLeft;
        } else if (facing == Direction.LEFT && walkState == WalkState.NOT_WALKING && shotState != ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.walkingLeft;
        } else if (facing == Direction.LEFT && walkState == WalkState.WALKING && shotState != ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.walkingLeft;
        } else if (facing == Direction.RIGHT && jumpState != JumpState.GROUNDED && shotState == ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.attackingRight;
        } else if (facing == Direction.RIGHT && walkState == WalkState.NOT_WALKING && shotState == ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.attackingRight;
        } else if (facing == Direction.RIGHT && walkState == WalkState.WALKING && shotState == ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.attackingRight;
        } else if (facing == Direction.LEFT && jumpState != JumpState.GROUNDED && shotState == ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.attackingLeft;
        } else if (facing == Direction.LEFT && walkState == WalkState.NOT_WALKING && shotState == ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.attackingLeft;
        } else if (facing == Direction.LEFT && walkState == WalkState.WALKING && shotState == ShotState.SHOOTING) {
            region = Assets.instance.peachAssets.attackingLeft;
        }

        Utils.drawTextureRegion(batch, region, position, Constants.PEACH_EYE_POSITION);
    }
}
