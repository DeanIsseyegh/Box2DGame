package com.mygdx.GameTwo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameTwo extends ApplicationAdapter {
	public static final String TITLE = "The Defender";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;
	public static final float PPM = 100;
	
	private SpriteBatch batch;
	private Texture img;
	private OrthographicCamera cam;
	private OrthographicCamera box2Dcam;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	private World world;
	private Box2DDebugRenderer b2dRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH / 100,  V_HEIGHT / 100);

		// Box2D Stuff
		b2dRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -7f), true);
		initPlayer();
		loadMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		cam.zoom += 0.01f;
		
		world.step(Gdx.graphics.getDeltaTime(), 1, 1);
		b2dRenderer.render(world, cam.combined);
		tmRenderer.setView(cam);
		tmRenderer.render();
		
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
	
	private void initPlayer() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		bdef.position.set(300 / PPM, 300 / PPM);
		bdef.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50 / PPM, 50 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = 1;
		fdef.filter.maskBits = -1;
		world.createBody(bdef).createFixture(fdef);
	}
	
	private void loadMap() {
		tileMap = new TmxMapLoader().load("res/maps/Map1.tmx");
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("Platforms");
		
		float tileSize = layer.getTileHeight();
		
		for (int row = 0; row < layer.getTileHeight(); row++) {
			for (int col = 0; col < layer.getTileWidth(); col++) {
				// Get cell
				Cell cell = layer.getCell(col, row);
				
				if (cell == null) continue;
				if (cell.getTile() == null) continue;
					
				BodyDef bdef = new BodyDef();
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM);
				
				ChainShape  chainShape = new ChainShape();
				Vector2 v[] = new Vector2[4];
				// bottom left
				v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				
				// top left
				v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
				
				// top right
				v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
				
				// bottom right
				v[3] = new Vector2(tileSize / 2 / PPM, -tileSize / 2 / PPM);
				
				chainShape.createChain(v);
				
				FixtureDef fdef = new FixtureDef();
				fdef.friction = 0;
				fdef.shape = chainShape;
				fdef.filter.categoryBits = 1; // temp
				fdef.filter.maskBits = -1;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);
			}
		}

	}
}
