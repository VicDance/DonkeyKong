package com.udacity.gamedev.donkeykong.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.donkeykong.Level;
import com.udacity.gamedev.donkeykong.entities.DonkeyKong;
import com.udacity.gamedev.donkeykong.entities.Door;
import com.udacity.gamedev.donkeykong.entities.Floor;
import com.udacity.gamedev.donkeykong.entities.Ladder;
import com.udacity.gamedev.donkeykong.entities.OilBarrel;
import com.udacity.gamedev.donkeykong.entities.Peach;
import com.udacity.gamedev.donkeykong.entities.Wall;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Comparator;


public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();


    public static Level load(String levelName, Viewport viewport) {

        String path = levelName;

        Level level = new Level(viewport);

        //System.out.println(path);

        FileHandle file = Gdx.files.internal(path);

        JSONParser parser = new JSONParser();

        //System.out.println(file.reader());

        try {
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());

            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);

            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);

            JSONArray objects = (JSONArray) composite.get(Constants.LEVEL_IMAGES);

            loadFloors(platforms, level);

            loadImages(objects, level);
        } catch (Exception ex) {
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }

    private static float safeGetFloat(JSONObject object, String key){
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }


    private static void loadFloors(JSONArray array, Level level) {

        Array<Floor> floorArray = new Array<Floor>();
        Array<Wall> wallArray = new Array<Wall>();

        for (Object object : array) {
            final JSONObject item = (JSONObject) object;

            final float x = safeGetFloat(item, Constants.LEVEL_X_KEY);

            final float y = safeGetFloat(item, Constants.LEVEL_Y_KEY);

            final float width = ((Number) item.get(Constants.LEVEL_WIDTH_KEY)).floatValue();


            final float height = ((Number) item.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();

            if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.FLOOR_SPRITE)){

                final Floor floor = new Floor(x, y + height, width, height);

                floorArray.add(floor);

                final String identifier = (String) item.get(Constants.LEVEL_IDENTIFIER_KEY);

                if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_KONG_TAG)) {
                    //Gdx.app.log(TAG, "Loaded an enemy on that platform");

                    final DonkeyKong kong = new DonkeyKong(level);

                    level.getKong().add(kong);
                }
            }else{

                final Wall wall = new Wall(x, y + height, width, height);

                wallArray.add(wall);
            }
        }

        floorArray.sort(new Comparator<Floor>() {
            @Override
            public int compare(Floor o1, Floor o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });

        wallArray.sort(new Comparator<Wall>() {
            @Override
            public int compare(Wall o1, Wall o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });

        level.getFloors().addAll(floorArray);
        level.getWalls().addAll(wallArray);
    }

    private static void loadImages(JSONArray array, Level level) {

        for (Object object : array) {
            JSONObject item = (JSONObject) object;

            Vector2 lowerLeftCorner = new Vector2();

            final float x = safeGetFloat(item, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(item, Constants.LEVEL_Y_KEY);

            lowerLeftCorner = new Vector2(x, y);

            if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.WALKING_RIGHT)) {

                final Vector2 peachPosition = lowerLeftCorner.add(Constants.PEACH_EYE_POSITION);

                //Gdx.app.log(TAG, "Loaded Peach at " + peachPosition);

                level.setPeach(new Peach(peachPosition, level));
            } else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.LADDER_SPRITE)) {

                final Vector2 ladderPosition = lowerLeftCorner.add(Constants.LADDER_CENTER);

                //Gdx.app.log(TAG, "Loaded the exit portal at " + ladderPosition);

                level.getLadders().add(new Ladder(40, 120, ladderPosition));
            }else if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.OIL_BARREL_SPRITE)){
                //System.out.println("entra");
                final Vector2 oilBarrelPosition = lowerLeftCorner.add(Constants.OIL_BARREL_CENTER);

                level.getOilBarrels().add(new OilBarrel(oilBarrelPosition, level));
            }else if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.EXIT_DOOR)){

                final Vector2 doorPosition = lowerLeftCorner.add(Constants.EXIT_DOOR_CENTER);

                level.getDoors().add(new Door(doorPosition, level));
            }
        }
    }
}
