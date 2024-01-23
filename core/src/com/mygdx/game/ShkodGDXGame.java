package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class ShkodGDXGame extends ApplicationAdapter {
	public static final float SCR_WIDTH = 900, SCR_HEIGHT = 1600;

	SpriteBatch batch;
	OrthographicCamera camera;

	Texture img;
	Texture imgBackGround;

	int nLeafs = 5500; // количество листьев
	Leaf[] leaf = new Leaf[nLeafs];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);

		img = new Texture("leaf.png");
		imgBackGround = new Texture("autumnforest.png");

		for (int i = 0; i < nLeafs; i++) {
			leaf[i] = new Leaf();
		}
	}

	@Override
	public void render () {
		for (int i = 0; i < nLeafs; i++) {
			leaf[i].move();
		}

		ScreenUtils.clear(0.5f, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
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
