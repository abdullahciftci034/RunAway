package com.abdullah;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameInputListenerScenesButton extends ClickListener{
	private GameMain gameMain;
	private int buttonId;
	public GameInputListenerScenesButton(GameMain gameMain,int buttonId) {
		// TODO Auto-generated constructor stub
		this.gameMain=gameMain;
		this.buttonId=buttonId;
	} 
	//0 jump
	//1 fire
	@Override
	public void clicked(InputEvent event, float x, float y) {
		switch (buttonId) {
		case 0:
			this.gameMain.playerAction.playerJump();
			break;
		case 1:
			this.gameMain.playerAction.bulletFire();
			break;
		default:
			break;
		}
	}
	


	
}
