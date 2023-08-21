package com.abdullah.entity.Array;

import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;

public abstract class BarrierArray implements Disposable{
	//engelleri   olu�turduk
	private GameMain gameMain;
	private GameDefine gameDefine;
	private World world;
	//private Body ;
	public SpriteBatch  spriteBatch;
	
	public  int bodyCount;
	private Body bodyList[];
	
	
	private float
				scaleSize, //scale boyutumuz
				scaleSizeImage,//bu ise �izilirkenki ters scale
				boxWidthScale,boxHeightScale, //box2d Scale edilmi� geni�lik y�kseklik
				ImagePositionX[],ImagePositionY[], //imagecin pozisyonu
				ImageWidthScale,ImageHeightScale,//image scale edilmi� y�kseli�i ve geni�li�i
				ImageOriginX,ImageOriginY,//scale edilmemi� image orijinX ve orijinY
				ImageWidth,ImageHeight//imagenin scale edilmemi� geni�lik ve y�kseli�i
				;//
	private Vector2 baslangicPoz;
	public BarrierArray(GameMain gameMain) {
		
		this.gameMain=gameMain;
		this.gameDefine=this.gameMain.gameDefine;
		this.world=this.gameMain.world;
		this.spriteBatch=this.gameMain.spriteBatch;
		baslangicPoz =new Vector2();
		
		
		
	}
	public void create() {
		this.bodyList=new Body[this.bodyCount];
		this.ImagePositionX=new float[this.bodyCount];
		this.ImagePositionY=new float[this.bodyCount];
	}
	
	//body Kismini olusturduk
	public Body createBody(short CategoryBIT,int []index) {
		Body body;
		PolygonShape polygonShape=new PolygonShape();
		polygonShape.setAsBox(this.boxWidthScale,this.boxHeightScale);
		
		FixtureDef fixtureDef =new FixtureDef();
		fixtureDef.shape=polygonShape;
		fixtureDef.friction=0f;
		fixtureDef.filter.categoryBits=CategoryBIT;
		fixtureDef.filter.maskBits=GameDefine.BIT_PLAYER | GameDefine.BIT_BARRIER_FIX |GameDefine.BIT_BARRIER_LIVE | GameDefine.BIT_BULLET;
		
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.StaticBody;
		
		body=this.world.createBody(bodyDef);
		body.setUserData(index);
		body.createFixture(fixtureDef);
		
		polygonShape.dispose();
		return body;
		
	}
	
	
	
	public void setPosition(float positionX,float positionY,int i) {
		this.baslangicPoz.x=positionX;
		this.baslangicPoz.y=positionY;
		this.bodyList[i].setTransform(baslangicPoz, 0);
	}
	//image scale edilmis boyutlarini hesapladik
	public void setImageScaleWidthAndHeight() {
		this.ImageWidthScale=this.ImageWidth/this.scaleSize;
		this.ImageHeightScale=this.ImageHeight/this.scaleSize;
	}
	// image scale edilmeden orta noktasini
	public void setImageOriginXY() {
		this.ImageOriginX=this.ImageWidth/2f;
		this.ImageOriginY=this.ImageHeight/2f;
	}
	//burda imagelerin pozisyonlarini belirledik
	public void setImagePositionXY() {
		for (int i = 0; i < bodyCount ; i++) {
			this.ImagePositionX[i]=this.bodyList[i].getPosition().x-(this.ImageWidth/2);
			this.ImagePositionY[i]=this.bodyList[i].getPosition().y-(this.ImageHeight/2);			
		}
	}
	
	public void setBoxWidthScale(float boxWidthScale) {
		this.boxWidthScale = boxWidthScale/(this.scaleSize*2);
	}
	public void setBoxHeightScale(float boxHeightScale) {
		this.boxHeightScale = boxHeightScale/(this.scaleSize*2);
	}
	public void setScaleSize(float scaleSize) {
		this.scaleSize = scaleSize;
		this.scaleSizeImage=1f/this.scaleSize;
	}
	public void setImageWidth(float imageWidth) {
		ImageWidth = imageWidth;
	}
	public void setImageHeight(float imageHeight) {
		ImageHeight = imageHeight;
	}
	
	public void setImagePositionX(int index,float x) {
		this.ImagePositionX[index]=x;
	}
	public void setImagePositionY(int index ,float y) {
		this.ImagePositionY[index]=y;
	}
	
	public void setBodyListAdd(Body body,int index) {
		this.bodyList[index]=body;
	}
	
	public  abstract void update();
	
	public abstract void render(float elepsedtime);
	
	public  abstract void render();

	
	
	public void info(int index) {
		System.out.println(this.getImagePositionX(index)+","+
				this.getImagePositionY(index) +","+
				this.getImageOriginX()+","+
				this.getImageOriginY()+","+
				this.getImageWidth()+","+
				this.getImageHeight()+","+
				this.getScaleSizeImage()+","+
				this.getScaleSizeImage()
				 );
	}
	
	
	
	//repozisyon icin
	public void rePositionBody(float X[],float Y[]) {
		for (int i = 0; i < this.bodyCount; i++) {
			baslangicPoz.x=X[i];
			baslangicPoz.y=Y[i];
			this.bodyList[i].setTransform(baslangicPoz, 0);
		}
	}
	
	public void rePositionImageAndBody(float X[],float Y[]) {
		this.rePositionBody(X, Y);
		this.setImagePositionXY();
	}
	
	
	
	
	
	
	public float getImagePositionX(int index) {
		return ImagePositionX[index];
	}
	public float getImagePositionY(int index) {
		return ImagePositionY[index];
	}


	public float getImageHeight() {
		return ImageHeight;
	}
	public float getImageWidth() {
		return ImageWidth;
	}
	public float getScaleSize() {
		return scaleSize;
	}
	public float getScaleSizeImage() {
		return this.scaleSizeImage;
	}
	
	public float getBoxWidthScale() {
		return boxWidthScale;
	}
	public float getBoxHeightScale() {
		return boxHeightScale;
	}
	public float getPositionBodyX(int index) {
		return this.bodyList[index].getPosition().x;
	}	
	public float getPositionBodyY(int index) {
		return this.bodyList[index].getPosition().y;
	}
	public float getImageWidthScale() {
		return this.ImageWidthScale;
	}
	public float getImageHeightScale() {
		return this.ImageHeightScale;
	}
	public float getImageOriginX() {
		return ImageOriginX;
	}
	public float getImageOriginY() {
		return ImageOriginY;
	}
	
	public Body getBodyIndex(int index) {
		return bodyList[index];
	}
	public Body[] getBodyList() {
		return bodyList;
	}
	public GameDefine getGameDefine() {
		return gameDefine;
	}
	public GameMain getGameMain() {
		return gameMain;
	}
	public World getWorld() {
		return world;
	}
	public abstract void createLast(float X[],float Y[]);
	
	
}
