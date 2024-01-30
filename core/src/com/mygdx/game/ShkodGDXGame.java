package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShkodGDXGame extends ApplicationAdapter {
	public static final float SCR_WIDTH = 900, SCR_HEIGHT = 1700;

	SpriteBatch batch;
	OrthographicCamera camera;

	Texture img;
	Texture imgStars;

	Stars[] stars = new Stars[2];
	int nLeafs = 50; // количество листьев
	Leaf[] leaf = new Leaf[nLeafs];

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);

		img = new Texture("leaf.png");
		imgStars = new Texture("stars.png");

		stars[0] = new Stars(0);
		stars[1] = new Stars(SCR_HEIGHT);

		for (int i = 0; i < nLeafs; i++) {
			leaf[i] = new Leaf();
		}
	}

	@Override
	public void render () {
		// события
		for (int i = 0; i < stars.length; i++) {
			stars[i].move();
		}
		for (int i = 0; i < nLeafs; i++) {
			leaf[i].move();
		}

		// отрисовка
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < stars.length; i++) {
			batch.draw(imgStars, stars[i].x, stars[i].y, stars[i].width, stars[i].height);
		}

		for (int i = 0; i < nLeafs; i++) {
			batch.draw(img, leaf[i].x, leaf[i].y, leaf[i].width, leaf[i].height);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
