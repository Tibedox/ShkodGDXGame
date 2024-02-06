package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

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
	EnemyShip[] enemyShips = new EnemyShip[10];
	Shot shot;
	Ship ship;
	
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

		shot = new Shot(ship.x, ship.y);

		for (int i = 0; i < enemyShips.length; i++) {
			enemyShips[i] = new EnemyShip();
		}
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
		for (int i = 0; i < enemyShips.length; i++) {
			enemyShips[i].move();
		}
		shot.move();
		ship.move();

		// отрисовка
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < stars.length; i++) {
			batch.draw(imgStars, stars[i].x, stars[i].y, stars[i].width, stars[i].height);
		}
		for (int i = 0; i < enemyShips.length; i++) {
			batch.draw(imgEnemyShip, enemyShips[i].getX(), enemyShips[i].getY(), enemyShips[i].width, enemyShips[i].height);
		}
		batch.draw(imgShot, shot.getX(), shot.getY(), shot.width, shot.height);
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
}
