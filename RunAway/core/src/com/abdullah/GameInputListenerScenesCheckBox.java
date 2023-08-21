package com.abdullah;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameInputListenerScenesCheckBox extends ChangeListener {
	private GameMain gameMain;
	private  CheckBox checkBox;
	private int id;
	
	public GameInputListenerScenesCheckBox(GameMain gameMain ,int id,CheckBox checkBox) {
		// TODO Auto-generated constructor stub
		this.gameMain=gameMain;
		this.checkBox=checkBox;
		this.id=id;
	}
	@Override
	public void changed(ChangeEvent event, Actor actor) {
		switch (id) {
		case 0:
			this.gameMain.playerAction.soundChange(this.checkBox.isChecked());
			
			break;

		default:
			break;
		}
		
	}

}
