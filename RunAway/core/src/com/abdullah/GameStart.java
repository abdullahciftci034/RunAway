package com.abdullah;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameStart implements Disposable {
	private GameMain gameMain;
	private SpriteBatch  spriteBatch;
	private OrthographicCamera orthographicCamera;
	private Viewport viewport;
	
	private Label  labelStart,LabelScore;
	private String labelStartText,labelScoreText;
	
	private Stage stage,stage2;
	public boolean StartEnd;
	
	public int worldTimer, score;
	
	public GameStart(GameMain gameMain) {
		this.gameMain=gameMain;
		this.spriteBatch=this.gameMain.spriteBatch;
		this.orthographicCamera=new OrthographicCamera(GameDefine.GAMESCREENWIDTH/2,GameDefine.GAMESCREENHEIGHT/2);
		this.viewport= new ScreenViewport(this.orthographicCamera);
		this.StartEnd=true;
		this.labelScoreText="Score  ";
		this.labelStartText="Click to Start";
		
		this.score=0;
		this.worldTimer=0;
	}
	public void create() {
		this.labelStart=new Label(labelStartText,new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		this.LabelScore=new Label(labelScoreText+score,new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		this.labelStart.setFontScale(4f);
		this.LabelScore.setFontScale(4f);
		
		this.stage=new Stage(this.viewport, this.spriteBatch);		
		Table table=new Table();
		table.setPosition(this.viewport.getScreenWidth()/2, this.viewport.getScreenHeight()/2);
		table.add(labelStart);
		table.row();
		table.add(LabelScore);
		this.stage.addActor(table);
		
		
		this.labelStart=new Label(labelStartText,new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		this.labelStart.setFontScale(4f);
		this.stage2=new Stage(this.viewport,this.spriteBatch);
		Table table2=new Table();
		table2.setPosition(this.viewport.getScreenWidth()/2, this.viewport.getScreenHeight()/2);
		table2.add(this.labelStart);
		this.stage2.addActor(table2);
	}
	public void render() {
		if(StartEnd) {
			this.stage2.draw();
			
		}else {
			this.stage.draw();
		}
	}
	public void labelUpdate() {
		this.LabelScore.setText(this.labelScoreText+this.score);
	}
	@Override
	public void dispose() {
		this.stage.dispose();
	}
}
