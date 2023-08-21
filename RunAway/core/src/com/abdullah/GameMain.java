package com.abdullah;



import com.abdullah.entity.PlayerAction;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class GameMain extends   Game  {
	
	public SpriteBatch spriteBatch;
	public OrthographicCamera orthographicCamera;
	public Viewport viewPort;
	
	private Vector2 worldGravity;
	public World world;
	public Box2DDebugRenderer box2DDebugRenderer;
	
	public GameMapLoader gameMapLoader;
	
	public GameScreen  gameScreen;
	
	public GameContactListener gameContactListener;
	
	public GameInputProccessor gameInpuProccessor;
	
	public GameDefine gameDefine;
	
	public GameHUD gameHUD;
	
	public GameStart gameStart;
	
	public PlayerAction playerAction;
	
	public GameSoundManager gameSoundManager;
	
 	public boolean StartKey,GameOverKey;//bu oyun basladi ise true oyun baslamadi ise false

	@Override
	public void create() {	
		this.StartKey=false;
		this.gameDefine=new GameDefine();
		this.spriteBatch=new SpriteBatch();
		this.gameStart=new GameStart(this);//baslamad kullanilan ekranda cikmasi istenen seyleri yaptik
		this.gameStart.create();
		this.gameSoundManager =new GameSoundManager();
		this.gameSoundManager.load();
		
		//orthoraghic world ve Viewport tanimlamalari yaptik
		this.orthographicCamera =new OrthographicCamera(GameDefine.GAMESCREENWIDTH/GameDefine.GAME_PPM,GameDefine.GAMESCREENHEIGHT/GameDefine.GAME_PPM);
		this.orthographicCamera.position.y=this.gameDefine.orthographicCameraPositionY;
		this.viewPort=new FitViewport(GameDefine.GAMESCREENWIDTH/(GameDefine.GAME_PPM),GameDefine.GAMESCREENHEIGHT/(GameDefine.GAME_PPM),orthographicCamera);
	
		//map islemleri
		this.gameMapLoader=new GameMapLoader(this,this.gameDefine.Map_path);
	
		//world olaylari burda yazdik
		this.worldGravity=this.gameDefine.worldGravity;
		this.world =new  World(this.worldGravity,true);
		this.box2DDebugRenderer=new Box2DDebugRenderer();
		
		//erkana ï¿½izilecek elemanlarï¿½ depo ettik
		this.gameScreen=new GameScreen(this);
		this.gameScreen.create();

		this.playerAction=new PlayerAction(this);//playerin bazi önemli tanimlamalari vardir. bir aksiyon oldgunda burdaki metodlar cagrilacakk
		
		//world iï¿½in contact listeneri ayarladï¿½k
		this.gameContactListener=new GameContactListener(this);
		this.world.setContactListener(gameContactListener);
		
		//Input proccesoru ayarladï¿½k
		this.gameInpuProccessor=new GameInputProccessor(this);
		
		this.gameHUD=new GameHUD(this);		
		InputMultiplexer im=new InputMultiplexer(this.gameHUD.stage,this.gameInpuProccessor); 
	
		Gdx.input.setInputProcessor(im);
		
		
		
		
	}
	public void update() {
		
		this.gameScreen.update();	
		this.orthographicCamera.update();
		this.gameHUD.update(this.elepsedtime);
	}

	private float elepsedtime=0;
	private int startDelay=0,gameOverDelay=0;
	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		
		
		Gdx.gl.glClearColor(0.1f,0f, 0f,0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(this.StartKey) {
			//burda baslangic delayi verdik
			if(startDelay<30) {
				startDelay++;
				return;
			}
			
			elepsedtime+=Gdx.graphics.getDeltaTime();
			
			this.update();
			
			this.spriteBatch.setProjectionMatrix(this.orthographicCamera.combined);

			//world cizdirmesi
			this.box2DDebugRenderer.render(this.world, this.orthographicCamera.combined);
			this.world.step(GameDefine.WORLDSTEEP, GameDefine.VELOCITYITERATIONS, GameDefine.POSITIONITERATIONS);

			//map cizdirmesi
			this.gameMapLoader.render();
			
			//oyun elemanlarï¿½nï¿½ ï¿½izdirdiï¿½miz yer
			this.gameScreen.render(elepsedtime);
			

			
			//labellarý çizdirdik
			this.gameHUD.render();
			
			
			//burda bitis delaylarini verdik
			if(this.GameOverKey) {
				if(this.gameOverDelay>60) {
					this.StartKey=false;
					this.gameOverDelay=0;
					this.startDelay=0;
					this.GameOverKey=false;
				}else {
					this.gameOverDelay++;
				}
			}	
		}else {
			
			this.gameStart.render();
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		this.spriteBatch.dispose();
		this.gameMapLoader.dispose();
		this.gameScreen.dispose();
		this.gameHUD.dispose();
		this.gameSoundManager.dispose();
		this.world.dispose();
		this.gameStart.dispose();
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		this.viewPort.update(width, height,false);
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		super.setScreen(screen);
	}

	@Override
	public Screen getScreen() {
		// TODO Auto-generated method stub
		return super.getScreen();
	}
	
	public void setElepsedtime(float elepsedtime) {
		this.elepsedtime = elepsedtime;
	}
}
