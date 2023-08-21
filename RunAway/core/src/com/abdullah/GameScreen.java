package com.abdullah;

import com.abdullah.entity.Bullet;
import com.abdullah.entity.Player;
import com.abdullah.entity.Array.BarrierCreateOto;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class GameScreen implements Disposable{	
	private GameMain gameMain;	
	private OrthographicCamera orthographicCamera;
	private World world;
	private GameMapLoader gameMapLoader;
	private GameDefine gameDefine;
	
	//kameranin konum hesabinda kullanlan olacak
	public float  cameraPositionHesap,//kamera ile player arsindaki 3 / 4 oranindaki hesap
				  playerPositionStart, //kameranin baslagic noktasini hesapladik
				  playerPositionEnd, //kameranin baslagic noktasini hesapladik
		          platformY, //Playerin ve cisimlerin bulundugu yuksekligi hesapladik
		          PlayerRadius, // player buyuklunu hesapladik
		          mapMeter=0, //mapin uzunlugu
		          gameMapFazlalik, //mapteki fazlaliklar gozkmeyececek sondaki ve bastaki kosamlar
		          freeFieldX0,//burda ise istedigmiz gibi koyabileceimiz cizimlerin alanlari
		          freeFieldX1, // burasi free alanini bitis noktasi
				  narrowField0X0,//burda ise cizimde dikkat edileecek alanlar
				  narrowField0X1,
				  narrowField1X0,
				  narrowField1X1,
				  bulletBetweenPlayerDistance;
				
			
	//player baslangic noktasi ve hizi
	public Vector2 playerStartVectorPoint ;
	private Vector2 playerVelocity;
	
	//yeniden basa geldiginde son hesaplanan vektorler
	public Vector2  reStartPosition , reStartVelocity ;
	
	// player
	public Player player;
	
	public Body playerBody;
	
	public Array<Body> zemin;
	
	public BarrierCreateOto barrierCreateOto;
	
	public Bullet bullet;
	
	public GameScreen(GameMain gameMain) {
		this.gameMain = gameMain;
		this.orthographicCamera=gameMain.orthographicCamera;
		this.world=gameMain.world;
		this.gameMapLoader=gameMain.gameMapLoader;
		this.gameDefine=this.gameMain.gameDefine;
		this.playerVelocity=this.gameDefine.playerVelocity;
	}
	
	
	public void create() {
		this.createGroundBox2d();
		this.calculate();
		
		this.barrierCreateOto =new BarrierCreateOto(this.gameMain);
		this.barrierCreateOto.create();
		this.barrierCreateOto.setPositionLayer();
	
		this.bullet=new Bullet(this.gameMain);
		
	}
	public void update() {
		this.player.update();
		this.orthographicCameraUpdate();
		this.controlPlayer();
	
		
	}
	public void render(float elepsedtime) {

		this.barrierCreateOto.render(elepsedtime);
		this.bullet.render();
		
		this.player.render(elepsedtime);
		
	}

	
	// ekranï¿½n playeri takip etmesini saï¿½ladï¿½k
	private void orthographicCameraUpdate() {
		this.orthographicCamera.position.x=this.playerBody.getPosition().x+this.cameraPositionHesap;
	}
	//camerenï¿½ yenide baï¿½lamsï¿½nï¿½ kontrol ettik
	private void controlPlayer() {
		if(this.playerBody.getPosition().x >=this.playerPositionEnd) {
			this.bulletBetweenPlayerDistance=this.bullet.body.getPosition().x-this.playerBody.getPosition().x;// player ve body arasýndaki uzakligi bulduk ve bunu geciste gondericez
			
			
			this.reStartPosition.y=this.playerBody.getPosition().y;
			this.reStartVelocity.x=this.playerBody.getLinearVelocity().x;
			this.reStartVelocity.y=this.playerBody.getLinearVelocity().y;
			this.playerBody.setTransform(this.reStartPosition, 0);
			this.playerBody.setLinearVelocity(reStartVelocity);
			this.bullet.setTransformUpdate(this.bulletBetweenPlayerDistance);//bulletGecisGuncelle
			this.gameMain.playerAction.levelUpgrade();
		}
	}
	
	//bazï¿½ ï¿½nemli hesaplamalarï¿½ yaptï¿½k
	private void  calculate() {
		//player tanï¿½mlalarï¿½nï¿½ aldï¿½k
		this.player=new Player(this.gameMain);
		this.playerBody=this.player.body;
		this.PlayerRadius=this.player.getPlayerRadius()	;
	
		//player  baï¿½taki hï¿½zï¿½nï¿½ belirledik
		this.playerBody.setLinearVelocity(this.playerVelocity);
		
		//player ve kamera  pozisyonlarï¿½nï¿½ belirledik
		this.gameMapFazlalik=(float) (3.5*GameDefine.TILEMAPSIZE)/GameDefine.GAME_PPM;
		this.cameraPositionHesap=GameDefine.GAMESCREENWIDTH/(4*GameDefine.GAME_PPM);
		this.playerPositionEnd=((this.mapMeter-GameDefine.GAMESCREENWIDTH/(2*GameDefine.GAME_PPM))-this.gameMapFazlalik)-this.cameraPositionHesap;
		this.playerPositionStart = this.gameMapFazlalik+this.cameraPositionHesap;
		
		//boï¿½alan aralï¿½ï¿½ï¿½
		this.freeFieldX0=this.gameMapFazlalik+GameDefine.GAMESCREENWIDTH;
		this.freeFieldX1=this.mapMeter-(this.gameMapFazlalik+GameDefine.GAMESCREENWIDTH);
		
        //burda ise baï¿½lagï¿½ï¿½ noktalarï¿½nï¿½ belirledik
		this.playerStartVectorPoint=new Vector2(this.playerPositionStart,this.platformY+this.PlayerRadius);//burda bir radius ekledik
		this.orthographicCameraUpdate();
		this.playerBody.setTransform(this.playerStartVectorPoint, 0);
		
		//burda yeniden baslangic noktasina  gelecek noktalarï¿½  ve hï¿½zï¿½nï¿½ belirledik
		this.reStartPosition =new Vector2(this.playerStartVectorPoint.x,this.playerStartVectorPoint.y);
		this.reStartVelocity=new Vector2(this.playerVelocity.x,this.playerVelocity.y);
		
		//birinci daralan x kordinatlari
		this.narrowField0X0=this.gameMapFazlalik;
		this.narrowField0X1=this.gameMapFazlalik+(GameDefine.GAMESCREENWIDTH/GameDefine.GAME_PPM);
		// ikinci daralana kordinatlari
		this.narrowField1X0=this.mapMeter-((GameDefine.GAMESCREENWIDTH/GameDefine.GAME_PPM)+this.gameMapFazlalik);
		this.narrowField1X1=this.mapMeter-(this.gameMapFazlalik);
		
	}
	
	
	//zemin olusturduk
	private void createGroundBox2d() {
		this.zemin=new  Array<Body>();
		MapObjects mapObjects=this.gameMapLoader.getLayerObjects("ground");
		for (RectangleMapObject rectangleMapObject : mapObjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle=(Rectangle)rectangleMapObject.getRectangle();
			this.platformY=rectangle.height/GameDefine.GAME_PPM;
			Vector2 []zeminVector2={new Vector2(0,0),new Vector2(rectangle.width/GameDefine.GAME_PPM,0)};
			ChainShape chainShape =new ChainShape();
			chainShape.createChain(zeminVector2);
				

			FixtureDef fixtureDef=new FixtureDef() ;
			fixtureDef.friction=0f;
			fixtureDef.filter.categoryBits=GameDefine.BIT_GROUND;
			fixtureDef.filter.maskBits= GameDefine.BIT_DEFAULT  | GameDefine.BIT_PLAYER | GameDefine.BIT_GROUND | GameDefine.BIT_BULLET ;
			fixtureDef.shape=chainShape;
			
			BodyDef bodyDef =new BodyDef();
			bodyDef.type=BodyType.StaticBody;
			bodyDef.position.x=rectangle.x/GameDefine.GAME_PPM;
			bodyDef.position.y=rectangle.height/GameDefine.GAME_PPM;
			
			zemin.add(this.world.createBody(bodyDef));
			zemin.peek().createFixture(fixtureDef);
			chainShape.dispose();
			this.mapMeter+=rectangle.width/GameDefine.GAME_PPM;
			
			
			
		}
	}
	
	@Override
	public void dispose() {
		this.bullet.dispose();
		this.player.dispose();
		this.gameMapLoader.dispose();
		this.barrierCreateOto.dispose();
		
	}
	
}
