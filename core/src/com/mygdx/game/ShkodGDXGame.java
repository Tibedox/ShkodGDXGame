package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class ShkodGDXGame extends ApplicationAdapter {
	// константы
	public static final float SCR_WIDTH = 900, SCR_HEIGHT = 1700;
	public static final int SHIP_LIVES = 1;

	// системные объекты
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	InputKeyboard keyboard;

	// ресурсы
	BitmapFont fontSmall;
	BitmapFont fontLarge;
	Texture imgAtlasShips;
	Texture imgEnemy;
	Texture imgStars;
	Texture imgShot;
	TextureRegion[] imgShip = new TextureRegion[12];
	TextureRegion[][] imgFragment = new TextureRegion[2][16];
	Sound sndShot;
	Sound sndExplosion;

	ShkodButton btnRestart;
	ShkodButton btnExit;

	// игровые объекты
	Player[] players = new Player[11];
	Stars[] stars = new Stars[2];
	Array<Enemy> enemies = new Array<>();
	Array<Shot> shots = new Array<>();
	Array<Fragment> fragments = new Array<>();
	Ship ship;

	// игровые переменные
	int numFragments = 40;
	int kills;
	String playerName = "BigBoy";
	boolean isGameOver;
	boolean isKeyboardUse;

	// временнЫе переменные
	long timeLastShot, timeShotInterval = 800;
	long timeSpawnLastEnemy, timeSpawnEnemyInterval = 1600;
	long timeLastSpeedUp, timeEnemiesSpeedUp = 10000;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();

		// создаём и загружаем ресурсы
		fontSmall = new BitmapFont(Gdx.files.internal("dscrystal70.fnt"));
		fontLarge = new BitmapFont(Gdx.files.internal("dscrystal100.fnt"));
		imgAtlasShips = new Texture("ships_atlas3.png");
		imgEnemy = new Texture("enemy1.png");
		imgStars = new Texture("stars.png");
		imgShot = new Texture("shipshot.png");

		for (int i = 0; i < imgShip.length; i++) {
			if(i<7) {
				imgShip[i] = new TextureRegion(imgAtlasShips, i * 400, 0, 400, 400);
			} else {
				imgShip[i] = new TextureRegion(imgAtlasShips, (12-i)*400, 0, 400, 400);
			}
		}
		for (int i = 0; i < imgFragment[0].length; i++) {
			imgFragment[0][i] = new TextureRegion(imgShip[0], i%4*100, i/4*100, 100, 100);
		}
		for (int i = 0; i < imgFragment[1].length; i++) {
			imgFragment[1][i] = new TextureRegion(imgEnemy, i%4*100, i/4*100, 100, 100);
		}

		sndShot = Gdx.audio.newSound(Gdx.files.internal("blaster.wav"));
		sndExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));

		keyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT/2, 12);

		// создаём кнопки
		btnRestart = new ShkodButton("Restart", 300, fontSmall);
		btnExit = new ShkodButton("Exit", 200, fontSmall);

		// создаём игровые объекты
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("Noname", 0);
		}
		loadRecords();

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
		if(Gdx.input.justTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);

			if(isKeyboardUse) {
				if (keyboard.endOfEdit(touch.x, touch.y)) {
					playerName = keyboard.getText();
					writeRecords();
				}
			} else {
				if (isGameOver & btnExit.hit(touch.x, touch.y)) {
					Gdx.app.exit();
				}
				if (isGameOver & btnRestart.hit(touch.x, touch.y)) {
					gameRestart();
				}
			}
		}

		// события
		for (int i = 0; i < stars.length; i++) {
			stars[i].move();
		}
		for (int i = 0; i < fragments.size; i++) {
			fragments.get(i).move();
		}
		for (int i = 0; i < enemies.size; i++) {
			enemies.get(i).move();
			if(enemies.get(i).outOfScreen()){
				enemies.removeIndex(i);
				if(ship.isAlive) {
					killShip();
				}
				continue;
			}
			if(ship.isAlive && enemies.get(i).overlap(ship)){
				enemies.removeIndex(i);
				killShip();
				sndExplosion.play();
			}
		}
		for (int i = 0; i < shots.size; i++) {
			shots.get(i).move();
			if(shots.get(i).outOfScreen()) {
				shots.removeIndex(i);
				continue;
			}
			for (int j = 0; j < enemies.size; j++) {
				if(shots.get(i).overlap(enemies.get(j))){
					spawnFragments(enemies.get(j));
					enemies.removeIndex(j);
					shots.removeIndex(i);
					sndExplosion.play();
					kills++;
					break;
				}
			}
		}
		if(ship.isAlive) {
			ship.move();
			spawnShots();
			spawnEnemies();
			speedUp();
		} else if(shots.size == 0 & enemies.size == 0 & !isGameOver){
			ship.respawn();
		}

		// отрисовка всего
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < stars.length; i++) {
			batch.draw(imgStars, stars[i].x, stars[i].y, stars[i].width, stars[i].height);
		}
		for (int i = 0; i < fragments.size; i++) {
			batch.draw(imgFragment[fragments.get(i).type][fragments.get(i).img], fragments.get(i).x, fragments.get(i).y,
					fragments.get(i).width/2, fragments.get(i).height/2, fragments.get(i).width, fragments.get(i).height,
					1, 1, fragments.get(i).rotation);
		}
		for (int i = 0; i < enemies.size; i++) {
			batch.draw(imgEnemy, enemies.get(i).getX(), enemies.get(i).getY(), enemies.get(i).width, enemies.get(i).height);
		}
		for (int i = 0; i < shots.size; i++) {
			batch.draw(imgShot, shots.get(i).getX(), shots.get(i).getY(), shots.get(i).width, shots.get(i).height);
		}
		if(ship.isAlive) {
			batch.draw(imgShip[ship.phase], ship.getX(), ship.getY(), ship.width, ship.height);
		}
		fontSmall.draw(batch, "KILLS: "+kills, 10, SCR_HEIGHT-10);
		for (int i = 0; i < ship.lives; i++) {
			batch.draw(imgShip[0], SCR_WIDTH-70*i-70, SCR_HEIGHT-70, 60, 60);
		}
		if(isGameOver) {
			fontLarge.draw(batch, "GAME OVER", 0, SCR_HEIGHT/4*3, SCR_WIDTH, Align.center, true);
			for (int i = 0; i < players.length-1; i++) {
				fontSmall.draw(batch, i+1+" "+players[i].name, 150, 1100-70*i);
				String points = points(fontSmall, i+1+" "+players[i].name, ""+players[i].score, SCR_WIDTH-300);
				fontSmall.draw(batch, points+players[i].score, 150, 1100-70*i, SCR_WIDTH-300, Align.right, true);
			}
			fontSmall.draw(batch, btnRestart.text, btnRestart.x, btnRestart.y);
			fontSmall.draw(batch, btnExit.text, btnExit.x, btnExit.y);
			if(isKeyboardUse) keyboard.draw(batch);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgEnemy.dispose();
		imgStars.dispose();
		imgAtlasShips.dispose();
		imgShot.dispose();
		sndShot.dispose();
		sndExplosion.dispose();
		fontSmall.dispose();
		fontLarge.dispose();
		keyboard.dispose();
	}

	void spawnShots() {
		if(TimeUtils.millis() > timeLastShot+timeShotInterval) {
			shots.add(new Shot(ship.x, ship.y));
			timeLastShot = TimeUtils.millis();
			sndShot.play(0.2f);
		}
	}

	void spawnEnemies() {
		if(TimeUtils.millis() > timeSpawnLastEnemy+timeSpawnEnemyInterval) {
			enemies.add(new Enemy());
			timeSpawnLastEnemy = TimeUtils.millis();
		}
	}

	void spawnFragments(SpaceObject object) {
		for (int i = 0; i < numFragments; i++) {
			fragments.add(new Fragment(object));
		}
	}

	void killShip() {
		sndExplosion.play();
		ship.lives--;
		ship.isAlive = false;
		if(ship.lives == 0) {
			gameOver();
		}
		spawnFragments(ship);
	}

	void speedUp() {
		if(timeLastSpeedUp + timeEnemiesSpeedUp < TimeUtils.millis()){
			timeLastSpeedUp = TimeUtils.millis();
			Enemy.scaleSpeed+=0.2f;
		}
	}

	void gameOver() {
		isGameOver = true;
		isKeyboardUse = true;
	}

	void writeRecords() {
		players[players.length-1].name = playerName;
		players[players.length-1].score = kills;
		sortRecords();
		saveRecords();
		isKeyboardUse = false;
	}

	void gameRestart() {
		isGameOver = false;
		enemies.clear();
		shots.clear();
		fragments.clear();
		ship.lives = SHIP_LIVES;
		ship.respawn();
		kills = 0;
		// засыпаем на 1 с
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	String points(BitmapFont font, String textLeft, String textRight, float width) {
		GlyphLayout glLeft = new GlyphLayout(font, textLeft);
		GlyphLayout glRight = new GlyphLayout(font, textRight);
		float allPointsWidth = width - glLeft.width - glRight.width;
		GlyphLayout glPoint = new GlyphLayout(font, ".");
		int nPoints = (int) (allPointsWidth/glPoint.width);
		String s = "";
		for (int i = 0; i < nPoints/2; i++) {
			s += '.';
		}
		return s;
	}
	
	void sortRecords(){
		for (int j = 0; j < players.length-1; j++) {
			for (int i = 0; i < players.length-1-j; i++) {
				if(players[i].score<players[i+1].score){
					Player c = players[i];
					players[i] = players[i+1];
					players[i+1] = c;
				}
			}
		}
	}

	void saveRecords(){
		Preferences preferences = Gdx.app.getPreferences("records");
		for (int i = 0; i < players.length; i++) {
			preferences.putString("player"+i, players[i].name);
			preferences.putInteger("score"+i, players[i].score);
		}
		preferences.flush();
	}

	void loadRecords(){
		Preferences preferences = Gdx.app.getPreferences("records");
		for (int i = 0; i < players.length; i++) {
			if(preferences.contains("player"+i)) players[i].name = preferences.getString("player"+i);
			if(preferences.contains("score"+i)) players[i].score = preferences.getInteger("score"+i);
		}
	}
}
