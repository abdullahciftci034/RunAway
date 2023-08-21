package com.abdullah.entity;

import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.abdullah.GameScreen;
import com.abdullah.GameSoundManager;
import com.abdullah.entity.Player.PlayerState;
import com.abdullah.entity.Array.BarrierArrayLive;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


//burda bazi onemli fonksiyonlar yazdik
//Player move ability
public class PlayerAction {
	private GameMain gameMain;
	private GameDefine gameDefine;
	private GameScreen gameScreen;
	private GameSoundManager gameSoundManager;

	private Player player;
	private Bullet bullet;
	private Body playerBody;
	
	private boolean wake;	
	private Vector2 zipla,
					point,
					playerVelocity,
					bulletFireVelocity;
	private boolean TabanKontrol=false;
	private boolean mute=true;
	
	public PlayerAction (GameMain  gameMain) {
		this.gameMain=gameMain;		
		this.gameDefine=this.gameMain.gameDefine;
		this.gameScreen=this.gameMain.gameScreen;
		this.gameSoundManager=this.gameMain.gameSoundManager;
		
		this.player=this.gameScreen.player;
		this.playerBody=player.body;
		
		this.bullet=this.gameScreen.bullet;
		
		
		this.point=this.gameDefine.point;
		this.zipla=this.gameDefine.zipla;
		this.playerVelocity=this.gameDefine.playerVelocity;
		this.bulletFireVelocity=this.gameDefine.bulletFireVelocity;
		this.wake=true;
		
		
		
	}
	
	public void playerDeath() {//oyunumuz bittiginde ne olacak
		if(!gameStartControl()) 
			return;
		this.gameMain.gameStart.StartEnd=false;
		this.player.playerState=PlayerState.DEAT; 
		this.gameMain.GameOverKey=true;
		this.gameMain.gameStart.labelUpdate();
		this.playerBody.setLinearVelocity(this.gameDefine.stop);
		if(this.mute)
			this.gameSoundManager.soundDeat.play();
	
	}

	
	public void playerGroundContactStart() {// birkez ziplam olaylarini kontrol ettik
		if(!gameStartControl()) 
			return;
		this.playerBody.setLinearVelocity(this.gameDefine.playerVelocity);
		this.player.jumpActive=false;
		this.player.playerState=PlayerState.RUN;
		if(TabanKontrol) {
			TabanKontrol=false;
		}else {
			TabanKontrol=true;
		}
		
	}
	public void playerGroundContactEnd() {// birkez ziplam olaylarini kontrol ettik
		if(!gameStartControl()) 
			return;
		if(!TabanKontrol) {
			TabanKontrol=true;
		}else {
			TabanKontrol=false;
			this.player.playerState=PlayerState.JUMP;
		}
		
	}
	
	
	
	public void GameStart() {//oyunun baslamasi icin ekrana dokunma kismidir.
		if(!this.gameMain.StartKey) {
			
			this.gameMain.StartKey=true;
			this.gameMain.gameStart.StartEnd=false;			
			this.reset();
			return ;
		}
		return;
	}
	private void reset() {//baslangic ayarlarini aldik
		this.playerBody.setTransform(this.gameScreen.playerStartVectorPoint, 0);
		this.gameMain.setElepsedtime(0);
		this.gameMain.gameStart.score=0;
		this.gameDefine.setVector(this.gameDefine.zipla,this.gameDefine.ziplaCopy);
		this.gameDefine.setVector(this.gameDefine.point,this.gameDefine.pointCopy);
		this.gameDefine.setVector(this.gameDefine.playerVelocity,this.gameDefine.playerVelocityCopy);
		this.gameDefine.setVector(this.gameDefine.bulletFireVelocity,this.gameDefine.bulletFireVelocityCopy);
		this.gameDefine.setVector(this.gameDefine.worldGravity,this.gameDefine.worldGravityCopy);
		this.gameMain.world.setGravity(this.gameDefine.worldGravity);
		this.playerBody.setLinearVelocity(this.playerVelocity);
		this.player.playerState=PlayerState.RUN;
		this.player.jumpActive=false;
		this.gameDefine.level=1;
		this.gameScreen.barrierCreateOto.setLayerNowIndex(0);
		this.gameScreen.barrierCreateOto.rePositionLayer();
		this.gameMain.gameHUD.updateLabelScore();
		this.nowLevelRate=1;
		this.gameMain.gameHUD.setScoreLevelRate(1);
	}
	
	public void playerJump() {//playerin ziplama bolumudur.
		if(!gameStartControl()) 
			return;
		if(!this.player.jumpActive) {
			this.player.jumpActive=true;				
			this.playerBody.setLinearVelocity(this.playerVelocity);
			this.playerBody.applyLinearImpulse(this.zipla, this.point, this.wake);
			if(this.mute)
				this.gameSoundManager.soundJump.play();
		}
	
	}
	
	
	
	
	public void bulletFire() {//bir bullet ates etme bolumdur.
		if(!gameStartControl()) 
			return;
		if(!this.bullet.active) {
			this.bullet.body.setActive(true);
			this.bullet.body.setTransform(this.player.body.getPosition(),0);
			this.bullet.body.setLinearVelocity(this.gameDefine.stop);
			this.bullet.body.applyLinearImpulse(this.bulletFireVelocity, this.point, this.wake);
			this.bullet.active=true;
			if(this.mute)
				this.gameSoundManager.soundFire.play();
		}
	}
	
	public void bulletGroundContact() {// bullet grounda degdiginde ne yok olmasini hesapladik
		this.bullet.redestroyBullet=true;
	}
	
	public void bulletBarrierLiveContactStart() {//canli barierlerin vefat ettigi andir.
		this.gameMain.gameStart.score+=10;
		this.gameMain.gameHUD.updateLabelScore();
		if(this.mute)
			this.gameSoundManager.soundLiveDeath.play();
	}
	public void bulletBarrierLiveContactEnd(int index[]) {//canli barierlerin vefat ettigi andir.
		for (BarrierArrayLive barrierArrayLive: this.gameScreen.barrierCreateOto.getLayer().barrierArrayLives ){
			if(barrierArrayLive.getId()==index[0]) {
				barrierArrayLive.death(index[1]);
			}
		}		
	}
	
	public boolean gameStartControl() {
		return this.gameMain.StartKey;
	}

	public void soundChange(boolean mute) {//ses acma kapama
		this.mute=mute;
	}
	
	private int levelEndVelocity=8;
	private int levelEnd=20;
	private int nowLevelRate=1;
	public void levelUpgrade() {// level arttirma kontrol etme bolumu
		if(this.gameDefine.level<=this.levelEndVelocity) {
			this.gameMain.gameDefine.setPlayerVelocity(0.15f);
			this.gameMain.world.setGravity(this.gameMain.gameDefine.worldGravity);
			this.player.reloadVelocity();	
		}
		if(this.gameDefine.level<=this.levelEnd) {
			this.gameDefine.level++;
			this.gameMain.gameHUD.updateLabelScore();
			if(this.gameDefine.level==(this.nowLevelRate+2)) {
				this.gameMain.gameHUD.setScoreLevelRate(this.gameMain.gameHUD.getScoreLevelRate()+1);
				this.nowLevelRate=this.gameDefine.level;
			}
		}
		this.gameScreen.barrierCreateOto.randomMap();
	}
	// layer degisikligi kontrol edilir
	// level sonlari belirlenir
	// hiz arttirilir
	// her level %2 artacak 
	// toplam 10 level olacak
}
