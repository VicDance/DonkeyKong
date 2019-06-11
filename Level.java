package com.udacity.gamedev.donkeykong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.donkeykong.entities.Barrel;
import com.udacity.gamedev.donkeykong.entities.DonkeyKong;
import com.udacity.gamedev.donkeykong.entities.Door;
import com.udacity.gamedev.donkeykong.entities.Enemy;
import com.udacity.gamedev.donkeykong.entities.Fire;
import com.udacity.gamedev.donkeykong.entities.Floor;
import com.udacity.gamedev.donkeykong.entities.Ladder;
import com.udacity.gamedev.donkeykong.entities.OilBarrel;
import com.udacity.gamedev.donkeykong.entities.Peach;
import com.udacity.gamedev.donkeykong.entities.Wall;
import com.udacity.gamedev.donkeykong.util.Constants;
import com.udacity.gamedev.donkeykong.util.Enums.Direction;

public class Level {

    private Viewport viewport;
    private Peach peach;
    private Array<Floor> floors;
    private Array<Ladder> ladders;
    private DelayedRemovalArray<DonkeyKong> kong;
    private DelayedRemovalArray<Barrel> barrels;
    private Array<Wall> walls;
    private Array<OilBarrel> oilBarrels;
    private DelayedRemovalArray<Enemy> enemies;
    private DelayedRemovalArray<Fire> fires;
    private Array<Door> doors;

    public Level(Viewport viewport) {

        this.viewport = viewport;

        peach = new Peach(Constants.DEFAULT_SPAWN_LOCATION, this);

        floors = new Array<Floor>();

        ladders = new Array<Ladder>();

        barrels = new DelayedRemovalArray<Barrel>();

        kong = new DelayedRemovalArray<DonkeyKong>();

        walls = new Array<Wall>();

        oilBarrels = new Array<OilBarrel>();

        enemies = new DelayedRemovalArray<Enemy>();

        fires = new DelayedRemovalArray<Fire>();

        doors = new Array<Door>();
        //initDebugLevel();
    }

    public Peach getPeach() {
        return peach;
    }

    public void setPeach(Peach peach) {
        this.peach = peach;
    }

    public DelayedRemovalArray<DonkeyKong> getKong() {
        return kong;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public DelayedRemovalArray<Barrel> getBarrels() {
        return barrels;
    }

    public Array<Ladder> getLadders() {
        return ladders;
    }

    public Array<Floor> getFloors() {
        return floors;
    }

    public Array<Wall> getWalls() {
        return walls;
    }

    public Array<OilBarrel> getOilBarrels() {
        return oilBarrels;
    }

    public DelayedRemovalArray<Enemy> getEnemies() {
        return enemies;
    }

    public DelayedRemovalArray<Fire> getFires() {
        return fires;
    }

    public Array<Door> getDoors() {
        return doors;
    }

    public void update(float delta) {

        //peach
        peach.update(delta, floors);

        //kong
        kong.begin();

        for(int i = 0; i < kong.size; i++) {

            kong.get(i).update(delta);

            if(kong.get(i).getLifes() <= 0){

                kong.removeIndex(i);
            }
        }

        kong.end();

        //barrels
        barrels.begin();

        for (Barrel barrel : barrels) {

            barrel.update(delta);

            if (!barrel.active) {

                barrels.removeValue(barrel, false);
            }
        }

        barrels.end();

        //OilBarrels
        for (OilBarrel oilBarrel : oilBarrels) {

            oilBarrel.update(delta);
        }

        //enemies
        enemies.begin();

        for (Enemy enemy : enemies) {

            enemy.update(delta);

            if (!enemy.isActive()) {

                enemies.removeValue(enemy, false);
            }
        }

        enemies.end();

        //Fire
        fires.begin();

        for (Fire fire : fires) {

            fire.update(delta);

            if (!fire.isActive()) {

                fires.removeValue(fire, false);
            }
        }

        fires.end();

        //Doors
        for (Door door : doors) {

            door.update(delta);
        }
    }

    public void render(SpriteBatch batch) {

        for (Floor floor : floors) {

            floor.render(batch);
        }

        for (DonkeyKong donkeyKong : kong) {

            donkeyKong.render(batch);
        }

        peach.render(batch);

        for (Barrel barrel : barrels) {

            barrel.render(batch);
        }

        for(Ladder ladder : ladders){

            ladder.render(batch);
        }

        for (Wall wall : walls) {

            wall.render(batch);
        }

        for (OilBarrel oilBarrel : oilBarrels) {

            oilBarrel.render(batch);
        }

        for (Enemy enemy : enemies) {

            enemy.render(batch);
        }

        for (Fire fire : fires) {

            fire.render(batch);
        }

        for (Door door : doors) {

            door.render(batch);
        }
    }

    private void initDebugLevel() {

        peach = new Peach(new Vector2(20, 60), this);

        floors = new Array<Floor>();

        ladders = new Array<Ladder>();

        barrels = new DelayedRemovalArray<Barrel>();

        Floor kongFloor = new Floor(121, 50, 70, 20);

        kong = new DelayedRemovalArray<DonkeyKong>();

        floors.add(new Floor(0, 25, 30, 20));
        floors.add(new Floor(27, 25, 50, 20));
        floors.add(new Floor(74, 25, 50, 20));
        floors.add(new Floor(121, 25, 20, 20));
        floors.add(new Floor(138, 25, 40, 20));
        floors.add(new Floor(175, 25, 40, 20));
        floors.add(new Floor(210, 25, 30, 20));
        floors.add(kongFloor);

        ladders.add(new Ladder(30, 70, new Vector2(35, 20)));
    }

    public void spawnBarrel(Vector2 position, Direction direction) {
        barrels.add(new Barrel(this, position, direction));
    }

    public void spawnEnemy(Vector2 position, Direction direction){
        enemies.add(new Enemy(this, direction, new Vector2(position.x, position.y+10)));

    }

    public void spawnFire(Vector2 position, Direction direction) {
        fires.add(new Fire(position, direction, this));
    }
}
