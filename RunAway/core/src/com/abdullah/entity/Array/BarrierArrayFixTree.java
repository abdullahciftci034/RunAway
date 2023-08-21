
package com.abdullah.entity.Array;
import com.abdullah.GameMain;

public class BarrierArrayFixTree extends BarrierArrayFix {
	public BarrierArrayFixTree(GameMain gameMain) {
		super(gameMain);
		this.setPathTexture("require/Barriers/tree.png");
		this.setImageHeight(40f);
		this.setImageWidth(30f);
		this.setBoxWidthScale(this.getImageWidth()-15f);
		this.setBoxHeightScale(this.getImageHeight()-15f);
		
		
	}
	
	@Override
	public float getScaleSizeImage() {
		return 1f/(this.getScaleSize()*3f);
	}

}
