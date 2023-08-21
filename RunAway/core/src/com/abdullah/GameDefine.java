package com.abdullah;

import com.badlogic.gdx.math.Vector2;

public class GameDefine {
	public static final int Scale=1;
	
	public static final int TILEMAPSIZE=16;
	
	public static final int GAMESCREENHEIGHT=TILEMAPSIZE*13;
	public static final int GAMESCREENWIDTH=TILEMAPSIZE*25;
	
	
	
	public static final short BIT_PLAYER=1;
	public static final short BIT_GROUND=2;
	public static final short BIT_BULLET = 4;
	public static final short BIT_BARRIER_FIX=8;
	public static final short BIT_BARRIER_LIVE=16;
	public static final short BIT_DEFAULT=32;
	public static final short BIT_DESTROY=64;
	
	public static final int GAME_PPM=100;
	
	
	//world tanimlamalari
	public final static float WORLDSTEEP=1f/20f;
	public final static int   VELOCITYITERATIONS=8,
								POSITIONITERATIONS=4;
	
	
	public  Vector2 zipla,ziplaCopy,
					point,pointCopy,
					playerVelocity,playerVelocityCopy,
					bulletFireVelocity,bulletFireVelocityCopy,
					worldGravity,worldGravityCopy,
					stop;
	
	public  float orthographicCameraPositionY,
				  animationHzSnake,
				  animationHzBat,
				  bulletRadius;

	public String Map_path;
	
	public int level;
	
	public GameDefine() {
		this.worldGravity=new Vector2(0,-15f/GameDefine.GAME_PPM);
		this.playerVelocity=new Vector2(15f/GameDefine.GAME_PPM,0); 
		this.bulletFireVelocity=new Vector2(50f/GameDefine.GAME_PPM,40f/GameDefine.GAME_PPM);
		this.point=new Vector2(0f,0f); 
		this.zipla=new Vector2(0f,45f/GameDefine.GAME_PPM);
		this.stop=new Vector2(0,0);
		this.orthographicCameraPositionY=104/GameDefine.GAME_PPM;
		this.animationHzSnake=1f/7f;
		this.animationHzBat=1f/6f;
		this.Map_path="require/test3.tmx";
		this.level=1;
		this.bulletRadius=6f;
		this.ziplaCopy=new Vector2();
		this.pointCopy=new Vector2();
		this.playerVelocityCopy=new Vector2();
		this.bulletFireVelocityCopy=new Vector2();
		this.worldGravityCopy=new Vector2();
		
		this.setVector(this.ziplaCopy,this.zipla);
		this.setVector(this.pointCopy,this.point);
		this.setVector(this.playerVelocityCopy,this.playerVelocity);
		this.setVector(this.bulletFireVelocityCopy,this.bulletFireVelocity);
		this.setVector(this.worldGravityCopy,this.worldGravity);
	}
	
	public void setPlayerVelocity(float percentVelocityReduce) {
		this.playerVelocity.x+=this.playerVelocity.x*percentVelocityReduce;
		this.bulletFireVelocity.x+=this.bulletFireVelocity.x*percentVelocityReduce;
		this.bulletFireVelocity.y+=this.bulletFireVelocity.y*percentVelocityReduce;
		this.zipla.x-=((this.zipla.x*percentVelocityReduce)*4);
		this.zipla.y+=this.zipla.y*percentVelocityReduce;
		this.worldGravity.y+=(this.worldGravity.y*percentVelocityReduce)*2;
		
	}
	public void setVector(Vector2 mainVector2,Vector2 tempVector2) {
		mainVector2.x=tempVector2.x;
		mainVector2.y=tempVector2.y;
	}

	
//%21 kadar artabilir	
	
}
