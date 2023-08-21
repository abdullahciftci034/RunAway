package com.abdullah.entity;

import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {
	
	public enum PlayerState{
		JUMP,RUN,DEAT,IDLE,HIT
	}
	private GameMain gameMain;
	private SpriteBatch spriteBatch;
	private World world;
	
	private Animation<TextureRegion>  AnimationIdle,AnimationRun,AnimationJump,AnimationHit,AnimationDeath;
	private Texture textureIdle,textureRun,textureJump,textureHit,textureDeath;
	
	
	public Body body;
	public PlayerState playerState;
	
	
	private String CharacterPath;
	
	
	private float characterScale,//karakter buyuklugu
				  characterImageScale,//karakterin imageScale
				  characterImagePositionX, //karakterin x pozisyonu
				  characterImagePositionY, // karakterin y pozisyonu
				  originX,originY, // karakterin resminin orta noktas�
				  cikartilanX,cikartilanY,//burdada �ikartilan b�l�m
				  PlayerRadius;//body karakter b�y�kl���
	
	private int tileWidth,tileHeight;//image split sizelar

	public boolean jumpActive;


	
	public Player(GameMain gameMain) {
		this.gameMain=gameMain;
		this.spriteBatch=this.gameMain.spriteBatch;
		this.world=this.gameMain.world;
		this.jumpActive=false;
		this.define();
		this.createBody();
		this.createAnimation();
		
	}
	
	
	public Player() {
		this.createBody();
		this.createAnimation();
		this.define();
		
	}
	
	
	private void define() {
		this.playerState=PlayerState.RUN;
		this.setCharacterScale(GameDefine.GAME_PPM);
		this.CharacterPath="require/Character/";
		this.setPlayerRadius(15f);
		this.tileWidth=50;
		this.tileHeight=50;
		this.setOriginXYAndPosition();
		
		
	}
	
	//player body
	private void createBody() { 
		
		
		CircleShape circleShape =new CircleShape();
		circleShape.setRadius(PlayerRadius);
		
		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.shape=circleShape;
		fixtureDef.friction=0f;
		fixtureDef.filter.categoryBits=GameDefine.BIT_PLAYER;
		fixtureDef.filter.maskBits=GameDefine.BIT_DEFAULT  | GameDefine.BIT_DESTROY | GameDefine.BIT_GROUND | GameDefine.BIT_BARRIER_FIX | GameDefine.BIT_BARRIER_LIVE;  
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.DynamicBody;
		
		body=world.createBody(bodyDef);
		body.createFixture(fixtureDef).setUserData("player");;
		
		circleShape.dispose();
		
		
	}
	//Animasyon yap�m�
	private  void createAnimation() {
		//Idle Animation
		TextureRegion[] texttureRegions;
		this.textureIdle=new Texture(Gdx.files.internal(CharacterPath+"Idle.png"));	
		TextureRegion[][] texttureRegionsTemp;
		texttureRegionsTemp =TextureRegion.split(textureIdle, this.tileWidth ,this.tileHeight);
		texttureRegions=new TextureRegion[4];
	
		for (int j = 0; j < 4; j++) {
			texttureRegions[j]=texttureRegionsTemp[0][j];
		}
		this.AnimationIdle=new <TextureRegion>Animation<TextureRegion>(1f/6f,texttureRegions);
		
	
		//Run animation
		TextureRegion[] texttureRegions1;
		TextureRegion[][] texttureRegionsTemp1;
		this.textureRun=new Texture(Gdx.files.internal(CharacterPath+"Run.png"));	
		texttureRegionsTemp1 =TextureRegion.split(textureRun,this.tileWidth ,this.tileHeight);
		texttureRegions1=new TextureRegion[6];
	
		for (int j = 0; j < 6; j++) {
			texttureRegions1[j]=texttureRegionsTemp1[0][j];
		}
		this.AnimationRun=new <TextureRegion>Animation<TextureRegion>(1f/6f,texttureRegions1);
	
		
		//Jump Animation
		TextureRegion[] texttureRegions11;
		TextureRegion[][] texttureRegionsTemp11;
		this.textureJump=new Texture(Gdx.files.internal(CharacterPath+"Jump.png"));	

		
		texttureRegionsTemp11 =TextureRegion.split(textureJump,this.tileWidth ,this.tileHeight);
		texttureRegions11=new TextureRegion[2];
		
		for (int j = 0; j < 2; j++) {
			texttureRegions11[j]=texttureRegionsTemp11[0][j];
		}
		this.AnimationJump=new <TextureRegion>Animation<TextureRegion>(1f/2f,texttureRegions11);
		
		
		//Hit Animation
		TextureRegion[] texttureRegions111;
		TextureRegion[][] texttureRegionsTemp111;
		this.textureHit=new Texture(Gdx.files.internal(CharacterPath+"Take Hit.png"));	

		
		texttureRegionsTemp111 =TextureRegion.split(textureHit,this.tileWidth ,this.tileHeight);
		texttureRegions111=new TextureRegion[4];
		
		for (int j = 0; j < 4; j++) {
			texttureRegions111[j]=texttureRegionsTemp111[0][j];
		}
		this.AnimationHit=new <TextureRegion>Animation<TextureRegion>(1f/4f,texttureRegions111);
		
		
		
		// Death Animation
		TextureRegion[] texttureRegions1111;
		TextureRegion[][] texttureRegionsTemp1111;
		this.textureDeath=new Texture(Gdx.files.internal(CharacterPath+"Death.png"));	

		
		texttureRegionsTemp1111 =TextureRegion.split(textureDeath,this.tileWidth ,this.tileHeight);
		texttureRegions1111=new TextureRegion[8];
		
		for (int j = 0; j < 8; j++) {
			texttureRegions1111[j]=texttureRegionsTemp1111[0][j];
		}
		this.AnimationDeath=new <TextureRegion>Animation<TextureRegion>(1f/4f,texttureRegions1111);
		
		
	}
	
	private void setOriginXYAndPosition() {
		this.originX=((float)this.tileWidth)/2;
		this.originY=((float)this.tileHeight)/2;
		this.cikartilanX=this.originX;//burda yukar�daki ile ayn� i�lem yap�lm��
		this.cikartilanY=this.originY;
	}
	public void update() {//karakterin remini konumunu guncelledik
		this.characterImagePositionX=this.body.getPosition().x-cikartilanX;
		this.characterImagePositionY=this.body.getPosition().y-cikartilanY;
	}
	
	public void render(float elepsedtime) {//renderladigimiz kisim
		
		switch (this.playerState) {
		case IDLE:
			this.drawPlayer(this.AnimationIdle, elepsedtime);
			break;
		case RUN:
			this.drawPlayer(this.AnimationRun,elepsedtime);
			break;
		case JUMP:
			this.drawPlayer(this.AnimationJump, elepsedtime);
			break;
		case DEAT:
			this.drawPlayer(this.AnimationDeath, elepsedtime);
			break;
		case HIT:
			this.drawPlayer(this.AnimationHit, elepsedtime);
			break;
		default:
			break;
		}
		
	}
	private void drawPlayer(Animation<TextureRegion> animation,float elepsedtime) {
		this.spriteBatch.begin();
		this.spriteBatch.draw(animation.getKeyFrame(elepsedtime, true),
				this.characterImagePositionX,
				this.characterImagePositionY,
				this.originX,
				this.originY,
				this.tileWidth,
				this.tileHeight,
				this.characterImageScale,
				this.characterImageScale,
				0
				);
		this.spriteBatch.end();
	}

	@Override
	public void dispose() {

		this.textureIdle.dispose();
		this.textureJump.dispose();
		this.textureRun.dispose();
		this.textureHit.dispose();
		this.textureDeath.dispose();
		
	}

	public float getPlayerRadius() {
		return PlayerRadius;
	}
	public void setPlayerRadius(float playerRadius) {
		this.PlayerRadius = playerRadius/this.characterScale;
	}

	private void setCharacterImageScale() {
		this.characterImageScale = 1f/this.characterScale;
	}
	public void setCharacterScale(float characterScale) {
		this.characterScale = characterScale;
		this.setCharacterImageScale();
	}
	
	
	public float PlayerBodyPositionX() {
		return this.body.getPosition().x;
	}
	public float PlayerBodyPositionY() {
		return this.body.getPosition().x;
	}
	public Vector2 PlayerBodyPosition() {
		return this.body.getPosition();
	}
	
	public void reloadVelocity() {
		this.body.setLinearVelocity(this.gameMain.gameDefine.playerVelocity);
	}
	
	
	
	
	
}
