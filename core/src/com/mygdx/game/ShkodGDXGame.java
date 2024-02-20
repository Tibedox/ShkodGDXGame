package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class ShkodGDXGame extends ApplicationAdapter {
	public static final float SCR_WIDTH = 900, SCR_HEIGHT = 1700;

	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;

	Texture imgEnemyShip;
	Texture imgShip;
	Texture imgStars;
	Texture imgShot;

	Stars[] stars = new Stars[2];
	Array<Enemy> enemies = new Array<>();
	Array<Shot> shots = new Array<>();
	Ship ship;

	long timeLastShot, timeShotInterval = 800;
	long timeSpawnLastEnemy, timeSpawnEnemyInterval = 1600;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();

		imgEnemyShip = new Texture("enemy1.png");
		imgStars = new Texture("stars.png");
		imgShip = new Texture("ship.png");
		imgShot = new Texture("shipshot.png");

		stars[0] = new Stars(0);
		stars[1] = new Stars(SCR_HEIGHT);

		ship = new Ship();
	}

	@Override
	public void render () {
		// касания
		if(Gdx.input.isTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);

			ship.hit(touch.x);
		}

		// события
		for (int i = 0; i < stars.length; i++) {
			stars[i].move();
		}
		for (int i = 0; i < enemies.size; i++) {
			enemies.get(i).move();
			if(enemies.get(i).outOfScreen()){
				enemies.removeIndex(i);
				continue;
			}
			if(enemies.get(i).overlap(ship)){
				enemies.removeIndex(i);
			}
		}
		for (int i = 0; i < shots.size; i++) {
			shots.get(i).move();
			if(shots.get(i).outOfScreen()) {
				shots.removeIndex(i);
				break;
			}
			for (int j = 0; j < enemies.size; j++) {
				if(shots.get(i).overlap(enemies.get(j))){
					enemies.removeIndex(j);
					shots.removeIndex(i);
					break;
				}
			}
		}
		ship.move();
		spawnShots();
		spawnEnemies();

		// отрисовка
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < stars.length; i++) {
			batch.draw(imgStars, stars[i].x, stars[i].y, stars[i].width, stars[i].height);
		}
		for (int i = 0; i < enemies.size; i++) {
			batch.draw(imgEnemyShip, enemies.get(i).getX(), enemies.get(i).getY(), enemies.get(i).width, enemies.get(i).height);
		}
		for (int i = 0; i < shots.size; i++) {
			batch.draw(imgShot, shots.get(i).getX(), shots.get(i).getY(), shots.get(i).width, shots.get(i).height);
		}
		batch.draw(imgShip, ship.getX(), ship.getY(), ship.width, ship.height);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgEnemyShip.dispose();
		imgStars.dispose();
		imgShip.dispose();
		imgShot.dispose();
	}

	void spawnShots() {
		if(TimeUtils.millis() > timeLastShot+timeShotInterval) {
			shots.add(new Shot(ship.x, ship.y));
			timeLastShot = TimeUtils.millis();
		}
	}

	void spawnEnemies() {
		if(TimeUtils.millis() > timeSpawnLastEnemy+timeSpawnEnemyInterval) {
			enemies.add(new Enemy());
			timeSpawnLastEnemy = TimeUtils.millis();
		}
	}
}
