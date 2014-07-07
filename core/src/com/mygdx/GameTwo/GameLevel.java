package com.mygdx.GameTwo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.GameTwo.Entities.Player.Player;
import com.mygdx.GameTwo.Managers.CameraHelper;
import com.mygdx.GameTwo.Managers.CollisionManager;
import com.mygdx.GameTwo.Managers.WorldController;

public class GameLevel implements Screen {
	private static final String TAG = GameLevel.class.getName();
	
	private WorldController wc;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	private CollisionManager collisionManager;
	private CameraHelper camHelper;
	
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
		camHelper = new CameraHelper(cam, wc);
		wc.setCameraHelper(camHelper);
		wc.getInputManager().setControls();
		
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
		Vector2 startPos = new Vector2(100, 100);
		player = new Player(startPos, wc);
	}
	
	private void loadMap() {
		tileMap = new TmxMapLoader().load("maps/Map1.tmx");
		wc.setTiledMap(tileMap);
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
	}


	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
		tmRenderer.dispose();
		tileMap.dispose();
	}
	
	@Override
	public void resize(int width, int height) {} 
	
	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
