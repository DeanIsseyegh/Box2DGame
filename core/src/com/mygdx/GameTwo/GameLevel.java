package com.mygdx.GameTwo;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.GameTwo.Entities.IEntity;
import com.mygdx.GameTwo.Entities.Enemy1.Enemy1;
import com.mygdx.GameTwo.Entities.Player.Player;
import com.mygdx.GameTwo.Entities.TiledMapItems.Coin;
import com.mygdx.GameTwo.Entities.TiledMapItems.ITiledMapItem;
import com.mygdx.GameTwo.Managers.CameraHelper;
import com.mygdx.GameTwo.Managers.CollisionManager;
import com.mygdx.GameTwo.Managers.WorldController;

public class GameLevel implements Screen {
	private static final String TAG = GameLevel.class.getName();
	static final int SCALE = 2;
	public static final int WIDTH  = 480 * SCALE;
    public static final int HEIGHT = 320 * SCALE;
	
	private WorldController wc;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	private CollisionManager collisionManager;
	private CameraHelper camHelper;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	private Player player;
	private IEntity enemy1;
	
	public GameLevel(WorldController wc){
		this.wc = wc;
	}
	
	@Override
	public void show() {
		collisionManager = wc.getCollisionManager();
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		camHelper = new CameraHelper(cam, wc);
		wc.setCameraHelper(camHelper);
		wc.getInputManager().setControls();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		loadMap();
		initPlayer();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime = clampDeltaTime(deltaTime);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		tmRenderer.setView(cam);
		tmRenderer.render();
		collisionManager.handle(player);
		batch.begin();
		player.update(deltaTime, batch);
		enemy1.update(deltaTime, batch);
		batch.end();
	}
	
	private float clampDeltaTime(float deltaTime){
		float max = 1 / 25f;
		deltaTime = deltaTime > max ? max : deltaTime;
		return deltaTime;
	}
	
	private void initPlayer() {
		Vector2 startPos = new Vector2(100, 300);
		player = new Player(startPos, wc);
		startPos = new Vector2(300, 300);
		enemy1 = new Enemy1(startPos, wc);
	}
	
	private void loadMap() {
		tileMap = new TmxMapLoader().load("maps/Map1.tmx");
		wc.setTiledMap(tileMap);
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
		initAnimatedCoinTiles();
	}
	
	private void initAnimatedCoinTiles(){
		Array<StaticTiledMapTile> frameTiles = new Array<>(4);
		Iterator<TiledMapTile> tiles = tileMap.getTileSets().getTileSet("coins").iterator();
		while (tiles.hasNext()) {
			TiledMapTile tile = tiles.next();
			if (tile.getProperties().containsKey("animation") && tile.getProperties().get("animation", String.class).equals("coin")){
				frameTiles.add((StaticTiledMapTile) tile);
			}
			
		}
		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(0.3f, frameTiles);
		for (TiledMapTile tile : frameTiles) {
			animatedTile.getProperties().putAll(tile.getProperties());
		}
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("Items");
		
		Array<ITiledMapItem> coins = new Array<>();
		for (int x = 0; x < layer.getWidth(); x++){
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x,y);
				if (cell != null){
					if (cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("coin")){
						cell.setTile(animatedTile);
						ITiledMapItem coin = new Coin(x, y, cell, layer.getTileWidth(), layer.getTileHeight());
						coins.add(coin);
					}
				}
				wc.getCollisionManager().hookCoins(coins);
			}
		}
	}


	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
		tmRenderer.dispose();
		tileMap.dispose();
	}
	
	@Override
	public void resize(int width, int height) {

	} 
	
	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
