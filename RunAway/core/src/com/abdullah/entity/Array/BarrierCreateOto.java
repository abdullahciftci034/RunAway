package com.abdullah.entity.Array;


import com.abdullah.GameDefine;
import com.abdullah.GameMain;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

public class BarrierCreateOto implements Disposable {
	private GameMain gameMain;
	private BarrierLayer layer;
	private int LayerNowIndex,//cizdirilecek olan index
				maxBodyCount,//bir layerda bulacaka maximum body sayisi
				LayerCount, //layer sayilari
				barrierTypeCount;//olusacak barrier tipleri sayisi
	
	private float layersPositionX[][][],layersPositionY[][][];
	
	public BarrierCreateOto(GameMain gameMain) {
		this.gameMain=gameMain;
	}
	
	public void create() {
		float array[]= {
				2,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,1,0,
				0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,
				0,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,1,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,1,
				0,1,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,0,3,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,0,3,0,0,0,0,
				2,0,0,0,0,0,0,0,0,0,3,0,0,0,0,2,0,0,0,0,0,0,0,3,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,2,0,0,0,2,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,2,0,0,0,0,
				1,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,2,
				0,1,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,1,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,3,0,0,
				0,0,3,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,1,0,0,0,2,0,0,0,0,0,
				0,0,0,0,0,2,0,0,0,0,0,0,0,3,0,0,0,0,0,0,3,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0,0,2,0,0,0,0,0,0,3,0,0,0,0,0,0,0,3,0,0,0,0,
				0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,
				0,0,0,0,0,0,0,3,0,0,0,0,0,0,1,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,3,0,0,0,0,2,0,0,0,0,1,0,0,
				0,0,0,1,0,0,0,2,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,2,0,0,0,0,0,0,1,0,0,0,2,0,
				1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,3,0,0,0,0,0,0,2,0,0,0,0,0,3,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,0,0,0,1,0,0,0,1,0
};
		float levels[][] =this.fixing(array, 13);
		
		
		
		
		this.LayerCount=levels.length;
		this.barrierTypeCount=3;
		this.maxBodyCount=10;
		this.LayerNowIndex=1;

		this.layer =new BarrierLayer(this.gameMain);
		this.setLayersPositionX(this.hesaplaX(levels));
		
		
		
		
		
	}
	public void setPositionLayer() {//yeni olusturulan layerdan hemen sonra cagrilmalidir.render edilmeden cagrilmalidir.
		// 0 index rock
		// 1 index bat
		// 2 index snake
		//burda her bir objenin posizyonlarini belirledik
		this.layer.setPositionLayerCreate(this.layersPositionX[this.LayerNowIndex],this.layersPositionY[this.LayerNowIndex]);
	}
	
	
	public void rePositionLayer() {
		this.layer.rePosition(this.layersPositionX[this.LayerNowIndex],this.layersPositionY[this.LayerNowIndex]);
	}
	
	public void render(float elepsedtime) {
		this.layer.render(elepsedtime);		
	}
	
	public void nextLayer() {
		if((1+this.LayerNowIndex)<this.LayerCount) {
		 this.LayerNowIndex++;
		}
	}
	public void previousLayer() {
		if((this.LayerNowIndex-1)>-1) {
			this.LayerNowIndex--;
		}
		
	}


	public void setLayersPositionX(float[][][] layersPositionX) {
		this.layersPositionX = layersPositionX;
	}



	public void setLayersPositionY(float[][][] layersPositionY) {
		this.layersPositionY = layersPositionY;
	}

	
	public int getMaxBodyCount() {
		return maxBodyCount;
	}
	public int getLayerCount() {
		return LayerCount;
	}
	public int getBarrierTypeCount() {
		return barrierTypeCount;
	}

	

	public int getLayerNowIndex() {
		return LayerNowIndex;
	}

	public void setLayerNowIndex(int layerNowIndex) {
		LayerNowIndex = layerNowIndex;
	}

	public BarrierLayer getLayer() {
		return layer;
	}

	@Override
	public void dispose() {
		this.layer.dispose();
	}
	
	

