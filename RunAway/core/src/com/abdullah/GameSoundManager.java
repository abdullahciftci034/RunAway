package com.abdullah;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class GameSoundManager implements Disposable {
	private AssetManager assetManager;
	
	public Sound soundJump,soundFire,soundDeat,soundLiveDeath;
	public void load() {
		assetManager= new AssetManager();
	
		this. assetManager.load("require/audio/sounds/jump.wav", Sound.class);
	    this. assetManager.load("require/audio/sounds/fall.wav", Sound.class);
	    this. assetManager.load("require/audio/sounds/death.wav", Sound.class);
	    this. assetManager.load("require/audio/sounds/deathLive.wav", Sound.class);
	    this.  assetManager.finishLoading();
	    
	    
	    this. soundJump=assetManager.get("require/audio/sounds/jump.wav");
	    this.soundFire=assetManager.get("require/audio/sounds/fall.wav");
	    this.soundDeat=assetManager.get("require/audio/sounds/death.wav");
	    this.soundLiveDeath=assetManager.get("require/audio/sounds/deathLive.wav");
	  
	}
	@Override
	public void dispose() {
		this.assetManager.dispose();
	}
}
