package com.abdullah.entity.Array;

import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BarrierArrayLive extends BarrierArray {
	private Animation<TextureRegion> []animations = null; 
	private Texture texture=null;
	private String animationTexturePath;
	private float animationHz;
	private int id;

	public BarrierArrayLive(GameMain gameMain) {
		super(gameMain);
		this.setScaleSize(GameDefine.GAME_PPM);
		
	}
	
	@Override
	public void createLast(float X[],float Y[]) {//bu ise son cagrilan olucak
		this.bodyCount=X.length;
		this.create();
		this.load(this.id);
		for (int j = 0; j < this.bodyCount; j++) {
			this.setPosition(X[j], Y[j], j);			
		}
		this.setImagePositionXY();
	}

	@Override
	public void create() {
		super.create();
		
	}

	public void load(int type) {
		this.loadTextureAnimation();
		
//		this.setBoxWidthScale(this.getImageWidth());
//		this.setBoxHeightScale(this.getImageHeight());
		for (int i = 0; i < this.bodyCount; i++) {
			int d[]=new int[]{type,i}; 
			this.setBodyListAdd(this.createBody(GameDefine.BIT_BARRIER_LIVE,d),i);
		}
		this.setImageOriginXY();
	}
	
	public void loadTextureAnimation() {
		this.texture=new Texture(Gdx.files.internal(this.animationTexturePath));
		TextureRegion [][]tempTexttureRegion=TextureRegion.split(this.texture,(int)this.getImageWidth(),(int)this.getImageHeight());
		
		Array<TextureRegion> array=new Array<TextureRegion>();
		for (int i = 0; i < tempTexttureRegion.length; i++) {
			for (int j = 0; j < tempTexttureRegion[i].length; j++) {
				array.add(tempTexttureRegion[i][j]);
			}
			
		}
		this.animations =new Animation[this.bodyCount];
		
		for (int i = 0; i < this.bodyCount; i++) {
			this.animations[i]=new Animation<TextureRegion>(this.animationHz,array); 
		}
		
	}


	@Override
	public void update() {
	}
	
	private int time=0;
	private boolean deathKontrol=false;
	private int deathIndex;
	@Override
	public void render(float elepsedtime) {
		for (int i = 0; i < this.bodyCount; i++) {			
			this.spriteBatch.begin();
			spriteBatch.draw(		
					this.animations[i].getKeyFrame(elepsedtime, true),
					this.getImagePositionX(i),
					this.getImagePositionY(i),
					this.getImageOriginX(),
					this.getImageOriginY(),
					this.getImageWidth(),
					this.getImageHeight(),
					this.getScaleSizeImage(),
					this.getScaleSizeImage(),
					0
					);
			this.spriteBatch.end();
		}
		if(this.deathKontrol) {
			this.setImagePositionX(deathIndex, -1f);
			this.setImagePositionY(deathIndex, -1f);
			this.getBodyIndex(deathIndex).setTransform(-1f,-1f, 0);
			this.deathKontrol=false;
			
			
		}
	}
	public void death(int index) {
		this.deathIndex=index;
		this.deathKontrol=true;
	}
	
	@Override
	public void dispose() {
		this.texture.dispose();
	}
	
	@Override
	public void render() {
		
	}
	public float getAnimationHz() {
		return animationHz;
	}
	public void setAnimationHz(float animationHz) {
		this.animationHz = animationHz;
	}



	public String getAnimationTexturePath() {
		return animationTexturePath;
	}

	public void setAnimationTexturePath(String animationTexturePath) {
		this.animationTexturePath = animationTexturePath;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
