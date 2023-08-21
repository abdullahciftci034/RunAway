package com.abdullah.entity;

import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.abdullah.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;

public class Bullet implements Disposable {
	private GameMain gameMain;
	private World world;
	public SpriteBatch  spriteBatch;
	private GameScreen gameScreen;
	
	private Texture texture;
	private Sprite sprite; 
	private String pathTexture;
	
	private float BulletRadius, // bulletin scale edilmis radiusu
				  BulletScale,  //bulletin scaleOrani
				  BulletImageScale;//Bulletin resmini scale orani
			
			
	public Body body;
	
	public boolean active,redestroyBullet=false;
	private int reDestroyBulletCount=0;
	
	//yeniden baslamada kullanilan posizyonu
	private Vector2 bulletRestartPosition,
					bulletRestartLasVelocity;
	
	public Bullet(GameMain gameMain) {
		this.gameMain=gameMain;
		this.world=this.gameMain.world;
		this.spriteBatch=this.gameMain.spriteBatch;
		this.gameScreen=this.gameMain.gameScreen;
		
		this.setBulletScale(GameDefine.GAME_PPM);
		this.setBulletRadius(this.gameMain.gameDefine.bulletRadius);
		this.pathTexture="require/BulletRock.png";
		
		this.createBullet();
		this.createTexture();
		this.active=false;
		
		this.bulletRestartPosition=new Vector2(0,0);
		this.bulletRestartLasVelocity=new Vector2(0,0);
		
	}
	
	

	private void createBullet() {
		//Vector2 vector2=new Vector2(500,this.gameScreen.platformY+60);
		body=this.createBody(0);
	}
	
	//body Kismini olusturduk
	private Body createBody(int index) {
		
		CircleShape  circleShape=new CircleShape();
		circleShape.setRadius(this.BulletRadius);
		
		FixtureDef fixtureDef =new FixtureDef();
		fixtureDef.shape=circleShape;
		fixtureDef.friction=0f;
		fixtureDef.filter.categoryBits=GameDefine.BIT_BULLET;
		fixtureDef.filter.maskBits= GameDefine.BIT_BARRIER_LIVE | GameDefine.BIT_DEFAULT | GameDefine.BIT_GROUND ;
	
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.DynamicBody;
		bodyDef.bullet=true;
		bodyDef.active=false;
		
		
		Body body=this.world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(index);
		circleShape.dispose();
		
		return body;
	}
	
	//BulletRock.png
	private void createTexture() {
		this.texture=new Texture(Gdx.files.internal(this.pathTexture));
		this.sprite=new Sprite(this.texture);
		this.sprite.setScale(this.BulletImageScale);
		
	}

	public void render() {
		if(this.active) {
			this.sprite.setX(this.getPositionX()-(this.sprite.getOriginX()));
			this.sprite.setY(this.getPositionY()-(this.sprite.getOriginY()));
			this.spriteBatch.begin();
			this.sprite.draw(spriteBatch);
			this.spriteBatch.end();
			reDestroyBullet();
		}
	}
	
	
	//yere veya sabit bir canl� cisime �aprt���nda aktif olacak
	private void reDestroyBullet() {
		if(this.redestroyBullet) {
			if(this.reDestroyBulletCount>15) {
				this.reDestroyBulletCount=0;
				this.active=false;
				this.body.setActive(false);
				this.redestroyBullet=false;
			}
			this.reDestroyBulletCount++;
		}
	}
	
	
	@Override
	public void dispose() {
		this.texture.dispose();
		
	}
	public void update() {
		
	}
	
	
	public void render(float elepsedtime) {
		
	}

	


	private void setCharacterImageScale() {
		this.BulletImageScale = (1f/this.BulletScale)*(0.05f);
	}
	public void setBulletScale(float bulletScale) {
		this.BulletScale = bulletScale;
		this.setCharacterImageScale();
	}
	private void setBulletRadius(float bulletRadius) {
		// TODO Auto-generated method stub
		this.BulletRadius = bulletRadius/this.BulletScale;
	}
	
	
	
	
	public float getPositionX() {
		return  this.body.getPosition().x;
	}
	public float getPositionY() {
		return  this.body.getPosition().y;
	}

	
	
	public void setTransformUpdate(float bulletBetweenPlayerDistance) {//merminin gecis sirasinda atildiginda yok olmamasi icin yaptik
		bulletBetweenPlayerDistance+=this.gameScreen.playerPositionStart;
		this.bulletRestartPosition.x=bulletBetweenPlayerDistance;
		this.bulletRestartPosition.y=this.body.getPosition().y;
		this.bulletRestartLasVelocity=this.body.getLinearVelocity();
		this.body.setTransform(this.bulletRestartPosition, 0 );
		this.body.setLinearVelocity(bulletRestartLasVelocity);
	}
	
	
	
	
	
	public void info() {
		System.out.println("sprite : ("+this.sprite.getX()+","+this.sprite.getY()+")");
		System.out.println("body : "+this.body.getPosition());
		
	}


	

	
}
