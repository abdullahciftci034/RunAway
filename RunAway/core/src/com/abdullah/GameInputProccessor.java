package com.abdullah;

import com.abdullah.entity.PlayerAction;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class GameInputProccessor  implements InputProcessor {
	private GameMain  gameMain ;
	private PlayerAction playerAction;
	
	
	
	
	public GameInputProccessor(GameMain  gameMain ) {
		super();
		this.gameMain  = gameMain ;
		this.playerAction=this.gameMain.playerAction;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE :{// ziplama bolumu
			this.playerAction.playerJump();
			break;
		}
		case Keys.F:{// player ates etme bolumu
			this.playerAction.bulletFire();
			break;
		}
		case Keys.H:{// burda hizini arttirdik ama sonra bunu iptal edicez
			this.playerAction.levelUpgrade();
			break;
		}
		case Keys.J:{// burda hizini arttirdik ama sonra bunu iptal edicez
			System.out.println("repostion oldu");
			this.gameMain.gameScreen.barrierCreateOto.nextLayer();
			this.gameMain.gameScreen.barrierCreateOto.rePositionLayer();
			
			break;
		}
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		 this.playerAction.GameStart();
		 return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
