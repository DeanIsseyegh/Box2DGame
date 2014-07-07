package com.mygdx.GameTwo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.GameTwo.Entities.Player.Player;
import com.mygdx.GameTwo.Managers.CollisionManager;
import com.mygdx.GameTwo.Managers.WorldController;

public class GameLevel implements Screen {
	private static final String TAG = GameLevel.class.getName();
	
	private WorldController wc;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	private CollisionManager collisionManager;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	private Player player;
	
	public GameLevel(WorldController wc){
		this.wc = wc;
	}
	
	@Override
	public void show() {
		collisionManager = wc.getCollisionManager();
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, MainGame.V_WIDTH,  MainGame.V_HEIGHT);
		
		initPlayer();
		loadMap();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		
		tmRenderer.setView(cam);
		tmRenderer.render();
		
		collisionManager.handle();
		
		batch.begin();
		player.update(deltaTime, batch);
		batch.end();

	}
	
	private void initPlayer() {
		Vector2 startPos = new Vector2(50, 70);
		player = new Player(startPos, wc);
	}
	
	private void loadMap() {
		tileMap = new TmxMapLoader().load("maps/Map1.tmx");
		wc.setTiledMap(tileMap);
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
	}


	@Override
	public void dispose() {}
	
	@Override
	public void resize(int width, int height) {}
	
	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
