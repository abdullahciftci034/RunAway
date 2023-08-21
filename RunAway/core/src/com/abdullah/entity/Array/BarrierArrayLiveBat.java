package com.abdullah.entity.Array;

import com.abdullah.GameMain;

public class BarrierArrayLiveBat extends  BarrierArrayLive  {

	public BarrierArrayLiveBat(GameMain gameMain) {
		super(gameMain);
		this.setImageHeight(20f);
		this.setImageWidth(35f); 
		this.setAnimationHz(this.getGameDefine().animationHzBat);
		this.setAnimationTexturePath("require/Barriers/bat_flying2_strip8.png");
		this.setBoxWidthScale(this.getImageWidth()-7f);
		this.setBoxHeightScale(this.getImageHeight()-7f);
		this.setId(1);
	}

}
