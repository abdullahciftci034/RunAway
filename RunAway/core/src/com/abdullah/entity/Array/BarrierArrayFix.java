package com.abdullah.entity.Array;

import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BarrierArrayFix extends BarrierArray {
	private Texture texture;
	private String pathTexture;
	private Sprite sprite[]; 
	
	public BarrierArrayFix(GameMain gameMain) {
		super(gameMain);
		this.setScaleSize(GameDefine.GAME_PPM);
	}
	
	@Override
	public void createLast(float X[],float Y[]) {//bu ise en son posizyonlari belirlenirken cagrilacak
		this.bodyCount=X.length;
		this.create();
		for (int i = 0; i < this.bodyCount; i++) {
			this.setPosition(X[i],Y[i], i);
		} 
		this.setImagePositionXY();
		for (int i = 0; i < this.bodyCount; i++) {
			this.setSpritePosition(this.getImagePositionX(i),this.getImagePositionY(i),i);
		}
		
	}
	
	@Override
	public void create() {
		super.create();
		for (int j = 0; j < this.bodyCount; j++) {
			this.setBodyListAdd(this.createBody(GameDefine.BIT_BARRIER_FIX,null),j);//body olusturduk			
		}
		this.createTexture();//resmi olusturduk
	}
	public void createTexture() {
		this.texture=new Texture(Gdx.files.internal(this.pathTexture));
		this.sprite=new Sprite[this.bodyCount];
		for (int i = 0; i < this.bodyCount; i++) {
			this.sprite[i]=new Sprite(this.texture);
			this.sprite[i].setScale(this.getScaleSizeImage());
		}
			
	}

	@Override
	public void render() {
		
	}
	
	@Override
	public void update() {
		
	}

	
	@Override
	public void dispose() {
		this.texture.dispose();
	}

	@Override
	public void render(float elepsedtime) {
		for (int i = 0; i < this.bodyCount; i++) {			
			this.spriteBatch.begin();
			this.sprite[i].draw(spriteBatch);
			this.spriteBatch.end();
		}
	}
	
	public void setSpritePosition(float x ,float y ,int i) {
		this.sprite[i].setPosition(x, y);
	}
	
	@Override
	public void setImagePositionXY() {
		for (int i = 0; i < this.bodyCount; i++) {
			this.setImagePositionX(i,(float) this.getBodyIndex(i).getPosition().x-this.sprite[i].getWidth()/2);
			this.setImagePositionY(i,(float)this.getBodyIndex(i).getPosition().y-this.sprite[i].getHeight()/2);			
		}
	}
	
	@Override
	public void rePositionImageAndBody(float X[],float Y[]) {
		this.rePositionBody(X, Y);
		this.setImagePositionXY();
		this.rePositionSprite();
	}
	
	private void rePositionSprite() {
		for (int i = 0; i < this.bodyCount; i++) {
			this.setSpritePosition(this.getImagePositionX(i),this.getImagePositionY(i),i);
		}
	}

	public String getPathTexture() {
		return pathTexture;
	}

	public void setPathTexture(String pathTexture) {
		this.pathTexture = pathTexture;
	}
	

}
