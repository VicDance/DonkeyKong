package com.udacity.gamedev.donkeykong.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public PeachAssets peachAssets;
    public FloorAssets floorAssets;
    public DonkeyAssets donkeyAssets;
    public BarrelAssets barrelAssets;
    public FireAssets fireAssets;
    public LadderAssets ladderAssets;
    public WallAssets wallAssets;
    public OilBarrelAssets oilBarrelAssets;
    public EnemyAssets enemyAssets;
    public DoorAssets doorAssets;

    private AssetManager assetManager;


    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        peachAssets = new PeachAssets(atlas);
        floorAssets = new FloorAssets(atlas);
        donkeyAssets = new DonkeyAssets(atlas);
        barrelAssets = new BarrelAssets(atlas);
        fireAssets = new FireAssets(atlas);
        ladderAssets = new LadderAssets(atlas);
        wallAssets = new WallAssets(atlas);
        oilBarrelAssets = new OilBarrelAssets(atlas);
        enemyAssets = new EnemyAssets(atlas);
        doorAssets = new DoorAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class PeachAssets {

        public final TextureAtlas.AtlasRegion attackingLeft;
        public final TextureAtlas.AtlasRegion attackingRight;

        public final TextureAtlas.AtlasRegion walkingLeft;
        public final TextureAtlas.AtlasRegion walkingRight;

        public final TextureAtlas.AtlasRegion jumpingLeft;
        public final TextureAtlas.AtlasRegion jumpingRight;

        public PeachAssets(TextureAtlas atlas) {

            walkingLeft = atlas.findRegion(Constants.WALKING_LEFT);
            walkingRight = atlas.findRegion(Constants.WALKING_RIGHT);

            jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

            attackingLeft = atlas.findRegion(Constants.ATTACKING_LEFT);
            attackingRight = atlas.findRegion(Constants.ATTACKING_RIGHT);
        }
    }

    public class DoorAssets{

        public final TextureAtlas.AtlasRegion door;

        public  DoorAssets(TextureAtlas atlas){

            door = atlas.findRegion(Constants.EXIT_DOOR);
        }
    }

    public class FloorAssets {

        public final NinePatch floorNinePatch;

        public FloorAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.FLOOR_SPRITE);

            int edge = Constants.FLOOR_EDGE;

            floorNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class DonkeyAssets{

        public final TextureAtlas.AtlasRegion standing;
        public final TextureAtlas.AtlasRegion standingRight;
        //public final Animation donkeyKong;

        public DonkeyAssets(TextureAtlas atlas){

            standing = atlas.findRegion(Constants.KONG_STANDING);
            standingRight = atlas.findRegion(Constants.KONG_WALKING_RIGHT);

            /*Array<AtlasRegion> donkeyFrames = new Array<AtlasRegion>();
            donkeyFrames.addAll(standing, walkingRight);*/

            //donkeyKong = new Animation(Constants.KONG_FRAME_DURATION, donkeyFrames);
        }
    }

    public class BarrelAssets{

        public final TextureAtlas.AtlasRegion barrel;
        public final TextureAtlas.AtlasRegion front_barrel;

        public BarrelAssets(TextureAtlas atlas){

            barrel = atlas.findRegion(Constants.BARREL);
            front_barrel = atlas.findRegion(Constants.FRONT_BARREL);
        }
    }

    public class LadderAssets{

        public final TextureAtlas.AtlasRegion ladder;

        public LadderAssets(TextureAtlas atlas){

            ladder = atlas.findRegion(Constants.LADDER_SPRITE);
        }
    }

    public class WallAssets{

        public final NinePatch wallNinePatch;

        public WallAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.WALL_SPRITE);

            int edge = Constants.WALL_EDGE;

            wallNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class OilBarrelAssets{

        public final TextureAtlas.AtlasRegion oilBarrel;

        public OilBarrelAssets(TextureAtlas atlas){

            oilBarrel = atlas.findRegion(Constants.OIL_BARREL_SPRITE);
        }
    }

    public class FireAssets{

        public final TextureAtlas.AtlasRegion attackRight;
        public final TextureAtlas.AtlasRegion attackLeft;

        public FireAssets(TextureAtlas atlas){

            attackRight = atlas.findRegion(Constants.ATTACK_RIGHT);
            attackLeft = atlas.findRegion(Constants.ATTACK_LEFT);
        }
    }

    public class EnemyAssets {

        public final TextureAtlas.AtlasRegion enemy_left;
        public final TextureAtlas.AtlasRegion enemy_right;

        public EnemyAssets(TextureAtlas atlas) {
            enemy_left = atlas.findRegion(Constants.ENEMY_LEFT);
            enemy_right = atlas.findRegion(Constants.ENEMY_RIGHT);
        }
    }
}
