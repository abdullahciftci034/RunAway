package com.abdullah;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameHUD implements Disposable {
	private GameMain gameMain;
	
	public Stage stage;
	private Table  table;
	private OrthographicCamera orthographicCamera;
	private Viewport viewport;
	
	public Label 	
			labelScore,
			labelLevel,
			labelWorldTimer;
		
	public Button buttonJump,buttonFire;
	
	private Skin skin;

	
	private GameInputListenerScenesButton gameInputListenerScenesFire,gameInputListenerScenesJump;
	private GameInputListenerScenesCheckBox gameInputListenerScenesSound;
	public GameHUD(GameMain gameMain) {
		
		this.gameMain=gameMain;
			
		this.orthographicCamera=new OrthographicCamera(GameDefine.GAMESCREENWIDTH, GameDefine.GAMESCREENHEIGHT);
		this.viewport=new ScreenViewport(this.orthographicCamera);
		
		this.stage=new Stage(this.viewport,this.gameMain.spriteBatch);
		
		this.skin=new Skin(Gdx.files.internal("require\\button1\\output\\newButton.json"));
		
		this.table =new Table();
		
		this.table.top();
		this.table.setFillParent(true);
		
		
		this.labelScore = new Label("Score : "+this.gameMain.gameStart.score,skin) ;
		this.labelLevel=new Label("Level : "+this.gameMain.gameDefine.level,skin);
		this.labelWorldTimer=new Label("Time : "+this.gameMain.gameStart.worldTimer,skin);
	
		this.table.add(labelScore).expandX();
		this.table.add(labelLevel).expandX();
		this.table.add(labelWorldTimer).expandX();
		
		
		
		
		
		
		this.buttonJump=new TextButton("Jump",skin); 
		this.buttonJump.setPosition(this.buttonJump.getWidth()/4, this.buttonJump.getHeight()/4);	
		
		this.buttonFire=new TextButton("Fire",skin); 
		this.buttonFire.setPosition(this.viewport.getScreenWidth()-this.buttonFire.getWidth()-this.buttonFire.getWidth()/4, 0+this.buttonFire.getHeight()/4);
		
		
		this.gameInputListenerScenesJump=new GameInputListenerScenesButton(this.gameMain,0);
		this.gameInputListenerScenesFire=new GameInputListenerScenesButton(this.gameMain,1);
		
		this.buttonJump.addListener(this.gameInputListenerScenesJump);
		this.buttonFire.addListener(this.gameInputListenerScenesFire);
		
		
		CheckBox checkBox=new CheckBox("", skin);
		checkBox.setPosition(this.viewport.getScreenWidth()-checkBox.getWidth(), this.viewport.getScreenHeight()-checkBox.getHeight());
		checkBox.setChecked(true);
	
		this.gameInputListenerScenesSound=new GameInputListenerScenesCheckBox(this.gameMain,0,checkBox);
		checkBox.addListener(this.gameInputListenerScenesSound);
		this.stage.addActor(checkBox);
		this.stage.addActor(table);
		this.stage.addActor(buttonJump);
		this.stage.addActor(buttonFire);
		
		
		
	}
	
	private int previousworldTimer=0,scorei=0;
	private int scoreLevelRate=1;
	
	public void update(float elepsedtime) {
		this.gameMain.gameStart.worldTimer=(int)elepsedtime;
		
		if(this.previousworldTimer!=this.gameMain.gameStart.worldTimer) {
			this.labelWorldTimer.setText("Time: "+this.gameMain.gameStart.worldTimer);
			this.previousworldTimer=this.gameMain.gameStart.worldTimer;
			if(this.scorei>0) {
				this.gameMain.gameStart.score+=scoreLevelRate;
				this.labelScore.setText("Score : "+this.gameMain.gameStart.score);
				this.scorei=0;
			}else {
				this.scorei++;
			}
			
		}
	}
	
	public void updateLabelScore() {
		this.labelScore.setText("Score : "+this.gameMain.gameStart.score);
		this.labelLevel.setText("Level : "+this.gameMain.gameDefine.level);
		this.labelWorldTimer.setText("Timer : "+this.gameMain.gameStart.worldTimer);
	}
	
	
	public void render () {
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
	}

	@Override
	public void dispose() {
		this.stage.dispose();
		this.skin.dispose();
	}

	
	
	
	
	
	
	
	public void setScoreLevelRate(int scoreLevelRate) {
		this.scoreLevelRate = scoreLevelRate;
	}
	public int getScoreLevelRate() {
		return this.scoreLevelRate;
	}
	
	
	
}
