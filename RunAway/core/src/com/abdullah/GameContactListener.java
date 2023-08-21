package com.abdullah;
import com.abdullah.entity.PlayerAction;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {
	private GameMain  gameMain ;
	private PlayerAction playerAction;
	
	public GameContactListener(GameMain  gameMain ) {
		super();
		this.gameMain  = gameMain ;
		this.playerAction=this.gameMain.playerAction;
	}
	
	
	@Override
	public void beginContact(Contact contact) {
	    Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        switch (cDef) {
			case GameDefine.BIT_GROUND | GameDefine.BIT_PLAYER://burda ise animasyon gelısler hesapladik
			{	
				this.playerAction.playerGroundContactStart();
				 
				break;
			}
			case GameDefine.BIT_BULLET | GameDefine.BIT_GROUND://burada merminin geri d�n���m�n� hesaplad�k
			{
				this.playerAction.bulletGroundContact();
				break;
			}
			case GameDefine.BIT_BULLET | GameDefine.BIT_BARRIER_LIVE://canli nesneleri yok etme
			{
				this.playerAction.bulletBarrierLiveContactStart();
				break;
			}
			case GameDefine.BIT_PLAYER | GameDefine.BIT_BARRIER_FIX : // duran nesneye carptiginda
			{
				
				this.playerAction.playerDeath();
			}
			case GameDefine.BIT_PLAYER  | GameDefine.BIT_BARRIER_LIVE: //canli nesneye canli nesnelere carptiginda oyun biter
			{
				this.playerAction.playerDeath();
			}
			default:
				break;
		}
	      
	}
	

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        	
        switch (cDef) {
			case GameDefine.BIT_GROUND | GameDefine.BIT_PLAYER://burda ise animasyon ge�i�lerini hesaplad�k
			{
				this.playerAction.playerGroundContactEnd();
				break;
			}
			case GameDefine.BIT_BULLET | GameDefine.BIT_BARRIER_LIVE://canli nesneleri yok etme
			{
				if(contact.getFixtureB().getBody().getUserData() instanceof Integer[]) {
					this.playerAction.bulletBarrierLiveContactEnd((int[])contact.getFixtureB().getBody().getUserData());
				}else {
					this.playerAction.bulletBarrierLiveContactEnd((int[])contact.getFixtureA().getBody().getUserData());
				}
				break;
			}
			default:
				break;
        }
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		 
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}