	public float [][][] hesaplaX(float levels[][]) {//burdan degerleri al sonra sonra levellari set et
		float result[][][]=new float[levels.length][barrierTypeCount][this.maxBodyCount];
		for (int i = 0; i < levels.length; i++) {
			result[i]=this.hesaplaXSub(levels[i],i);
		}
		return result;
	}
	
	private float [][] hesaplaXSub(float LevelArray[],int k) {
		
		float  treeX[]=new float[this.maxBodyCount];
		float batX[]=new float[this.maxBodyCount];
		float snakeX[]=new float[this.maxBodyCount];
		if(this.layersPositionY==null) {
			this.setLayersPositionY(this.hesaplaY());
		}	
		int tree=0,bat=0,snake=0;
		for (int i = 0; i < LevelArray.length; i++) {
			if(LevelArray[i]!=0) {
				/*
				 * 3 tree -sivri tas
				 * 1 bat - coin
				 * 2 snake -uzun ince coin
				 * 
				 * 
				 * 
				 * 
				 * */
				if(LevelArray[i]==3 && tree<this.maxBodyCount) {//tree
					treeX[tree]=((i+1)*16)/GameDefine.GAME_PPM+this.gameMain.gameScreen.narrowField0X1;
					this.layersPositionY[k][0][tree]=this.gameMain.gameScreen.platformY+(15f/GameDefine.GAME_PPM);
					tree++;
				}else if(LevelArray[i]==1 && bat<this.maxBodyCount) {//bat 
					batX[bat]=((i+1)*16f)/GameDefine.GAME_PPM+this.gameMain.gameScreen.narrowField0X1;
					if(0==MathUtils.random.nextInt(2)) {
						this.layersPositionY[k][1][bat]=this.gameMain.gameScreen.platformY+this.gameMain.gameScreen.player.getPlayerRadius()-(2f/GameDefine.GAME_PPM);						
					}else {
						this.layersPositionY[k][1][bat]=this.gameMain.gameScreen.platformY+(this.gameMain.gameScreen.player.getPlayerRadius()*3)+(5f/GameDefine.GAME_PPM);	
					}
					
					bat++;
				}else if(LevelArray[i]==2 && snake<this.maxBodyCount) {//snake
					snakeX[snake]=((i+1)*16)/GameDefine.GAME_PPM+this.gameMain.gameScreen.narrowField0X1;
					this.layersPositionY[k][2][snake]=this.gameMain.gameScreen.platformY+(8f/GameDefine.GAME_PPM);
					snake++;
				}
			}
		}
		float result[][] = {treeX,batX,snakeX};
		return result;
	}
	public float[][][] hesaplaY() {
		float result[][][]=new float[LayerCount][barrierTypeCount][maxBodyCount];
		return result;
	}
	
	private void info() {
		for (int i = 0; i < layersPositionY.length; i++) {
			for (int j = 0; j < layersPositionY[i].length; j++) {
				for (int j2 = 0; j2 < layersPositionY[i][j].length; j2++) {
					System.out.print(layersPositionY[i][j][j2]+",");
				}
				System.out.println();

			}
			System.out.println("-------------------------------------");
			System.out.println("-------------------------------------");
		}
	}
	private int end=0;
	private boolean arttirmalast=true;
	public void randomMap() {
		if(this.LayerNowIndex==0) {
			end=0;
			arttirmalast=true;
		}
		if(arttirmalast) {
			
			if(this.LayerCount>end-1 ) {	
				end++;	
				this.LayerNowIndex=end;
			}else {
				arttirmalast=false;
			}
		}else {
			this.LayerNowIndex=MathUtils.random.nextInt(10)+3;
		}
		this.rePositionLayer();
	}
	
	private float[][] fixing(float array[],int levelCount) {
		int size=(array.length+1)/levelCount;
		float array1[][]=new  float [levelCount][size];
		int j=0;
		for (int i = 0; i < array1.length; i++) {
			for (int k = 0; k < array1[i].length; k++) {
				array1[i][k]=array[j];
				j++;
			}
		}
		return array1;
		
	}
	
	
}
