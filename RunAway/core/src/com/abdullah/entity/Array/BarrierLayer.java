package com.abdullah.entity.Array;

import java.util.ArrayList;

import com.abdullah.GameMain;
import com.badlogic.gdx.utils.Disposable;

public class BarrierLayer implements Disposable {
	private ArrayList<BarrierArray> barrierArrays;
	
	public ArrayList<BarrierArrayLive> barrierArrayLives;
	
	public BarrierArrayFixTree barrierArrayTree;
	public BarrierArrayLiveSnake barrierArraySnake;
	public BarrierArrayLiveBat barrierArrayBat;
	
	public BarrierLayer(GameMain gameMain) {
		this.barrierArrayTree=new BarrierArrayFixTree(gameMain);
		this.barrierArrayBat=new BarrierArrayLiveBat(gameMain);
		this.barrierArraySnake=new BarrierArrayLiveSnake(gameMain);
		this.barrierArrays=new ArrayList<BarrierArray>();
		this.barrierArrays.add(this.barrierArrayTree);
		this.barrierArrays.add(this.barrierArrayBat);
		this.barrierArrays.add(this.barrierArraySnake);
		this.barrierArrayLives=new ArrayList<BarrierArrayLive>();
		this.barrierArrayLives.add(barrierArraySnake);
		this.barrierArrayLives.add(barrierArrayBat);
		
		
	}
	public void setPositionLayerCreate(float X[][],float Y[][]) {//burda posizyonlarini belirledik
		
		// 0 index rock
		// 1 index bat
		// 2 index snake
		//burda her bir objenin posizyonlarini belirledik
		int i=0;
		for (BarrierArray array :this.barrierArrays) {
			array.createLast(X[i], Y[i]);
			i++;
		}
		
	}
	public void rePosition(float X[][],float Y[][]) {
		int i=0;
		for (BarrierArray array :this.barrierArrays   ) {
			array.rePositionImageAndBody(X[i], Y[i]);
			i++;
		}
	}
	
	
	
	public void render(float elepsedtime) {
		for (BarrierArray array :this.barrierArrays ) {
			array.render(elepsedtime);
		}
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		for (BarrierArray array :this.barrierArrays   ) {
			array.dispose();
		}
	}
	
	private void info () {
		for (BarrierArray barrierArray : barrierArrays) {
			for (int i = 0; i < barrierArray.bodyCount; i++) {
				System.out.print("("+barrierArray.getImagePositionX(i)+","+barrierArray.getImagePositionY(i)+")-");
			} 
			System.out.println();
		}
	}
}
