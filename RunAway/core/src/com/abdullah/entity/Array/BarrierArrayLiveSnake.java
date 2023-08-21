package com.abdullah.entity.Array;

import com.abdullah.GameMain;

public class BarrierArrayLiveSnake extends BarrierArrayLive{
		public BarrierArrayLiveSnake(GameMain gameMain) {
			super(gameMain);
			this.setImageHeight(16f);
			this.setImageWidth(23f); 
			this.setAnimationHz(this.getGameDefine().animationHzSnake);
			this.setAnimationTexturePath("require/Barriers/GREENSnake_strip7.png");
			this.setBoxHeightScale(this.getImageHeight()-5f);
			this.setBoxWidthScale(this.getImageWidth()-5f);
			
			this.setId(2);
		}

}
