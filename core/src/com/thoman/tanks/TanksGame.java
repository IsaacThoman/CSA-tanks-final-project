package com.thoman.tanks;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import javax.net.ssl.SSLProtocolException;
import java.util.ArrayList;

public class TanksGame implements ApplicationListener {

	public static Board board;
	public static ArrayList<GameObject> objectsToRemove = new ArrayList<GameObject>();

	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public static AssetManager assets;
	public Environment environment;
	public static boolean loading;
	Input.Keys keys;

	public ArrayList<ModelInstance> tileInstances = new ArrayList<ModelInstance>();
	public ArrayList<ModelInstance> gameObjectInstances = new ArrayList<ModelInstance>();

	public ArrayList<ModelInstance> allInstancesCombined = new ArrayList<ModelInstance>(); //update every second or so

	public static Model salamander;
	public static Model bananan;
	public static Model tankModel;

	public static int tankStartHealth = 1;
	public static double tanksPerSecond = 0.4;

	@Override
	public void create () {

		board = new Board();

		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 60.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 8f, 6f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 30000f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
		keys = new Input.Keys();

		assets = new AssetManager();
		assets.load("data/Tanks/tnk_tank_e.g3db", Model.class);
		assets.load("data/CageSalamander/salamander2.g3db",Model.class);
		assets.load("data/Banana/Banana.g3db",Model.class);
		loading = true;


	}

	private void doneLoading() {

	//	shipInstance.transform.setToTranslationAndScaling(0,0.49f,0,0.03f,0.03f,0.03f);
	//	shipInstance.transform.setTranslation(4,0.49f,0);
	//	gameObjectInstances.add(shipInstance);
		board.fillObjectInstances(tileInstances,gameObjectInstances);

		salamander = TanksGame.assets.get("data/CageSalamander/salamander2.g3db", Model.class);
		bananan = TanksGame.assets.get("data/Banana/Banana.g3db", Model.class);
		tankModel = TanksGame.assets.get("data/Tanks/tnk_tank_e.g3db", Model.class);
		for(int i = -10; i>-1000; i-=5){

		}

		GameObject salamanderObject = new Tower(salamander,3,0,0);
		board.addGameObject(salamanderObject);
		//ModelInstance salamanderInstance = new ModelInstance(salamander);
		//GameObject salamanderObj = new GameObject(salamanderInstance,3,3,3,1);
		//board.addGameObject(salamanderObj);
		loading = false;
	}

public static int frameOn;

	@Override
	public void render () {
		if (loading && assets.update())
			doneLoading();
		//camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(allInstancesCombined);
		modelBatch.end();

		Ray ray = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
		Vector3 tmpVector = new Vector3();
		float distance = -ray.origin.y / ray.direction.y;
		tmpVector.set(ray.direction).scl(distance).add(ray.origin);

		board.update();
		if(frameOn%10==0){
			board.fillObjectInstances(tileInstances, gameObjectInstances);
			allInstancesCombined.clear();
			for (ModelInstance m : tileInstances)
				allInstancesCombined.add(m);
			for (ModelInstance m : gameObjectInstances)
				allInstancesCombined.add(m);
		}

		int[] coords = board.closestSquare(tmpVector.x,tmpVector.y,tmpVector.z);

		if(frameOn%(60/tanksPerSecond)==0)
			addTank();

frameOn++;
	while(objectsToRemove.size()>0){
		board.removeGameObject(objectsToRemove.remove(0));
	}

	}

	private void addTank(){
		if(loading) return;
		GameObject tank1 = new Tank(tankModel, -8,-2, board.level1TurningPoints(), 90);
		board.addGameObject(tank1);
	}

	public static void addProjectile(float x, float z ,double direction, double speed){
		if(loading) return;
		GameObject projectile = new Projectile(bananan, x,z,direction,0.07);
		board.addGameObject(projectile);
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		assets.dispose();
	}

	public void resume () {
	}

	public void resize (int width, int height) {
	}

	public void pause () {
	}
}
